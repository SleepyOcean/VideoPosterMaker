package tools;

import java.io.IOException;

/**
 * @name: CommandExcutor.java
 * @author: sleepyocean
 * @date: created in 2018年3月13日 下午11:27:37
 * @version: 1.0
 * @description: TODO (write your description)
 */

public class CommandExcutor {
	public static void excute(String command) throws IOException, InterruptedException {
		String result;
		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec(command);
		StreamCaptureThread errorStream = new StreamCaptureThread(process.getErrorStream());
		StreamCaptureThread outputStream = new StreamCaptureThread(process.getInputStream());
		new Thread(errorStream).start();
		new Thread(outputStream).start();
		process.waitFor();

		result = command + "\n" + outputStream.output.toString() + errorStream.output.toString();
		System.out.println(result);
	}
}
