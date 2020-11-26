package com.pws.javafeatures.io.baseio;

import com.pws.javafeatures.util.PrintUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author panws
 * @since 2017-08-16
 */
public class ProcessControlTest {

	public static void main(String[] args) {

		OsExecutor.executeCommand("javap ProcessControlTest");
	}
}

class OsExecutor {

	private OsExecutor() {

	}

	static void executeCommand(String command) {

		boolean errFlag = false;

		try {

			Process process = new ProcessBuilder(command.split(" ")).start();

			String s;

			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while ((s = in.readLine()) != null) {
				PrintUtil.println(s);
			}

			BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			while ((s = err.readLine()) != null) {
				PrintUtil.err("System.err :" + s);
				errFlag = true;
			}

		} catch (IOException e) {
			if (command.startsWith("CMD /C")) {
				executeCommand("CMD /C " + command);
			} else {
				throw new RuntimeException(e);
			}
		}

		if (errFlag) {
			throw new OsExecuteException("Errors executing " + command);
		}
	}
}

class OsExecuteException extends RuntimeException {

	OsExecuteException(String msg) {

		super(msg);
	}
}