#!/opt/homebrew/bin/bash

#######
# ES索引索引备份，仅支持从prod备份到test环境
# 支持索引：[1] dps-power-station-device-history-log, [2] dps-alarm-device-rule-record-log
# 支持模式：[1] snapshot&restore, [2] snapshot only, [3] restore only
##
# 使用前提：在源集群和目标集群中，都已经将backup repository映射好（目前使用oss方式）
#  PUT _snapshot/es_backup
#  {
#      "type": "oss",
#      "settings": {
#          "endpoint": "xxx",
#          "access_key_id": "xxx",
#          "secret_access_key": "xxx",
#          "bucket": "xxx",
#          "base_path": "es-backup/",
#          "compress": true
#      }
#  }
#######

# 配置参数
ES_SOURCE_HOST="xxx"  # TODO: replace me
ES_TARGET_HOST="xxx"  # TODO: replace me
SNAPSHOT_REPO="es_backup"

# index and snapshot definition
SOURCE_INDICES=("dps-power-station-device-history-log" "dps-alarm-device-rule-record-log" "dps-power-station-device-month-electric-log" "dps-power-station-device-year-electric-log")
declare -A SNAPSHOT_NAMES_MAP=(
  ["dps-power-station-device-history-log"]="snapshot_device_history_detail_log_full_temp"
  ["dps-alarm-device-rule-record-log"]="snapshot_alarm_log_full_temp"
  ["dps-power-station-device-month-electric-log"]="snapshot_device_history_month_log_full_temp"
  ["dps-power-station-device-year-electric-log"]="snapshot_device_history_year_log_full_temp"
)

# reindex task id
SOURCE_REINDEX_TASK_ID=""
TARGET_REINDEX_TASK_ID=""

# get es credential
get_es_credentials() {
  local es_host=$1
  local es_credential

  if [ "$es_host" == "$ES_SOURCE_HOST" ]; then
    es_credential="xxx:xxx"   # TODO: replace me
  elif [ "$es_host" == "$ES_TARGET_HOST" ]; then
    es_credential="xxx:xxx"   # TODO: replace me
  else
    echo "Error: 未找到对应的凭证" >&2
    exit 1
  fi

  echo "$es_credential"
}

# 输出带颜色的文字
echo_color() {
  local color_code
  case $1 in
    1) color_code="\033[31m" ;; # 红色
    2) color_code="\033[32m" ;; # 绿色
    3) color_code="\033[33m" ;; # 黄色
    4) color_code="\033[1;34m" ;; # 蓝色
    5) color_code="\033[35m" ;; # 紫色
    6) color_code="\033[36m" ;; # 青色
    *) color_code="\033[0m" ;;  # 默认无颜色
  esac
  local text=$2
  echo -e "${color_code}${text}\033[0m"
}

# 通用函数：执行 curl 并打印返回值
execute_and_print() {
  local method=$1
  local url=$2
  local data=$3
  local credentials=$4

  echo -e "\033[1;34mAPI 请求 [$method]: $url\033[0m" # 蓝色字体
  if [ -n "$data" ]; then
    echo -e "\033[1;34m请求数据:\033[0m $data"
    RESPONSE=$(curl -s -u "$credentials" -X "$method" "$url" -H 'Content-Type: application/json' -d "$data")
  else
    RESPONSE=$(curl -s -u "$credentials" -X "$method" "$url")
  fi

  # 检查 curl 是否执行成功
  if [ $? -ne 0 ]; then
    echo -e "\033[1;31m请求失败：无法连接到 $url\033[0m" # 红色字体
    exit 1
  fi

  echo -e "\033[1;34mAPI 响应:\033[0m" # 绿色字体
  if echo "$RESPONSE" | jq . > /dev/null 2>&1; then
    echo "$RESPONSE" | jq .
  else
    echo "$RESPONSE"
    echo -e "\033[1;31m警告: 响应不是有效的 JSON 格式。\033[0m"
  fi
}

