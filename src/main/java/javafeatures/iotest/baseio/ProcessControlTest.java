package javafeatures.iotest.baseio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author panws
 * @since 2017-08-16
 */
public class ProcessControlTest {

	public static void main(String[] args) {

		OSExecutor.executeCommand("javap ProcessControlTest");
	}
}

class OSExecutor {

	public static void executeCommand(String command) {

		boolean errFlag = false;

		try {

			Process process = new ProcessBuilder(command.split(" ")).start();

			String s;

			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while ((s = in.readLine()) != null) {
				System.out.println(s);
			}

			BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			while ((s = err.readLine()) != null) {
				System.err.println("System.err :" + s);
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
			throw new OSExecuteException("Errors executing " + command);
		}
	}
}

class OSExecuteException extends RuntimeException {

	public OSExecuteException(String msg) {

		super(msg);
	}
}