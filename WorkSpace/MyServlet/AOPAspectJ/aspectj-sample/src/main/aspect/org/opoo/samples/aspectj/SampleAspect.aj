/*
 * $Id$
 *
 * Copyright 2002-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.opoo.samples.aspectj;

import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

public aspect SampleAspect {

    /**
     * 切入点：SampleService继承树中所有 public 且以 add 开头的方法。SampleServiceImpl#add(int,int)方法满足这个条件。
     */
    public pointcut serviceAddMethods(): execution(public * org.opoo.samples.aspectj.SampleService+.add*(..));

    Object around(): serviceAddMethods(){
        Object oldValue = proceed();
        System.out.println("原值是：" + oldValue);
        return Integer.MIN_VALUE;
    }

    /**
     * 切入点：SampleService继承树中所有标注了AuthCheck的方法。
     */
    public pointcut serviceAuthCheckAnnotatedMethods(): execution(* org.opoo.samples.aspectj.SampleService+.*(..)) && @annotation(AuthCheck);

    before(): serviceAuthCheckAnnotatedMethods(){
        if (1 == 1) {//权限检查代码
            System.out.println("zfsdkjfnhs");
            throw new IllegalStateException("权限不足");
        }
    }

    /**
     * 切入点：SampleService继承树中所有 public 的方法。
     */
    public pointcut serviceAllPublicMethods(): execution(public * org.opoo.samples.aspectj.SampleService+.*(..));

    after(): serviceAllPublicMethods(){
        MethodSignature methodSignature = (MethodSignature) thisJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        System.out.println("[LOG] 方法被调用了: " + method);
    }

    pointcut exHandler(): handler(Exception+);

    //pointcut point(): call(void aspectj.MyClass.foo(..));

    before(): exHandler()
            {
                System.out.println("handle ex");
                System.out.println(thisJoinPoint.getSourceLocation());
                System.out.println(thisJoinPoint.getSignature());
            }
}