# 执行 HTTP 请求并返回响应
execute() {
  local method=$1
  local url=$2
  local data=$3
  local credentials=$4
  local response

  # 构造请求
  if [ -n "$data" ]; then
    response=$(curl -s -u "$credentials" -X "$method" "$url" -H 'Content-Type: application/json' -d "$data")
  else
    response=$(curl -s -u "$credentials" -X "$method" "$url")
  fi

  # 返回响应
  echo "$response"
}

check_continue() {
  echo ""
  read -p "请确认是否继续 (输入 'yes' 继续): " USER_INPUT
  if [ "$USER_INPUT" != "yes" ]; then
    echo "操作已终止。"
    exit 0
  fi
}

# 用户选择源索引
choose_source_index() {
  echo_color 3 "#### 请选择要备份的源索引："
  for i in "${!SOURCE_INDICES[@]}"; do
    echo "$((i + 1)). ${SOURCE_INDICES[i]}"
  done

  read -p "请输入索引对应的编号 (1-${#SOURCE_INDICES[@]}): " INDEX_CHOICE
  if [[ "$INDEX_CHOICE" -ge 1 && "$INDEX_CHOICE" -le ${#SOURCE_INDICES[@]} ]]; then
    SOURCE_INDEX="${SOURCE_INDICES[$((INDEX_CHOICE - 1))]}"
    # TODO: change me
    TARGET_INDEX="${SOURCE_INDEX}-prod"
#    TARGET_INDEX="${SOURCE_INDEX}"
    SNAPSHOT_NAME=${SNAPSHOT_NAMES_MAP[$SOURCE_INDEX]}
    echo "已选择："
    echo "- 源索引：$SOURCE_INDEX"
    echo "- 目标索引：$TARGET_INDEX"
    echo "- 快照名称：$SNAPSHOT_NAME"
  else
    echo "无效选择，请重新运行脚本。"
    exit 1
  fi

  check_continue
}

# 检查并删除已有的临时快照
delete_existing_snapshot() {
  local es_host=$1
  local es_credentials
  es_credentials=$(get_es_credentials "$es_host")
  local es_req_url="$es_host/_snapshot/$SNAPSHOT_REPO/$SNAPSHOT_NAME"

  echo_color 3 "\n#### 检查并删除临时快照：$SNAPSHOT_NAME"

  echo_color 4 "API 请求 [GET]: $es_req_url"
  RESPONSE=$(execute "GET" "$es_req_url" "" "$es_credentials")
  echo "API 响应:"
  echo "$RESPONSE" | jq . 2>/dev/null || echo "$RESPONSE"

  # 检查 RESPONSE 是否为有效 JSON
  if echo "$RESPONSE" | jq . > /dev/null 2>&1; then
    # 提取 `.snapshots` 字段的长度，判断是否存在
    EXISTS=$(echo "$RESPONSE" | jq -r 'if .snapshots then .snapshots | length > 0 else false end')
  else
    echo "Error: API 响应不是有效的 JSON。"
    EXISTS=false
  fi

  if [ "$EXISTS" == "true" ]; then
    echo "检测到临时快照 $SNAPSHOT_NAME 已存在。"
    read -p "是否删除临时快照 $SNAPSHOT_NAME ？(yes/no): " DELETE_CONFIRMATION
    if [ "$DELETE_CONFIRMATION" == "yes" ]; then
      echo "正在删除临时快照 $SNAPSHOT_NAME ..."
      execute_and_print "DELETE" "$es_host/_snapshot/$SNAPSHOT_REPO/$SNAPSHOT_NAME" "" "$es_credentials"
    else
      echo "保留临时快照 $SNAPSHOT_NAME ,跳过删除步骤。"
    fi
  else
    echo "临时快照 $SNAPSHOT_NAME 不存在，跳过删除步骤。"
  fi
}

# 打印存储库中所有快照
list_snapshots() {
  local es_host=$1
  local es_credentials
  es_credentials=$(get_es_credentials "$es_host")

  echo_color 3 "\n#### 获取当前存储库中所有快照..."
  execute_and_print "GET" "$es_host/_snapshot/$SNAPSHOT_REPO/_all" "" "$es_credentials"

  check_continue
}

# 创建快照
create_snapshot() {
  local es_host=$1
  local es_credentials
  es_credentials=$(get_es_credentials "$es_host")

  echo_color 3 "\n#### 为临时索引创建快照..."
  execute_and_print "PUT" "$es_host/_snapshot/$SNAPSHOT_REPO/$SNAPSHOT_NAME" '{
    "indices": "'"$SOURCE_INDEX"'"
  }' "$es_credentials"

  check_continue
}

# 循环检查快照创建进度
wait_for_snapshot_completion() {
  local es_host=$1
  local es_credentials
  es_credentials=$(get_es_credentials "$es_host")
  local es_req_url="$es_host/_snapshot/$SNAPSHOT_REPO/$SNAPSHOT_NAME/_status"

  echo_color 3 "\n#### 开始循环检查快照进度，快照名称: $SNAPSHOT_NAME"
  while true; do
    echo_color 4 "API 请求 [GET]: $es_req_url"
    RESPONSE=$(curl -s -u "$es_credentials" -X GET "$es_req_url")
    echo "当前快照状态:"
    echo "$RESPONSE" | jq . 2>/dev/null || echo "$RESPONSE"

    # 检查快照状态是否完成
    IN_PROGRESS=$(echo "$RESPONSE" | jq -r '.snapshots[0].state')
    if [ "$IN_PROGRESS" == "SUCCESS" ]; then
      echo "快照任务已完成！"
      break
    elif [ "$IN_PROGRESS" == "FAILED" ]; then
      echo "快照任务失败，请检查日志。"
      exit 1
    else
      echo "快照任务尚未完成，等待 10 秒后重试..."
      sleep 10
    fi
  done

  check_continue
}

# 循环检查索引恢复进度
wait_for_restore_completion() {
  local es_host=$1
  local credentials
  credentials=$(get_es_credentials "$es_host")
  local es_req_url="$es_host/$TARGET_INDEX/_recovery"

  echo_color 3 "#### 开始监控索引 [$TARGET_INDEX] 的恢复进度..."
  while true; do
    # 查询恢复状态
    echo_color 4 "API 请求 [GET]: $es_req_url"
    local response
    response=$(curl -s -u "$credentials" -X GET "$es_req_url")
    echo "$response" | jq . 2>/dev/null || echo "$response"

    # 检查 API 响应是否为有效 JSON
    if ! echo "$response" | jq . > /dev/null 2>&1; then
      echo_color 1 "Error: 恢复状态 API 响应无效：$response"
      break
    fi

    # 检查恢复进度
    local total_shards
    local completed_shards
    total_shards=$(echo "$response" | jq -r '.[].shards | length')
    completed_shards=$(echo "$response" | jq -r '.[].shards | map(select(.stage == "DONE")) | length')

    if [ "$completed_shards" -eq "$total_shards" ]; then
      echo_color 2 "#### 恢复完成: $completed_shards/$total_shards shards."
      break
    else
      echo_color 3 "#### 恢复中: $completed_shards/$total_shards shards 完成..."
    fi

    # 等待 5 秒后再次检查
    sleep 5
  done
}

# 恢复快照
perform_restore() {
  local es_host=$1
  local es_credentials
  es_credentials=$(get_es_credentials "$es_host")

  echo_color 3 "\n#### 执行快照恢复，恢复至临时索引..."
  execute_and_print "POST" "$es_host/_snapshot/$SNAPSHOT_REPO/$SNAPSHOT_NAME/_restore" '{
    "indices": "'"$SOURCE_INDEX"'",
    "rename_pattern": "'"$SOURCE_INDEX"'",
    "rename_replacement": "'"$TARGET_INDEX"'"
  }' "$es_credentials"

  check_continue
}

# 检查目标集群是否存在目标索引
check_and_delete_target_index() {
  local es_host=$1
  local credentials
  credentials=$(get_es_credentials "$es_host")
  local es_req_url="$es_host/_cat/indices/$TARGET_INDEX"

  while true; do
    echo_color 3 "#### 检查索引是否存在：$TARGET_INDEX"

    # 使用 HEAD 请求检查索引是否存在
    echo_color 4 "API 请求 [GET]: $es_req_url"
    local response
#    response=$(curl -s -u "$credentials" -X GET "$es_req_url" | grep -w "$index_name")
    response=$(curl -s -u "$credentials" -X GET "$es_req_url")
    echo "$response" | jq . 2>/dev/null || echo "$response"

    INDEX_EXIST=$(echo "$response" | grep -w "$TARGET_INDEX" | grep -w "green" )
    echo "INDEX_EXIST: $INDEX_EXIST"
    RESP_STATUS_CODE=$(echo "$response" | jq -r '.status' 2>/dev/null || echo "")
    echo "RESP_STATUS_CODE: $RESP_STATUS_CODE"

    if [[ -n "$RESP_STATUS_CODE" && "$RESP_STATUS_CODE" -eq 404 ]]; then
      echo_color 2 "索引 [$TARGET_INDEX] 不存在，可以继续后续操作。"
      break
    fi

    if [[ -n "$INDEX_EXIST" ]]; then
      echo_color 2 "索引 [$TARGET_INDEX] 存在。"
      echo_color 3 "请手动删除索引 [$TARGET_INDEX] 后回到脚本中重试。"
      read -p "是否重新检查索引 [$TARGET_INDEX] 是否存在？(yes/no): " retry_check
      if [ "$retry_check" != "yes" ]; then
        echo_color 1 "用户选择退出检查，脚本终止。"
        exit 1
      fi
      continue
    fi

    echo_color 1 "Error: 检查索引 [$TARGET_INDEX] 时发生错误，HTTP 响应为：$response"
    exit 1

  done

  check_continue
}

# 删除已存在的目标索引
delete_index() {
  local es_host=$1
  local es_credentials
  es_credentials=$(get_es_credentials "$es_host")

  echo_color 3 "\n#### 删除临时索引：$TEMP_INDEX"
  execute_and_print "DELETE" "$es_host/$TEMP_INDEX" "" "$es_credentials"
}

proc_snapshot() {
  echo_color 3 "开始执行增量备份流程..."
  delete_existing_snapshot "$ES_SOURCE_HOST"
  list_snapshots "$ES_SOURCE_HOST"
  create_snapshot "$ES_SOURCE_HOST"
  wait_for_snapshot_completion "$ES_SOURCE_HOST"
  echo_color 3 "#### 增量备份流程已完成。"
}

proc_restore() {
  echo_color 3 "\n开始执行增量恢复流程..."
  list_snapshots "$ES_TARGET_HOST"
  check_and_delete_target_index "$ES_TARGET_HOST"
  perform_restore "$ES_TARGET_HOST"
  wait_for_restore_completion "$ES_TARGET_HOST"
  echo_color 3 "#### 增量恢复流程已完成。"
}

# 主流程
main() {
  # choose index
  choose_source_index

  # set run mode
  read -p "请选择备份模式：[1] snapshot&restore, [2] snapshot only, [3] restore only: " BACKUP_MODE
  if [ "$BACKUP_MODE" != "1" ] && [ "$BACKUP_MODE" != "2" ] && [ "$BACKUP_MODE" != "3" ]; then
    echo "Invalid backup mode! Exit."
    exit 0
  fi

  # snapshot and restore procedure
  if [ "$BACKUP_MODE" == "1" ]; then
    proc_snapshot
    proc_restore
  fi

  # snapshot only procedure
  if [ "$BACKUP_MODE" == "2" ]; then
    proc_snapshot
  fi

  # restore only procedure
  if [ "$BACKUP_MODE" == "3" ]; then
    proc_restore
  fi

  echo_color 3 "\n任务结束，退出。"
}

main
