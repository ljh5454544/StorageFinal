package com.jia.storage.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

    private MD5Utils(){};

    public static String encrypt(String password) throws NoSuchAlgorithmException {
        if (password == null)
            return null;
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] arr = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();
        for (byte a : arr){
            sb.append(Integer.toHexString(a & 0xff));
        }
        return sb.toString();
    }
}
