package com.huhuo.integration.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;

import com.huhuo.integration.HuhuoIntegrationTest;

public class LinuxUtilsTest extends HuhuoIntegrationTest {

	@Test
	public void simpleCmd() {
//		String command = "scp -P22 -r /opt/shell root@192.168.1.180:/opt/";
		String command = "who";
		String rs = LinuxUtils.execAndReturn(command);
		print(rs);
	}
	
	@Test
	public void getLocalhostIp() throws UnknownHostException {
		InetAddress localHost = InetAddress.getLocalHost();
		print(localHost.getHostName(), localHost.getHostAddress());
	}
	
	@Test
	public void complexCmd() {
		// ifconfig wlan0 | sed -n '/inet addr/p' | awk -F'[: ]+' '{print $4}'
//		ret = execAndReturn("ifconfig wlan0");
		String rs = LinuxUtils.execAndReturn("/bin/sh", "-c", "ifconfig wlan0 | sed -n '/inet addr/p' | awk -F'[: ]+' '{print $4}'");
		print(rs);
	}
	
	@Test
	public void rsync() {
		String[] commandArray = new String[] {"rsync", "-arv", "--delete", 
				"--rsh=ssh -p 22", "/opt/shell", "root@192.168.1.180:/opt/"};
		String rs = LinuxUtils.execAndReturn(commandArray);
		print(rs);
	}
	
	
}
