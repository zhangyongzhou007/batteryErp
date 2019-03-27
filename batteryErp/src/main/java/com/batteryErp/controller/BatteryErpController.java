package com.batteryErp.controller;

import com.batteryErp.entity.GetResultEntity;
import com.batteryErp.exception.MException;
import com.batteryErp.utils.HttpRequest;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "batteryErp")
public class BatteryErpController {
    private  static  final Logger log=Logger.getLogger(BatteryErpController.class);
    
    
    /**
     * @Author ZhangFuGui
     * @Description 
     * @Date 8:51 2019/3/27 0027
     * @Param [paramJson]
     * @return net.sf.json.JSONObject
     */
    @RequestMapping(value = "test.do")
    public  @ResponseBody
    JSONObject test(@RequestBody JSONObject paramJson){
        log.info("测试："+paramJson);
        GetResultEntity getResultEntity=GetResultEntity.create200();
        getResultEntity.setData(paramJson);
        return getResultEntity.toJSONObject();
    }
}
