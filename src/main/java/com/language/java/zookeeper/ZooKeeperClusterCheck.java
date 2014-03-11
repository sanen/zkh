package com.language.java.zookeeper;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.net.telnet.TelnetClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * this class is used to check zookeeper cluster server, restart donw the server
 * 
 * @author zhangkeh
 */
public class ZooKeeperClusterCheck implements Runnable {

    private static Properties prop = null;
    private static final Logger log = LoggerFactory.getLogger(ZooKeeperClusterCheck.class);

    private int systemType = 2;

    public ZooKeeperClusterCheck(int systemType) {
        this.systemType = systemType;
    }

    /**
     * 1. load zookeeper cluster servers config
     */
    public static void loadClusterConfig() {

        InputStream ins = null;
        prop = new Properties();

        try {
            ins = ZooKeeperClusterCheck.class.getClassLoader().getResourceAsStream("clusterServers.properties");
            prop.load(ins);
            ins.close();

            log.info("Load zookeeper cluster configuration file is successful");
        } catch (IOException e) {
            log.error("Throw the error, error message is {}", e.getMessage());
        }

    }

    /**
     * eg: localhost:127.0.0.1
     * 
     * @param host
     * @param port
     */
    public static boolean checkServerLive(String host, int port) {
        boolean isConnect = false;
        try {

            TelnetClient client = new TelnetClient();
            client.setDefaultTimeout(3000);
            client.connect(host, port);
            isConnect = true;

            log.info("This server connect status is {} , host is {} and port is {}", new Object[] { isConnect,
                                                                                                   host,
                                                                                                   port });

            return isConnect;

        } catch (Exception e) {

            log.error("Throw the exception when check sever live, error detail is {}", e.getMessage());
            log.info("This server connect status is {} ", isConnect);
            isConnect = false;
            return isConnect;
        }

    }

    /**
     * write restart bat script file
     * 
     * @param serverPath
     */
    public static void writeRestartBat(String serverPath) {
        try {
            FileWriter writer = new FileWriter("c:\\opt\\restart.bat");
            writer.write("@echo off");
            writer.write("\r\n ");
            writer.write("cd " + serverPath + "/bin");
            writer.write("\r\n ");

            // don't show console window
            // writer.write("start zkServer.cmd");

            // show console window
            writer.write("zkServer.cmd");
            writer.close();
            log.error("write restart zookeeper server bat file is finished");
        } catch (Exception e) {
            log.error("throw the error when write restart zookeeper server, error message is {}", e.getMessage());
        }
    }

    /**
     * write restart bat script file
     * 
     * @param serverPath
     */
    public static void writeRestartSh(String serverPath) {
        final String methodName = "writeRestartSh";
        log.debug("{} begin", methodName);

        try {
            FileWriter writer = new FileWriter("c:\\opt\\restart.sh");
            writer.write("\r\n ");
            writer.write("cd " + serverPath + "/bin");
            writer.write("\r\n ");

            // don't show console window
            // writer.write("start zkServer.cmd");

            // show console window
            writer.write("./zkServer.sh");
            writer.close();
            log.error("write restart zookeeper server sh file is finished");
        } catch (Exception e) {
            log.error("throw the error when write restart zookeeper server, error message is {}", e.getMessage());
            log.debug("{} end", methodName);
        }
        log.debug("{} end", methodName);
    }
    
    /**
     * restart zookeeper server on windows system
     * 
     * @return
     */
    public static boolean restartServerOnWindows() {

        try {
            Runtime.getRuntime().exec("cmd /c start c:/opt/restart.bat");
            log.info("this server restart success");
            return true;
        } catch (IOException e) {
            log.error("this server restart failed");
            return false;
        }

    }

    /**
     * restart zookeeper server
     * 
     * @return
     */
    public static boolean restartServerOnLinux() {

        try {
            Runtime.getRuntime().exec("cmd /c start c:/opt/restart.sh");
            log.info("this server restart success");
            return true;
        } catch (IOException e) {
            log.error("this server restart failed");
            return false;
        }

    }

    

    /**
     * check zookeeper cluster server and restart down server
     * 
     * @param systemType
     *            if it's 1,we run restart on window, if it's 2 we run restart on linux
     */
    public static void zookeeperClusterCheck(int systemType) {
        // 1. load zookeeper cluster servers config
        // 2. check cluster servers whether running
        // 3. get not running the server
        // 4. restart not running the server
        // 5. log this restart server

        // 1. load zookeeper cluster servers config
        loadClusterConfig();

        // 2. check cluster servers whether running
        for (int i = 1; i < prop.size() / 3 + 1; i++) {

            String host = prop.getProperty("host." + i);
            int port = Integer.parseInt((String)prop.getProperty("port." + i));
            String serverPath = prop.getProperty("server." + i);
            boolean connect = checkServerLive(host, port);

            if (!connect) {

                if (systemType == 1) {
                    // 3. write bat file for restart not running server on windows
                    writeRestartBat(serverPath);

                    // 4. restart not running the server on windows system
                    restartServerOnWindows();
                } else if (systemType == 2) {

                    // 3. write bat file for restart not running server on linux
                    writeRestartSh(serverPath);

                    // 4. restart not running the server on linux system
                    restartServerOnLinux();

                } else {
                    // default restart zookeeper server base on linux system
                    // 3. write bat file for restart not running server on windows
                    writeRestartBat(serverPath);

                    // 4. restart not running the server on linux system
                    restartServerOnLinux();
                }

                // 5. log this restart server
                log.info("We need to restart this server, it's host " + host + " and port is " + port);
            }

        }
    }

    /**
     * main method used to test cases
     * 
     * @param args
     */

    public static void main(String[] args) {
        //int systemType = Integer.parseInt(args[1]);
        //new ZooKeeperClusterCheck(systemType).run();
        System.out.println(checkServerLive("127.0.0.1", 2181));
        
        //System.out.println(checkServerLive("127.0.0.1", 2183));
    }
    
    public void run() {
        try {
            while (true) {
                Thread.sleep(3000);
                System.out.println("3秒后*********");
                zookeeperClusterCheck(systemType);
            }
        } catch (InterruptedException e) {
            log.error(" throw the error when we check zookeeper server and restart down server. error message is {}",
                      e.getMessage());
        }
    }

}
