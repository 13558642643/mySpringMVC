package com.ssm.zxw.controller;

import com.ssm.zxw.annotation.ZxwAutowired;
import com.ssm.zxw.annotation.ZxwController;
import com.ssm.zxw.annotation.ZxwRequestMapping;
import com.ssm.zxw.annotation.ZxwRequestParam;
import com.ssm.zxw.service.TestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author : ZXW
 * @version : 1.0
 * @date : 2021-01-22 10:04
 * @Description :
 */
@ZxwController
@ZxwRequestMapping("/zxw")
public class TestController {

    @ZxwAutowired("zxwService")
    private TestService testService;

    @ZxwRequestMapping("/query")
    public void query(HttpServletResponse response, HttpServletRequest request,
                      @ZxwRequestParam("name") String name,@ZxwRequestParam("age") String age){
        try {
            PrintWriter pw = response.getWriter();
            String msg = testService.getMsg(name);
            msg += age;
            pw.write(msg);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}
