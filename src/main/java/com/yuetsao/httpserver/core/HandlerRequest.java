package com.yuetsao.httpserver.core;

import com.yuetsao.httpserver.util.Logger;
import org.yuetsao.oa.LoginServlet;

import javax.serlvet.Servlet;
import java.io.*;
import java.net.Socket;
import java.util.Map;

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
        PrintWriter printWriter = null;
        Logger.log("htttpserver thread" + Thread.currentThread().getName());
        try {
            br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            //获取响应流对象
            printWriter = new PrintWriter(clientSocket.getOutputStream());
            //获取请求行数据
            String requetLine = br.readLine();
            //获取uri 在url里
            String requestURI = requetLine.split(" ")[1];
            if(requestURI.endsWith(".html") || requestURI.endsWith(".htm")) {
                //处理静态页面的方法
                responseStaticPage(requestURI, printWriter);
            }else {
                //动态资源的处理
                String servletPath = requestURI;
                if(servletPath.contains("?")) {
                    servletPath = servletPath.split("[?]")[0];
                }
//                if("/oa/login".equals(servletPath)) {
//                    LoginServlet loginServlet = new LoginServlet();
//                    loginServlet.service();
//                }
                //获取应用的名称： oa在uri中： /oa/login
                String webapname = servletPath.split("[/]")[1];
                //获取servletMaps集合中的value值 key:urlPattern value:servletClassName
                Map<String, String> servletMap = WebParser.servletMaps.get(webapname);
                //获取servletMap集合中的key值 -》 存在于uri中
                String urlPattern = servletPath.substring(1+ webapname.length());
                //获取servletClassName
                String servletClassName = servletMap.get(urlPattern);
                //判断该业务处理的Servlet类是否存在
                if(servletClassName != null) {
                    //
                    printWriter.print("HTTP/1.1 200 OK\n");
                    printWriter.print("Content-Type:text/html;charset=utf-8\n\n");
                    //通过反射机制创建该业务处理类
                    try {
                        //获取封装响应参数对象
                        ResponseObject responseObject = new ResponseObject();
                        responseObject.setOut(printWriter);
                        Class c = Class.forName(servletClassName);
                        Object object = c.newInstance();
                        //这个时候，服务器开发人员不知道如何调用servlet业务处理类里的方法了
                        Servlet servlet = (Servlet)object;
                        servlet.service(responseObject);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }
                }else {

                }



            }
            printWriter.flush();
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

    /**
     * 处理静态页面
     */
    public void responseStaticPage(String requestURI, PrintWriter printWriter) {
        //静态页面的路径
        String htmlPath= requestURI.substring(1);
        BufferedReader br = null;
        StringBuilder html = new StringBuilder();
        try {
            br = new BufferedReader(new FileReader(htmlPath));
            html = new StringBuilder();
            //拼接响应信息
            html.append("Http/1.1 200 OK\n");
            html.append("Content-Type:text/html;charset=utf-8\n\n");
            String temp = null;
            while((temp = br.readLine()) !=null) {
                html.append(temp);
            }
            printWriter.print(html);
        } catch (FileNotFoundException e) {
            //404找不到资源
            StringBuilder stringBuilder = new StringBuilder();
            html.append("HTTP/1.1 404 NotFound\n");
            html.append("Content-Type:text/html;charset=utf-8\n\n");
            html.append("<html>");
            html.append("<head>");
            html.append("<title>404错误</title>");
            html.append("<meta content='text/html;charset=utf-8'/>");
            html.append("</head>");
            html.append("<body>");
            html.append("<center> <font size='35px' color='red'>404-not found</font></center>");
            html.append("</body>");
            html.append("</html>");
            printWriter.print(html);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
