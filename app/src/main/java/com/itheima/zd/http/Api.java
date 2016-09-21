package com.itheima.zd.http;

public interface Api {
    //服务器的主机地址
    String SERVER_HOST = "http://127.0.0.1:8090/";
    //图片url的前缀 : http://127.0.0.1:8090/image?name=
    String IMAGE_PREFIX = SERVER_HOST + "image?name=";
    //home页的url地址
    String Home = SERVER_HOST + "home?index=";
    String App = SERVER_HOST + "app?index=";
    String Game = SERVER_HOST + "game?index=";
    String Subject = SERVER_HOST + "subject?index=";
    /**
     * recommend页的url地址
     */
    String Recommend = SERVER_HOST + "recommend?index=0";
    /**
     * 分类页的url地址
     */
    String Category = SERVER_HOST + "category?index=0";
    /**
     * Hot页的url地址
     */
    String Hot = SERVER_HOST + "hot?index=0";

    String Detail = SERVER_HOST + "detail?packageName=%s";

}
