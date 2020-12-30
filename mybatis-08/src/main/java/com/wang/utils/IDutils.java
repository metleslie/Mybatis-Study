package com.wang.utils;

import org.junit.Test;

import java.util.UUID;

/**
 * @Author 19225
 * @create 2020/12/26 13:56
 */
@SuppressWarnings("all")
public class IDutils {
    public static String getId()
    {
        return UUID.randomUUID().toString().replace("-","");
    }
    @Test
    public void a()
    {
        System.out.println(IDutils.getId());
    }
}
