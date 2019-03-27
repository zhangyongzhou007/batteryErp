package com.batteryErp.entity;


import net.sf.json.JSONObject;

import java.io.Serializable;

public class BatteryEntity implements Serializable {
    //id
    private  String   batteryId;
    public  static  final  String  BATTERY_ID="batteryId";

    //公司
    private  String company;
    public  static  final  String COMPANY="company";

    //电瓶所属公司id
    private  String   batteryCompanyId;
    public  static  final  String  BATTERY_COMPANY_ID="batteryCompanyId";

    //电瓶所属公司名称
    private  String   batteryCompanyName;
    public  static  final  String  BATTERY_COMPANY_NAME="batteryCompanyName";

    //电瓶名称
    private  String   batteryName;
    public  static  final  String  BATTERY_NAME="batteryName";

    //型号
    private  String  model;
    public  static  final  String MODEL="model";

    //电瓶剩余数量
    private  String   batteryRemainNum;
    public  static  final  String  BATTERY_REMAIN_NUM="batteryRemainNum";

    //电瓶的价格
    private  String   batteryPrice;
    public  static  final  String  BATTERYP_RICE="batteryPrice";

    private  String   createTime;
    public  static  final  String  CREATE_TIME="createTime";

    private  String   updateTime;
    public  static  final  String  UPDATE_TIME="updateTime";

    /*删除状态 0：未删除  1：已删除*/
    private String deleteState;
    public  static  final  String DELETE_STATE="deleteState";

    public String getBatteryId() {
        return batteryId;
    }

    public void setBatteryId(String batteryId) {
        this.batteryId = batteryId;
    }

    public String getBatteryCompanyId() {
        return batteryCompanyId;
    }

    public void setBatteryCompanyId(String batteryCompanyId) {
        this.batteryCompanyId = batteryCompanyId;
    }

    public String getBatteryCompanyName() {
        return batteryCompanyName;
    }

    public void setBatteryCompanyName(String batteryCompanyName) {
        this.batteryCompanyName = batteryCompanyName;
    }

    public String getBatteryName() {
        return batteryName;
    }

    public void setBatteryName(String batteryName) {
        this.batteryName = batteryName;
    }

    public String getBatteryRemainNum() {
        return batteryRemainNum;
    }

    public void setBatteryRemainNum(String batteryRemainNum) {
        this.batteryRemainNum = batteryRemainNum;
    }

    public String getBatteryPrice() {
        return batteryPrice;
    }

    public void setBatteryPrice(String batteryPrice) {
        this.batteryPrice = batteryPrice;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(String deleteState) {
        this.deleteState = deleteState;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public JSONObject toJSONObject(){
     return    JSONObject.fromObject(this);
    }

    @Override
    public java.lang.String toString() {
        return toJSONObject().toString();
    }
}
