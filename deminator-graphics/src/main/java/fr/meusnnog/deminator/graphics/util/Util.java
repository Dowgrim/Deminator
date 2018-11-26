package fr.meusnnog.deminator.graphics.util;

import java.net.*;
import java.util.Enumeration;

public class Util {
	public static InetAddress getFirstNonLoopbackAddress(boolean preferIpv6) throws SocketException {
		Enumeration en = NetworkInterface.getNetworkInterfaces();
		while (en.hasMoreElements()) {
			NetworkInterface i = (NetworkInterface)en.nextElement();
			for (Enumeration en2 = i.getInetAddresses(); en2.hasMoreElements();) {
				InetAddress addr = (InetAddress)en2.nextElement();
				if (!addr.isLoopbackAddress()) {
					if (!preferIpv6 && addr instanceof Inet4Address) {
						return addr;
					} else if (preferIpv6 && addr instanceof Inet6Address) {
						return addr;
					}
				}
			}
		}
		return null;
	}
}
