package com.language.java.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtil {

    /**
     * create a file
     */
    public void createFile() {
    }

    public void createFile(String fileName) {

    }

    public void createFile(String fileName, String filePath) {

    }

    /**
     * write content to file
     */

    /**
     * change file content
     */

    /**
     * check file whehter exists
     */

    /**
     * remove file
     */

    /**
     * rename file name
     */

    /**
     * read file content then print it to console 
     * 1) load file 
     * 2) read file content for each line 
     * 3) print each line content, you also could save line content to StringBuffer or StringBuilder then print it
     */
    @SuppressWarnings("unused")
    private String readFileContent() {
        final String methodName = "getDeviceDetailFromLocal";
        final StringBuffer buffer = new StringBuffer();
        try {
            final FileInputStream inputStream = new FileInputStream("globalResources/portlet/config/deviceconfig/server.xml");
            final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = reader.readLine();
            while (line != null) {
                buffer.append(line);
                line = reader.readLine();
            }
            inputStream.close();
        } catch (final FileNotFoundException e) {
            System.out.println("{}(): FileNotFoundException caught: {}" + methodName);
        } catch (final IOException e) {
            System.out.println("{}(): IOException caught: {}" + methodName);
        }

        final String deviceDetail = buffer.toString();
        return deviceDetail;
    }

    /**
     * append A file content to B file
     */

}
