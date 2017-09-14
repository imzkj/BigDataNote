package aop.AspectJ;


/**
 * Created by ZKJ on 2017/9/12.
 * 被拦截的业务类
 */

public class HelloService {
    public void sayHello(int id,String name){
        try {
            Thread.sleep(512);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Hello "+name+"("+id+")");
    }
}