package com.language.java.bat;

import java.net.InetAddress;

import org.apache.commons.net.telnet.TelnetClient; 

public class BatTest {

    /**
     * @param args
     */
    public static void main(String[] args) throws IllegalAccessException {
        checkServerWhetherLive("127.0.0.1", 2181);

    }

    /**
     * eg: localhost:127.0.0.1
     * 
     * @param host
     * @param port
     */
    public static void checkServerWhetherLive(String host, int port) {
        boolean isConnect = false;
        try {
            TelnetClient client = new TelnetClient();
            client.setDefaultTimeout(3000);
            client.connect(host, port);
            isConnect = true;
        } catch (Exception e) {
            /**
             * restart zookeeper cluster
             */

        } finally {
        }
        System.out.println(" connect status : " + isConnect);

    }

    /**
     * use the InetAddress to check host whether available
     */
    public static void testHostPort() {

        try {

            InetAddress address = InetAddress.getByName("127.0.0.1");
            boolean reachable = address.isReachable(5000);
            System.out.println("Is host reachable? " + reachable);

        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());

        }

    }
}