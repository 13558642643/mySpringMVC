package com.ssm.zxw.servlet;

import com.ssm.zxw.annotation.*;
import com.ssm.zxw.controller.TestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : ZXW
 * @version : 1.0
 * @date : 2021-01-22 10:22
 * @Description :
 */
public class DispatcherServlet extends HttpServlet {

    List<String> classNames = new ArrayList<String>();


    Map<String ,Object> beans = new HashMap<>();

    Map<String ,Object> handlerMap = new HashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        scanPackageZxw("com.ssm");
        instanceZxw();

        doAutoWired();


        UrlHanding();

        //super.init(config);
    }

    private void UrlHanding(){
        for(Map.Entry<String,Object> entry : beans.entrySet()) {
            Object instance = entry.getValue();
            Class<?> clazz = instance.getClass();
            if (clazz.isAnnotationPresent(ZxwController.class)) {
                ZxwRequestMapping map1 = clazz.getAnnotation(ZxwRequestMapping.class);
                String classPath = map1.value();

                Method[] methods = clazz.getMethods();
                for (Method method : methods){
                    if(method.isAnnotationPresent(ZxwRequestMapping.class)){
                        ZxwRequestMapping map2 = method.getAnnotation(ZxwRequestMapping.class);
                        String methodPath = map2.value();

                        handlerMap.put(classPath+methodPath,method);
                    }

                }
            }
        }


    }

    private void doAutoWired() {
        System.out.println("");

        for(Map.Entry<String,Object> entry : beans.entrySet()){
            Object instance = entry.getValue();
            Class<?> clazz = instance.getClass();
            if(clazz.isAnnotationPresent(ZxwController.class)){
                Field[] fields = clazz.getDeclaredFields();
                for (Field field :fields){
                    if(field.isAnnotationPresent(ZxwAutowired.class)){
                        ZxwAutowired zxwAutowired = field.getAnnotation(ZxwAutowired.class);
                        String key = zxwAutowired.value();

                        Object ins = beans.get(key);

                        field.setAccessible(true);

                        try {
                            field.set(instance,ins);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }else {
                continue;
            }


        }

    }

    private void instanceZxw() {
        for(String className : classNames){
            String cn = className.replace(".class","");

            try {
                Class<?> clazz = Class.forName(cn);

                if(clazz.isAnnotationPresent(ZxwController.class)){

                        Object instance = clazz.newInstance();

                    ZxwRequestMapping mapping = clazz.getAnnotation(ZxwRequestMapping.class);
                    String key = mapping.value();

                        beans.put(key,instance);

                }else if(clazz.isAnnotationPresent(ZxwService.class)){
                    Object instance1 = clazz.newInstance();

                    ZxwService service = clazz.getAnnotation(ZxwService.class);
                    String key = service.value();

                    beans.put(key,instance1);
                }else {
                    continue;
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }

    private void scanPackageZxw(String basePackage) {
        URL url = this.getClass().getClassLoader().getResource("/"+basePackage.replaceAll("\\.","/"));


        String fileStr = url.getFile();
        System.out.println("fileStr");
        System.out.println(fileStr);
        File file = new File(fileStr);

        String[] filesStr = file.list();

        for(String path : filesStr){

            File filePath = new File(fileStr+path);
            if(filePath.isDirectory()){
                scanPackageZxw(basePackage+"."+path);
            }else {
                classNames.add(basePackage+"."+filePath.getName());
            }

        }



    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        System.out.println("【doGet】");
        doPost(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("【doPost】");
        String url = req.getRequestURI();

       String context = req.getContextPath();

       String path = url.replace(context,"");

       Method method =  (Method)handlerMap.get(path);

        TestController testController = (TestController)beans.get("/"+path.split("/")[1]);

        Object args[] = hand(req,resp,method);

        try {
            method.invoke(testController,args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //  super.doPost(req, resp);
    }

    private static Object[] hand(HttpServletRequest req, HttpServletResponse resp,Method method){
        //拿到有哪些参数
        Class<?>[] paramClazzs = method.getParameterTypes();
        //根据参数个实例化对应的参数
        Object[] args = new Object[paramClazzs.length];
        int age_i = 0;
        int index = 0;
        for(Class<?> paramClazz:paramClazzs ){
            if(ServletRequest.class.isAssignableFrom(paramClazz)){
                args[age_i++] = req;
            }
            if(ServletResponse.class.isAssignableFrom(paramClazz)){
                args[age_i++] = resp;
            }

            Annotation[] paramAns = method.getParameterAnnotations()[index];

            if(paramAns.length > 0){
                for(Annotation annotation : paramAns){
                    if(ZxwRequestParam.class.isAssignableFrom(annotation.getClass())){
                        ZxwRequestParam requestParam = (ZxwRequestParam) annotation;
                        args[age_i++] = req.getParameter(requestParam.value());
                    }
                }
            }
            index++;
        }
        return args;
    }

}
