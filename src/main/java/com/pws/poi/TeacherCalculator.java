package com.pws.poi;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.pws.javafeatures.io.nio.FileUtils;
import com.pws.javafeatures.util.PrintUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

public class TeacherCalculator {

    private static List<String> fileNames = Lists.newArrayList();
    private static final String DIRECTORY = "src/main/resources/poi/";

    static {
        fileNames.add("0106.xls");
        fileNames.add("0113.xlsx");
        fileNames.add("0120.xls");
        fileNames.add("0127.xls");
        fileNames.add("0203.xls");
        fileNames.add("0217.xls");
        fileNames.add("0224.xls");
        fileNames.add("0303.xlsx");
        fileNames.add("0317.xlsx");
        fileNames.add("0324.xlsx");
        fileNames.add("0331.xlsx");
    }


    public static void main(String[] args) throws Exception {

//        calculate();
        write(calculate());


    }

    private static List<TeacherInfo> calculate() {
        List<TeacherInfo> teacherInfos = Lists.newArrayList();

        // cal
        for (String fileName : fileNames) {
            try {
                Map<String, Integer> teacherMap = new HashMap<>();
                String date = StringUtils.split(fileName, ".")[0];
                TeacherInfo teacherInfo = new TeacherInfo();
                teacherInfo.setDate(date);
                teacherInfo.setTeacherMap(teacherMap);
                teacherInfos.add(teacherInfo);

                Workbook workbook = readWorkbook(DIRECTORY + fileName);
                Sheet sheet = workbook.getSheetAt(0);
                for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row != null) {
                        Cell cell = row.getCell(0);
                        if (cell != null) {
                            String name = cell.toString();
                            if (StringUtils.isNotBlank(name)) {
                                name = StringUtils.lowerCase(name);
                                Integer count = teacherMap.get(name);
                                if (count == null) {
                                    count = 1;
                                } else {
                                    count++;
                                }
                                teacherMap.put(name, count);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                PrintUtil.err("failed: " + fileName);
                PrintUtil.err(e.getMessage(), e);
            }
        }

        // write
        try {
            FileUtils.write("src/main/resources/poi/teacher.txt", JSONArray.toJSONString(teacherInfos));
        } catch (Exception e) {
            PrintUtil.err("write failed: " + e);
        }

        return teacherInfos;
    }

    private static Workbook readWorkbook(String path) throws Exception {
        FileInputStream fis = new FileInputStream(path);
        Workbook workbook;
        if (StringUtils.endsWithIgnoreCase(path, ".xls")) {
            workbook = new HSSFWorkbook(fis);
        } else {
            workbook = new XSSFWorkbook(fis);
        }
        return workbook;
    }

    private static void write() throws Exception {
        String content = FileUtils.read("src/main/resources/poi/teacher.txt");
        List<TeacherInfo> teacherInfos = JSONArray.parseArray(content, TeacherInfo.class);
        write(teacherInfos);
    }

    private static void write(List<TeacherInfo> teacherInfos) throws Exception {
        teacherInfos.sort(Comparator.comparing(TeacherInfo::getDate));
        Set<String> names = new TreeSet<>();
        List<Map<String, Integer>> dataMaps = new LinkedList<>();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("s1");

        // first row
        Row firstRow = sheet.createRow(0);
        firstRow.createCell(0).setCellValue("week");
        int columnCount = 1;
        for (TeacherInfo teacherInfo : teacherInfos) {
            firstRow.createCell(columnCount++).setCellValue(teacherInfo.getDate());
            for (Map.Entry<String, Integer> entry : teacherInfo.getTeacherMap().entrySet()) {
                names.add(entry.getKey());
            }
            dataMaps.add(teacherInfo.getTeacherMap());
        }

        // content
        int rowCount = 1;
        for (String name : names) {
            Row row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(name);
            int cc = 1;
            for (Map<String, Integer> dataMap : dataMaps) {
                Integer num = dataMap.get(name);
                Cell cell = row.createCell(cc++);
                if (num != null) {
                    cell.setCellValue(num);
                }
            }
        }

        File xlsFile = new File("src/main/resources/poi/season1.xlsx");
        FileOutputStream fos = new FileOutputStream(xlsFile);
        workbook.write(fos);
    }


}
