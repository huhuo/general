package com.huhuo.integration.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;
/**
 * Linux command utils
 * @author wuyuxuan
 */
public class LinuxUtils {

	private static String encoding = "UTF-8";
	
	private static Long timeout = 30 * 60 * 1000L;
	
	private static void closeIO(InputStream errorStream,
			InputStream inputStream, OutputStream outputStream) {
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (errorStream != null) {
			try {
				errorStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (outputStream != null) {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 执行一条linux命令
	 * @param command 命令字符串
	 */
	public static String execAndReturn(String... args) {
		return execAndReturn(args, timeout);
	}

	/**
	 * 执行一条linux命令
	 * @param args 命令字符串
	 */
	public static String execAndReturn(String[] args, long timeout) {
		final long startTime = System.currentTimeMillis();
		InputStream errorStream = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		Timer timer = null;
		try {
			final Process process = Runtime.getRuntime().exec(args);
			if (timeout > 0) {
				timer = new Timer();
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						process.destroy();
						System.out.println(String
								.format("cost time: [%s] ms. process already stoped cause timeout.",
										(System.currentTimeMillis() - startTime)));
					}
				}, timeout);
			}
			errorStream = process.getErrorStream();
			inputStream = process.getInputStream();
			outputStream = process.getOutputStream();
			process.waitFor();
			// get return value
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int len = 0;
			while ((len = inputStream.read(b)) != -1) {
				bos.write(b, 0, len);
			}
			String retValue = new String(bos.toByteArray(), encoding);
			return retValue;
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		} finally {
			if (timer != null) {
				timer.cancel();
			}
			closeIO(errorStream, inputStream, outputStream);
		}
	}

}
