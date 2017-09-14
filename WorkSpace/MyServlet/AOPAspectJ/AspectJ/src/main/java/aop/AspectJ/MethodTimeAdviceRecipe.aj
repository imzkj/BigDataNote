package aop.AspectJ;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 记录方法的执行时间
 * @author Unmi
 */
public aspect MethodTimeAdviceRecipe {

    protected final Log log = LogFactory.getLog(MethodTimeAdviceRecipe.class);

    //拦截所有以 Service 结尾类的方法
    pointcut callServicePointCut(): call(* *..*(..))
            && !within(MethodTimeAdviceRecipe +);

    /**
     * 在方连接点(业务类方法)周围执行的通知
     */
    Object around(): callServicePointCut(){
        //用 commons-lang 提供的 StopWatch 计时，Spring 也提供了一个 StopWatch
        StopWatch clock = new StopWatch();
        System.out.println("start");
        clock.start(); //计时开始
        Object result = proceed();
        clock.stop();  //计时结束
        System.out.println("AspectJ");
        //显示出方法原型及耗时
        System.out.println("Takes: " + clock.getTime() + " ms [" + thisJoinPoint.getSignature() + "(" +
                thisJoinPoint.getSourceLocation().getLine() + ")]");
        log.info("Takes: " + clock.getTime() + " ms [" + thisJoinPoint.getSignature() + "(" +
                thisJoinPoint.getSourceLocation().getLine() + ")]");

        return result;
    }
}