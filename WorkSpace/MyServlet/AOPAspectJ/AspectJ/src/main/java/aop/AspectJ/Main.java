package aop.AspectJ;

/**
 * 测试主类
 * @author Unmi
 */
public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        HelloService helloService = new HelloService();
        helloService.sayHello(1,"Unmi");
    }
}