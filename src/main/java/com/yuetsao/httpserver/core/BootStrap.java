package com.yuetsao.httpserver.core;

import com.yuetsao.httpserver.util.Logger;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * httpserver程序主入口
 */
public class BootStrap {
    /**
     * 主程序
     * @param args
     */
    public static void main(String[] args) {
        //程序入口
        start();
    }

    private static void start() {

        try {
            Logger.log("htttpServer start");
            long start = System.currentTimeMillis();
            //获取系统端口号
            int port = ServerParser.getPort();
            Logger.log("httpserver-port:" + port);
            //服务器套接字，绑定端口号：8080
            ServerSocket serverSocket = new ServerSocket(port);
            long end = System.currentTimeMillis();
            Logger.log("httperServer started : 耗时" + (end-start) + "ms");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
