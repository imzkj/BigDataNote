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


public class SampleServiceImpl implements SampleService {
    public int add(int x, int y) {
        return x + y;
    }

    /**
     * 带自定义Annotation的方法。
     */
    @AuthCheck
    public String getPassword(String username) {
        return "password";
    }

    class myEx extends Exception {
    }

    public void foo(int x) throws myEx {
        throw new myEx();
    }

    public void testFoo() throws myEx {
        SampleServiceImpl myClass = new SampleServiceImpl();
        try {
            myClass.foo(3);
        } catch (myEx e) {
            System.out.println("meet ex in catch " + e);
        }
    }
}
