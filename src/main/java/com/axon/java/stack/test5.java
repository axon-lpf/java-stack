package com.axon.java.stack;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test5 {
    public static void main(String[] args) {
       /* System.out.println(Pattern.compile("^/operation/v1/.*").matcher("/operation/v1/systemMenu/queryUserVueRouterList").find());
        System.out.println(Pattern.compile("^/operation/v1/.*").matcher("/operation/v1/systemMenu/queryUserVueRouterList").find());*/

        String phone="17602193229";
        String result=phone.substring(5);
        System.out.println(result);
    }
}
