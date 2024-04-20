package com.example.springdemo.utils;

public class Codes{
    private int k=4563;
    private int b=12312;
    static public Integer Code(Integer x)
    {
        return x*4563+12312;
    }

    static public Integer Decode(Integer x)
    {
        Integer real_id=(x-12312)/4563;
        if(!Code(real_id).equals(x))
        {
            System.out.println("decode error");
            return 0;
        }
        else return real_id;
    }
}