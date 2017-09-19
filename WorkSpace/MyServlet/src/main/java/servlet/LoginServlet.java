package servlet;

import aop.AspectJ.HelloService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by ZKJ on 2017/8/28.
 */
//@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HelloService helloService = new HelloService();
        helloService.sayHello(1,"Unmi");
//        BeanFactory factory = new ClassPathXmlApplicationContext("applicationContextCommon.xml");
//        aop.SpringCommon.HelloService helloService1 = (aop.SpringCommon.HelloService) factory.getBean("helloService");
//
//        helloService1.sayHello(2, "Unmi");
        //获取前端传递参数
        //String username = request.getParameter("username");
        //获取web.xml初始化参数
        String username = getServletConfig().getInitParameter("username");
        String password = request.getParameter("password");
        boolean success = validateUser(username, password);

        try {
            // Write some content
            out.println("<html>");
            out.println("<head>");
            out.println("<title>LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");

            if (success) {
                out.println("<h2>Welcome Friend</h2>");
            } else {
                out.println("<h2>Validate your self again.</h2>");
            }

            out.println("</body>");
            out.println("</html>");
        }finally {
            out.close();
        }
    }

    private boolean validateUser( String username, String password ) {
        if (username.equals("zkj") && password.equals("yes")) {
            return true;
        }
        return false;
    }
}
