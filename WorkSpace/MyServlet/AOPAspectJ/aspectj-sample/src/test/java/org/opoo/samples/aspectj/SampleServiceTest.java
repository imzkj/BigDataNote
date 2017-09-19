package org.opoo.samples.aspectj;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class SampleServiceTest {
    private static SampleService service;

    @BeforeClass
    public static void init() {
        service = new SampleServiceImpl();
    }

    @Test
    public void testAdd() {
        int result = service.add(10, 20);
        System.out.println("现值是：" + result);
        assertNotSame(30, result);
    }

    @Test
    public void testGetPassword() {
        try {
            service.getPassword("admin");
            fail("应该出校异常");
        } catch (Exception e) {
            assertEquals("权限不足", e.getMessage());
        }
    }

    @Test
    public void testAnnotations() throws SecurityException, NoSuchMethodException {
        Method method = SampleServiceImpl.class.getMethod("getPassword", String.class);
        AuthCheck annotation2 = method.getAnnotation(AuthCheck.class);
        assertNotNull(annotation2);
        Method method1 = SampleServiceImpl.class.getMethod("getPassword", String.class);

        //能触发aop
//        try {
//            Class<SampleServiceImpl> sampleServiceImpl = (Class<SampleServiceImpl>) Class.forName("org.opoo.samples.aspectj.SampleServiceImpl");
//            SampleServiceImpl sampleService = sampleServiceImpl.newInstance();
//            sampleService.getPassword("zkj");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        or
//        SampleServiceImpl sampleService = new SampleServiceImpl();
//        sampleService.getPassword("zkj");
//        or
        try {
            SampleServiceImpl sampleService = SampleServiceImpl.class.newInstance();
            sampleService.getPassword("zkj");
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testException() throws Exception {
        service.testFoo();
    }
}
