package javax.serlvet;

import com.yuetsao.httpserver.core.ResponseObject;

import java.io.PrintWriter;

/**
 * 由sun公司指定的Servlet接口规范， 该接口由web服务器开发人员来调用， 由webapp开发人员来实现
 */
public interface Servlet {
    /**
     *处理业务的核心类
     * 响应参数responseObject
     */
    void service(ResponseObject responseObject);

}
