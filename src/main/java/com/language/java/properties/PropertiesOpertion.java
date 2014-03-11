package com.language.java.properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * This class care some properties file opertion, for example how to create a properties and set some property to this
 * file, how to get a properties file and read this file content.
 * 
 * @author zhangkeh
 */
public class PropertiesOpertion {

    /**
     * Main method to run some test cases
     * 
     * @param args
     */
    public static void main(String[] args) {

        PropertiesOpertion propertiesOpertion = new PropertiesOpertion();

        //propertiesOpertion.createProperties();

        //propertiesOpertion.getProperties();
        propertiesOpertion.loadClassPathProperties();

    }

    /**
     * Create a properties file and set some properties to this file
     */
    public void createProperties() {
        Properties prop = new Properties();
        OutputStream output = null;

        try {

            output = new FileOutputStream("config.properties");

            // set the properties value
            prop.setProperty("database", "localhost");
            prop.setProperty("dbuser", "mkyong");
            prop.setProperty("dbpassword", "password");

            // save properties to project root folder
            prop.store(output, null);
            System.out.println("create config.properies file is success!");
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * Get a properties and read this file property
     */
    public void getProperties() {

        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("config.properties");

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            System.out.println("database value is: " + prop.getProperty("database"));
            System.out.println("dbuser value is: " + prop.getProperty("dbuser"));
            System.out.println("dbpassword value is: " + prop.getProperty("dbpassword"));

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * Load a properties file from classpath
     */
    public void loadClassPathProperties() {
        Properties prop = new Properties();
        InputStream input = null;

        try {

            String filename = "config.properties";
            input = PropertiesOpertion.class.getClassLoader().getResourceAsStream(filename);
            if (input == null) {
                System.out.println("Sorry, unable to find " + filename);
                return;
            }

            // load a properties file from class path, inside static method
            prop.load(input);

            // get the property value and print it out
            System.out.println(prop.getProperty("database"));
            System.out.println(prop.getProperty("dbuser"));
            System.out.println(prop.getProperty("dbpassword"));

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }
    
    /**
     * print properties all properties and values
     * @param prop
     */
    @SuppressWarnings("rawtypes")
    public void loopProperties(Properties prop) {
        for (Enumeration e = prop.propertyNames(); e.hasMoreElements();) {
            String s = (String)e.nextElement();
            System.out.println(s + " : " + prop.getProperty(s));
        }

    }

}
