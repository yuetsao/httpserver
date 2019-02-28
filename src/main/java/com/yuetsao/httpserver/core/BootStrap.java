package com.yuetsao.httpserver.core;

import com.yuetsao.httpserver.util.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * httpserver程序主入口
 */
public class BootStrap {
    /**
     * 主程序
     * @param args
     */
    public static void main(String[] args) throws Exception{
        //程序入口
        start();
    }
    private static void start() throws Exception{
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        BufferedReader br =null;
        try {
            Logger.log("htttpServer start");
            long start = System.currentTimeMillis();
            //解析服务器中的web。xml
            String [] webAppNames = {"oa"};
            WebParser.parser(webAppNames);
            //获取系统端口号
            int port = ServerParser.getPort();
            Logger.log("httpserver-port:" + port);
            //服务器套接字，绑定端口号：8080
            serverSocket = new ServerSocket(port);
            //接收客户端消息
            String temp = null;
            while (true) {
                clientSocket = serverSocket.accept();
                br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                System.out.println(temp);
                //开始监听网络，此时程序处于等待状态，等待接受用户消息
                new Thread(new HandlerRequest(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(serverSocket!=null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
