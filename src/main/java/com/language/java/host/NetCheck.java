package com.language.java.host;

import java.net.InetAddress;

public class NetCheck {

    /**
     * eg: 127.0.0.1 use the InetAddress to check host whether available
     */
    public static void checkHost(String host) {

        try {

            InetAddress address = InetAddress.getByName(host);
            boolean reachable = address.isReachable(5000);
            System.out.println("Is host reachable? " + reachable);

        } catch (Exception e) {

            System.out.println("error: " + e.getMessage());

        }

    }
}
