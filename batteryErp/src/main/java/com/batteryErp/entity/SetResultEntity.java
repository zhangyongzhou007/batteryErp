package com.batteryErp.entity;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SetResultEntity {
    public class SetResultItemEntity {
        private int statusCode;
        private String info;

        public SetResultItemEntity() {
        }

        public SetResultItemEntity(int statusCode, String info) {
            this.statusCode = statusCode;
            this.info = info;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }

    int statusCode = 0;
    String info = "";
    List<SetResultItemEntity> statusList = new ArrayList<SetResultItemEntity>();

    public List<SetResultItemEntity> getStatusList() {
        return statusList;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setStatusList(List<SetResultItemEntity> statusList) {
        this.statusList = statusList;
    }

    public boolean isSuccess() {
        if (statusCode != 10000200) {
            return false;
        }
        for (int i = 0; i < statusList.size(); i++) {
            if (statusList.get(i).statusCode != 10000200) {
                return false;
            }
        }
        return true;
    }

    static public SetResultEntity create200() {
        SetResultEntity result = new SetResultEntity();
        result.statusCode = 10000200;
        result.info = "成功";
        return result;
    }

    static public SetResultEntity create500() {
        SetResultEntity result = new SetResultEntity();
        result.statusCode = 10000500;
        result.info = "结果不存在";
        return result;
    }

    static public SetResultEntity create404() {
        SetResultEntity result = new SetResultEntity();
        result.statusCode = 10000404;
        result.info = "";
        return result;
    }

    static public SetResultEntity create600() {
        SetResultEntity result = new SetResultEntity();
        result.statusCode = 10000600;
        result.info = "token过期或不存在";
        return result;
    }

    static public SetResultEntity create700() {
        SetResultEntity result = new SetResultEntity();
        result.statusCode = 10000700;
        result.info = "用户已存在";
        return result;
    }

    static public SetResultEntity create800() {
        SetResultEntity result = new SetResultEntity();
        result.statusCode = 10000800;
        result.info = "已有用户登录";
        return result;
    }

    static public SetResultEntity create200(int itemCount) {
        SetResultEntity result = new SetResultEntity();
        result.statusCode = 10000200;
        result.info = "成功";
        for (int i = 0; i < itemCount; i++) {
            result.addItem200();
        }
        return result;
    }

    public SetResultItemEntity addItem200() {
        SetResultItemEntity item = new SetResultItemEntity();
        item.statusCode = 10000200;
        item.info = "成功";
        this.statusList.add(item);
        return item;
    }


    static public SetResultEntity resultEntity(int statusCode, String info) {
        SetResultEntity result = new SetResultEntity();
        result.statusCode = statusCode;
        result.info = info;
        return result;
    }


    public JSONObject toJSONObject() {
        JSONObject obj = JSONObject.fromObject(this);
        return obj;
    }

    @Override
    public String toString() {
        JSONObject obj = this.toJSONObject();
        return obj.toString();
    }


}
