package listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by ZKJ on 2017/8/28.
 */
@WebListener
public class MyListener implements ServletContextListener {
    public void contextInitialized( ServletContextEvent servletContextEvent ) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        System.out.println("创建了Servlet!");
    }

    public void contextDestroyed( ServletContextEvent servletContextEvent ) {
        System.out.println("销毁了Servlet!");
    }
}
