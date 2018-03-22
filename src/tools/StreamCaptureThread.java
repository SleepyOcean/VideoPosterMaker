package tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @name: StreamCaptureThread.java
 * @author: sleepyocean
 * @date: created in 2018年3月13日 下午10:25:14
 * @version: 1.0
 * @description: TODO (write your description)
 */

public class StreamCaptureThread implements Runnable {
	InputStream inputStream;
	StringBuilder output;

	public StreamCaptureThread(InputStream inputStream) {
		this.inputStream = inputStream;
		this.output = new StringBuilder();
	}

	//TODO:考虑死锁问题
	@Override
	public void run() {
		try {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(this.inputStream, "GBK"));
				String line = br.readLine();
				while (line != null) {
					if (line.trim().length() > 0) {
						output.append(line).append("\n");
					}
					line = br.readLine();
				}
			} finally {
				if (inputStream != null) {
					inputStream.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
