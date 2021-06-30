package com.pws.commander;

import com.beust.jcommander.JCommander;
import com.pws.javafeatures.io.nio.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * JCommander Command Line Tool
 * <p>
 * -f, --files     JSON files relative path
 * <p>
 * -h, --help      Show all options
 *
 * @author wilson.pan
 * @date 2021/6/30
 */
@Slf4j
public class JCommanderRunner {

    public static void main(String[] args) {
        JCommanderArgs arguments = new JCommanderArgs();
        JCommander commander = JCommander.newBuilder().addObject(arguments).build();
        commander.parse(args);

        int status = 0;
        if (arguments.isHelp()) {
            commander.usage();
        } else {
            log.info("--------- Start ---------");
            status = run(arguments.getFiles());
            log.info("--------- End   ---------");
        }

        System.exit(status);
    }

    public static int run(List<String> files) {
        if (CollectionUtils.isEmpty(files)) {
            log.error("Please input at least 1 file path!");
            return -1;
        }

        for (String filePath : files) {
            log.info("Read from file path: [{}]", filePath);
            if (StringUtils.isBlank(filePath)) {
                log.error("file path is empty, skip.");
                continue;
            }

            try {
                String json = FileUtils.read(filePath);
                log.info("Read file success [{}]: {}", filePath, json);
            } catch (Exception e) {
                log.error("Read file failed [{}]: {}", filePath, e.getMessage());
            }
        }

        return 0;
    }
}
