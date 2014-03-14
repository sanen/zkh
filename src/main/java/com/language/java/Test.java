package com.language.java;

public class Test {

   //Integer
    public static int test(int i){
        if(i==1){
            return 1;
        }else{
            return i*test(i-1);
        }
    }
    /**
     * 15=2004310016
     * 16=2004189184
     *    
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(test(17));
    }

}
