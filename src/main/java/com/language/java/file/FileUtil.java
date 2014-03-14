package com.language.java.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class FileUtil {

    /**
     * create a file
     */
    @SuppressWarnings("unused")
    public void createFile() {
        File file = new File("example.txt");
    }

    /**
     * create a file by specific filename
     */
    @SuppressWarnings("unused")
    public void createFile(String fileName) {
        File file = new File(fileName);

    }

    /**
     * create a file by specific filename and filepath
     */
    @SuppressWarnings("unused")
    public void createFile(String fileName, String filePath) {
        File file = new File(filePath + "/" + fileName);
    }

    /**
     * write content to file by BufferedWriter
     */
    public static void writeContentToFile() {
        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("config/filename.txt"), "utf-8"));
            writer.write("Something");
        } catch (IOException ex) {
            // report
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {
            }
        }
    }

    /**
     * This method is used to write content to file by BufferedWriter 1) create a new file 2) install output object to
     * write content 3) write content
     */
    public static void writeContent() {
        String text = "Hello world";
        try {
            File file = new File("example.txt");
            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            output.write(text);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * write content to file by FileOutputStream
     */
    public static void writeFileContent() {
        try {
            // What ever the file path is.
            File statText = new File("config/statsTest.txt");
            FileOutputStream is = new FileOutputStream(statText);
            OutputStreamWriter osw = new OutputStreamWriter(is);
            Writer w = new BufferedWriter(osw);
            w.write("POTATO!!!");
            w.close();
        } catch (IOException e) {
            System.err.println("Problem writing to the file statsTest.txt");
        }
    }

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
     * read file content then print it to console 1) load file 2) read file content for each line 3) print each line
     * content, you also could save line content to StringBuffer or StringBuilder then print it
     */
    @SuppressWarnings("unused")
    private static String readFileContent() {
        final String methodName = "getDeviceDetailFromLocal";
        final StringBuffer buffer = new StringBuffer();
        try {
            final FileInputStream inputStream = new FileInputStream("config/server.xml");
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
        System.out.println(deviceDetail);
        return deviceDetail;
    }

    /**
     * append A file content to B file
     */

    public static void main(String[] args) {
        // test read file
        // readFileContent();

        // writeContentToFile();

        writeContent();
    }
}
