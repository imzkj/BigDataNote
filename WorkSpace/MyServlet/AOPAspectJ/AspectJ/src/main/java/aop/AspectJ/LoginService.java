package aop.AspectJ;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ZKJ on 2017/9/13.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginService extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //HelloService helloService = new HelloService();
        //helloService.sayHello(1,"Unmi");
        LoginService loginService = new LoginService();
        loginService.go();
        System.out.println("Login");
    }
    public void go(){
        System.out.println("go");
    }
}
