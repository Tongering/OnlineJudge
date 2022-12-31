package com.tongering.coderunningsystem.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class Code implements java.util.function.Supplier<Object> {

    public Object result(Object ...object) {
        return "成功调用";
    }

//    @Override
//    public Object get() {
//        return null;
//    }
    @Override
    public Object get() {
        File file = new File("input.txt");
        try {
            Scanner sc = new Scanner(file);
            String input = sc.next();
            System.out.println(input.substring(1));
            Object[] in = input.substring(1).split(",");
            return result(in);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
