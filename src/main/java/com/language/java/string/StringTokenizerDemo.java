package com.language.java.string;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class StringTokenizerDemo {

    /**
     * use the StringTokenizer to split a string with "," split
     */
    public static void stringTokenizerTest1() {
        System.out.println("\n---- String Split by space ------");
        // use the string.split to split a string
        String[] result = "this is a test".split("\\s");
        for (int x = 0; x < result.length; x++) {
            System.out.println(result[x]);
        }

        // we use the StringTokenizer to split a string
        String str = "This is String , split by StringTokenizer, created by mkyong";
        StringTokenizer st = new StringTokenizer(str);

        // default splict flag is space
        System.out.println("\n---- Split by space ------");
        while (st.hasMoreElements()) {
            System.out.println(st.nextElement());
        }

        // returnDelims default is false
        System.out.println("\n---- Split by comma ',' ------");
        StringTokenizer st2 = new StringTokenizer(str, ",");

        while (st2.hasMoreElements()) {
            System.out.println(st2.nextElement());
        }
    }

    public static void stringTokenizerTest2() throws IOException {
        File file = new File("testEquations.txt");
        String[] lines = new String[10];
        try {
            FileReader reader = new FileReader(file);
            BufferedReader buffReader = new BufferedReader(reader);
            int x = 0;
            String s;
            while ((s = buffReader.readLine()) != null) {
                lines[x] = s;
                x++;
            }
        } catch (IOException e) {
            System.exit(0);
        }
        String OPERATORS = "+-*/()";

        for (String st : lines) {
            if (st != null) {

                StringTokenizer tokens = new StringTokenizer(st, OPERATORS, true);
                while (tokens.hasMoreTokens()) {
                    String token = tokens.nextToken();
                    if (OPERATORS.contains(token))
                        handleOperator(token);
                    else
                        handleNumber(token);
                }
            }
        }
    }

    private static void handleNumber(String token) {
        System.out.println("" + token);

    }

    private static void handleOperator(String token) {
        System.out.println("" + token);

    }

    public static void main(String[] args) {

        // stringTokenizerTest1();

        try {
            stringTokenizerTest2();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
