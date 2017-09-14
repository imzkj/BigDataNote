package ZKJ;

import org.apache.commons.lang.time.StopWatch;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by ZKJ on 2017/9/14.
 */
public aspect TestAspectJ {

    //匹配所有类所有方法
    //pointcut mainPointcut():execution(* *..*(..));

    //匹配LoginService.go()方法
    pointcut loginPointcut():execution(void LoginService.go());

    //匹配所有以KJ结尾的方法
    pointcut zkjPointcut():execution(* *KJ(..));

    /*execution(* com..*.*Dao.find*(..))l
    匹配包名前缀为com的任何包下类名后缀为Dao的方法，方法名必须以find为前缀。如com.baobaotao.UserDao#findByUserId()、
    com.baobaotao.dao.ForumDao#findById()的方法都匹配切点。*/

    //在logPointcut之后指定下面的代码
//    Object around():mainPointcut() {
//        StopWatch clock = new StopWatch();
//        System.out.println("start Main");
//        clock.start(); //计时开始
//        Object result = proceed();
//        clock.stop();  //计时结束
//        System.out.println("AspectJ");
//        //显示出方法原型及耗时
//        System.out.println("Takes: " + clock.getTime() + " ms [" + thisJoinPoint.getSignature() + "(" +
//                thisJoinPoint.getSourceLocation().getLine() + ")]");
//
//        return result;
//    }

    Object around():loginPointcut() {
        StopWatch clock = new StopWatch();
        System.out.println("start Login");
        clock.start(); //计时开始
        Object result = proceed();
        clock.stop();  //计时结束
        System.out.println("AspectJ");
        //显示出方法原型及耗时
        System.out.println("Takes: " + clock.getTime() + " ms [" + thisJoinPoint.getSignature() + "(" +
                thisJoinPoint.getSourceLocation().getLine() + ")]");

        return result;
    }

    Object around():zkjPointcut() {
        StopWatch clock = new StopWatch();
        System.out.println("start ZKJ");
        clock.start(); //计时开始
        Object result = proceed();
        clock.stop();  //计时结束
        System.out.println("AspectJ");
        //显示出方法原型及耗时
        System.out.println("Takes: " + clock.getTime() + " ms [" + thisJoinPoint.getSignature() + "(" +
                thisJoinPoint.getSourceLocation().getLine() + ")]");

        return result;
    }
}
