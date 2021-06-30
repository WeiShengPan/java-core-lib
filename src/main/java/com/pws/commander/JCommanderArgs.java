package com.pws.commander;

import com.beust.jcommander.Parameter;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * @author wilson.pan
 * @date 2021/6/30
 */

@Getter
@ToString
public class JCommanderArgs {

    @Parameter(names = {"-f", "--files"}, variableArity = true, description = "files relative path")
    private List<String> files;

    @Parameter(names = {"-h", "--help"}, help = true)
    private boolean help;
}
