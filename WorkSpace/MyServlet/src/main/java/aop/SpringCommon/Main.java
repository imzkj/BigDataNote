package aop.SpringCommon;

/**
 * Created by ZKJ on 2017/9/12.
 */

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * 测试主类
 *
 * @author Unmi
 */
public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        BeanFactory factory = new ClassPathXmlApplicationContext("applicationContextCommon.xml");
        /*
         * 上面的配置方式需为每个应用方法执行时间记录的 Bean 在外层包一个 ProxyFactoryBean，原来的 Bean 设为一个 Target 实在时麻烦了。
         */
        /*
         *下面用一种应用自动代理的配置方式，指定 BeanNameAutoProxyCreator 的 beanNames 匹配模式即可，
         *如果写成 <value>*Service,*Manager</value>，逗号分隔开，bean ID 以 Service 或 Manager 结尾类的方法都被拦截，这样方便许多。
         */
//        BeanFactory factory = new ClassPathXmlApplicationContext("applicationContextCommon1.xml");

        HelloService helloService = (HelloService) factory.getBean("helloService");

        helloService.sayHello(1, "Unmi");
    }
}