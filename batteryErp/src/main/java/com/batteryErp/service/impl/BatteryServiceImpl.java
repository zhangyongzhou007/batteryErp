package com.batteryErp.service.impl;

import com.batteryErp.dao.mongo.BatteryProvider;
import com.batteryErp.entity.BatteryEntity;
import com.batteryErp.entity.vo.PageVo;
import com.batteryErp.exception.MException;
import com.batteryErp.service.IBatteryService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatteryServiceImpl implements IBatteryService {
    @Override
    public void addorUpdateBatterInfo(BatteryEntity batteryEntity) {
        BatteryProvider.getInstance().addorUpdateBatterInfo(batteryEntity);
    }

    @Override
    public void deleteBatteryInfo(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray(BatteryEntity.BATTERY_ID);
        List<String> list = (List<String>) JSONArray.toCollection(jsonArray);
        BatteryProvider.getInstance().deleteBatteryInfo(list);
    }

    @Override
    public List<BatteryEntity> getBatterList(JSONObject jsonObject, PageVo pageVo) {
        return BatteryProvider.getInstance().getBatterList(jsonObject,pageVo);
    }

    @Override
    public long getBatterCount(JSONObject jsonObject) throws MException {
        return BatteryProvider.getInstance().getBatterCount(jsonObject);
    }

    @Override
    public long getCountByCondtion(BatteryEntity batteryEntity) throws MException {

        return BatteryProvider.getInstance().getCountByCondtion(batteryEntity.toJSONObject());
    }
}

