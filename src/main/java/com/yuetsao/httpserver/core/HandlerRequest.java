package com.yuetsao.httpserver.core;

import com.yuetsao.httpserver.util.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 处理客户端请求
 * @author yuetso
 * @version 1.0
 * @since 1.0
 */
public class HandlerRequest implements Runnable {

    public Socket clientSocket;

    public HandlerRequest(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        //处理客户端请求
        //接收客户端消息
        BufferedReader br = null;
        Logger.log("htttpserver thread" + Thread.currentThread().getName());
        try {
            br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            //打印客户端消息
            String temp = null;
            while((temp = br.readLine()) !=null) {
                System.out.println(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(clientSocket!=null) {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(br!=null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
