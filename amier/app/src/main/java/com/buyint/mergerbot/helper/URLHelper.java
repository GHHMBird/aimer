package com.buyint.mergerbot.helper;

public class URLHelper {

//    String TokenURL = "http://10.0.2.12/"; //測試環境
//    String TokenURL = "http://10.0.15.189:10150/"; //张晓
    String TokenURL = "http://117.184.66.210:10150/"; //生產環境



//    String NoTokenURL = "http://10.0.2.12/"; //測試環境
//    String NoTokenURL = "http://10.0.15.189:10120/"; //张晓
    String NoTokenURL = "http://117.184.66.210:8091/"; //生產環境

    public static URLHelper getInstance() {
        return Singleton.instance;
    }

    private static final class Singleton {
        public static final URLHelper instance = new URLHelper();
    }
}