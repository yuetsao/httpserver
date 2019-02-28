package com.yuetsao.httpserver.core;

import java.io.PrintWriter;

/**
 * 负责封装响应流对象
 */
public class ResponseObject {
    private PrintWriter out;

    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }
}
