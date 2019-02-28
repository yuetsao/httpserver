package org.yuetsao.oa;

import com.yuetsao.httpserver.core.ResponseObject;

import javax.serlvet.Servlet;
import java.io.PrintWriter;

/**
 * 处理登录的业务
 */
public class LoginServlet implements Servlet {
    public void service(ResponseObject responseObject) {
        System.out.println("正在验证身份，请稍等。。。。");
        //获取响应流对象
        PrintWriter out = responseObject.getOut();
        out.print("<html>");
        out.print("<head>");
        out.print("<title>正在验证</title>");
        out.print("<meta content='text/html; charset=utf-=8'/");
        out.print("</head>");
        out.print("<body>");
        out.print("<center><font color='red'>正在验证请稍等。。。。</font></center>");
        out.print("</body>");
        out.print("</html>");
    }
}
