package com.example.myapp.utils;

public class ShareIP {
    private static String ip ="192.168.1.9" ;

//    static public void setIp(String s){
//        ShareIP.ip = s;
//    }

    /**
     * Get ip address
     * @return String ip
     */
    static public String getIp(){
        return ip;
    }
}
