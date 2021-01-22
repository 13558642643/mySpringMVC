package com.ssm.zxw.servlet;

import com.ssm.zxw.annotation.ZxwController;
import com.ssm.zxw.annotation.ZxwRequestMapping;
import com.ssm.zxw.annotation.ZxwService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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

    @Override
    public void init(ServletConfig config) throws ServletException {
        scanPackageZxw("com.ssm");
        instanceZxw();
        doAutoWired();
        //super.init(config);
    }

    private void doAutoWired() {
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
        URL url = this.getClass().getClassLoader().getResource("/"+basePackage.replace("\\.","/"));


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
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
