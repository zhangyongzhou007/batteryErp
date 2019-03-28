package com.batteryErp.service;

import com.batteryErp.constant.CommonConstant;
import com.batteryErp.entity.BatteryEntity;
import com.batteryErp.entity.vo.PageVo;
import com.batteryErp.exception.MException;
import com.batteryErp.utils.DateUtil;
import com.batteryErp.utils.StringUtils;
import com.mongodb.client.MongoCursor;
import com.star.common.utils.CommonGlobal;
import net.sf.json.JSONObject;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public interface IBatteryService {


    void  addorUpdateBatterInfo(BatteryEntity batteryEntity) throws MException;

    void  deleteBatteryInfo(JSONObject jsonObject) throws  MException;

    List<BatteryEntity> getBatterList(JSONObject jsonObject, PageVo pageVo) throws MException;

    long getBatterCount(JSONObject jsonObject) throws  MException;

    long getCountByCondtion(BatteryEntity batteryEntity)throws  MException;



}
