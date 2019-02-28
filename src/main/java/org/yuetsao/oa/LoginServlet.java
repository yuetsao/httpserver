package org.yuetsao.oa;

import javax.serlvet.Servlet;

/**
 * 处理登录的业务
 */
public class LoginServlet implements Servlet {
    public void service() {
        System.out.println("正在验证身份，请稍等。。。。");
    }
}
