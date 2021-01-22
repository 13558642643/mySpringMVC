package com.ssm.zxw.service.impl;

import com.ssm.zxw.annotation.ZxwService;
import com.ssm.zxw.service.TestService;

/**
 * @author : ZXW
 * @version : 1.0
 * @date : 2021-01-22 10:12
 * @Description :
 */
@ZxwService("zxwService")
public class TestServiceImpl implements TestService {

    @Override
    public String getMsg(String name) {
        StringBuilder str = new StringBuilder();
        str.append("进来了，");
        str.append(name);
        return str.toString();
    }
}
