package com.language.java.properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

        propertiesOpertion.createProperties();

        propertiesOpertion.getProperties();

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
            System.out.println("database value is: "+prop.getProperty("database"));
            System.out.println("dbuser value is: "+prop.getProperty("dbuser"));
            System.out.println("dbpassword value is: "+prop.getProperty("dbpassword"));

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

}
