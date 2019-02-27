package com.yuetsao.httpserver.core;

import com.sun.security.ntlm.Server;
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
            //服务器套接字，绑定端口号：8080
            ServerSocket serverSocket = new ServerSocket(8080);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
