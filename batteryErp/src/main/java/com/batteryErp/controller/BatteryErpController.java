package com.batteryErp.controller;

import com.batteryErp.entity.BatteryEntity;
import com.batteryErp.entity.GetResultEntity;
import com.batteryErp.entity.vo.PageVo;
import com.batteryErp.exception.MException;
import com.batteryErp.service.IBatteryService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "batteryErp")
public class BatteryErpController {
    private  static  final Logger log=Logger.getLogger(BatteryErpController.class);

    @Autowired
    private IBatteryService batteryService;

    /**
     * @Author ZhangFuGui
     * @Description 
     * @Date 8:51 2019/3/27 0027
     * @Param [paramJson]
     * @return net.sf.json.JSONObject
     *//*
    @RequestMapping(value = "test.do")
    public  @ResponseBody
    JSONObject test(@RequestBody JSONObject paramJson){
        log.info("测试："+paramJson);
        GetResultEntity getResultEntity=GetResultEntity.create200();
        getResultEntity.setData(paramJson);
        return getResultEntity.toJSONObject();
    }*/

    @RequestMapping(value = "addorUpdateBatterInfo.do")
    public  @ResponseBody
    JSONObject   addorUpdateBatterInfo(@RequestBody BatteryEntity batteryEntity){
        try {
            long countByCondtion = batteryService.getCountByCondtion(batteryEntity);
            if (countByCondtion>0){
                return MException.create500("已存在名为："+batteryEntity.getBatteryName()+" 型号为："+batteryEntity.getModel()+"物件！").toJSONObject();
            }
            batteryService.addorUpdateBatterInfo(batteryEntity);
            GetResultEntity getResultEntity = GetResultEntity.create200();
            return getResultEntity.toJSONObject();
        } catch (MException e) {
            log.error("\r\nMException e:\r\n" + e.getMessage());
            return e.toJSONObject();
        } catch (Exception e) {
            log.error("\r\nException e:\r\n" + MException.create500(e.getMessage()));
            return MException.create500(e.getMessage()).toJSONObject();
        }
    }

    @RequestMapping(value = "deleteBatteryInfo.do")
    public  @ResponseBody
    JSONObject   deleteBatteryInfo(@RequestBody JSONObject jsonObject){
        try {
            batteryService.deleteBatteryInfo(jsonObject);
            GetResultEntity getResultEntity = GetResultEntity.create200();
            return getResultEntity.toJSONObject();
        } catch (MException e) {
            log.error("\r\nMException e:\r\n" + e.getMessage());
            return e.toJSONObject();
        } catch (Exception e) {
            log.error("\r\nException e:\r\n" + MException.create500(e.getMessage()));
            return MException.create500(e.getMessage()).toJSONObject();
        }
    }

    @RequestMapping(value = "getBatterList.do")
    public  @ResponseBody
    JSONObject  getBatterList(@RequestBody JSONObject jsonObject){
        try {
            PageVo pageVo=new PageVo();
            long batterCount = batteryService.getBatterCount(jsonObject);
            pageVo.setTotalRow((int) batterCount);
            List<BatteryEntity> batteryEntityList = batteryService.getBatterList(jsonObject,pageVo);
            GetResultEntity getResultEntity = GetResultEntity.create200();
            getResultEntity.setPageVo(pageVo);
            getResultEntity.setData(batteryEntityList);
            return getResultEntity.toJSONObject();
        } catch (MException e) {
            log.error("\r\nMException e:\r\n" + e.getMessage());
            return e.toJSONObject();
        } catch (Exception e) {
            log.error("\r\nException e:\r\n" + MException.create500(e.getMessage()));
            return MException.create500(e.getMessage()).toJSONObject();
        }
    }
}
