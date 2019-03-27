package com.batteryErp.exception;

import com.batteryErp.entity.GetResultEntity;
import net.sf.json.JSONObject;

public class MException extends Exception {
	int statusCode = 0;
	String info = "";
	
	public static MException create(Exception ex){
		MException e = new MException();
		e.statusCode = 10000500;
		e.info = ex.getMessage();
		return e;
	}
	
	public static MException create(int statusCode,String info){
		MException e = new MException();
		e.statusCode = statusCode;
		e.info = info;
		return e;
	}
	
	public static MException create(GetResultEntity result){
		MException e = new MException();
		e.statusCode = result.getStatusCode();
		e.info = result.getInfo();
		return e;
	}
	
	public static MException create500(){
		MException e = new MException();
		e.statusCode = 10000500;
		e.info = "未知错误";
		return e;
	}
	public static MException create500(String info){
		MException e = new MException();
		e.statusCode = 10000500;
		e.info = info;
		return e;
	}
	
	public static MException create480(String info){
		MException e = new MException();
		e.statusCode = 10000480;
		e.info = "鉴权失败，code或token过期";
		return e;
	}
	
	public static MException create401(String info){
		MException e = new MException(); 
		e.statusCode = 10000401;
		e.info = info;
		return e;
	}
	
	public static MException create401(){
		MException e = new MException(); 
		e.statusCode = 10000401;
		e.info = "鉴权失败";
		return e;
	}
	
	public static MException create400(){
		MException e = new MException();
		e.statusCode = 10000400;
		e.info = "请求参数错误";
		return e;
	}
	
	public static MException createAdvExitClassReject(){
		MException e = new MException();
		e.statusCode = 10001001;
		e.info = "班主任不能退出班级";
		return e;
	}
	
	public static MException createClassNameDup(){
		MException e = new MException();
		e.statusCode = 10001002;
		e.info = "慕校班级名已存在";
		return e;
	}
	
	public static MException createMemberDup(){
		MException e = new MException();
		e.statusCode = 10001003;
		e.info = "成员已存在此慕校中";
		return e;
	}
	public static MException createClassMemberDup(){
		MException e = new MException();
		e.statusCode = 10001004;
		e.info = "成员已存在此班级中";
		return e;
	}
	public static MException createSchoolIdEmpty(){
		MException e = new MException();
		e.statusCode = 10001005;
		e.info = "学校ID不能为空";
		return e;
	}
	public static MException createClassIdEmpty(){
		MException e = new MException();
		e.statusCode = 10001006;
		e.info = "班级ID不能为空";
		return e;
	}
	public static MException createMemberIdEmpty(){
		MException e = new MException();
		e.statusCode = 10001007;
		e.info = "成员ID不能为空";
		return e;
	}
	public static MException createProfileIdEmpty(){
		MException e = new MException();
		e.statusCode = 10001008;
		e.info = "用户ID不能为空";
		return e;
	}
	public static MException createRemoveClassRejectOfMemberExist(){
		MException e = new MException();
		e.statusCode = 10001009;
		e.info = "班级下存在成员不能删除";
		return e;
	}
	public static MException createMemberInfoDup(){
		MException e = new MException();
		e.statusCode = 10001010;
		e.info = "班级下成员重复";
		return e;
	}
	
	
	public static MException createLiveUsed(){
		MException e = new MException();
		e.statusCode = 10001011;
		e.info = "直播资源已被使用，不能删除";
		return e;
	}
	public static MException createVodUsed(){
		MException e = new MException();
		e.statusCode = 10001012;
		e.info = "视频资源已被使用，不能删除";
		return e;
	}
	public static MException createVCError(){
		MException e = new MException();
		e.statusCode = 10001013;
		e.info = "验证码错误";
		return e;
	}
	public static MException createOldPwdError(){
		MException e = new MException();
		e.statusCode = 10001014;
		e.info = "原密码错误";
		return e;
	}

	public static MException createNoAuthKey(String msg){
		MException e = new MException();
		e.statusCode = 10001015;
		e.info = "请输入您的" + msg;
		return e;
	}

	public static MException creatAuthKeyError(String msg){
		MException e = new MException();
		e.statusCode = 10001016;
		e.info = msg + "错误";
		return e;
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
	
	public JSONObject toJSONObject(){
		JSONObject obj = new JSONObject();
		obj.put("statusCode", statusCode);
		if (info == null){
			info = "unknown error";
		}
		obj.put("info", info);
		return obj;
	}
	
	@Override
	public String toString() {
		JSONObject obj = this.toJSONObject();
		return obj.toString();
	}
	
//	public static MException createNoContent(){
//		MException e = new MException();
//		e.statusCode = 10000204;
//		e.info = "数据没有更新（增量）";
//		return e;
//	}
//	
//	public static MException createPartialContent(){
//		MException e = new MException();
//		e.statusCode = 10000206;
//		e.info = "数据有更新（增量）";
//		return e;
//	}
	
}
