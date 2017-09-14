package aop.SpringAspectJ;

/**
 * Created by ZKJ on 2017/9/12.
 */
import aop.SpringAspectJ.HelloService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * 测试主类
 * @author Unmi
 */
public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        BeanFactory factory =new ClassPathXmlApplicationContext("applicationContext.xml");
        HelloService helloService = (HelloService)factory.getBean("helloService");

        helloService.sayHello(1,"Unmi");
    }
}