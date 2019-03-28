package com.batteryErp.dao.mongo;


import com.batteryErp.constant.CommonConstant;
import com.batteryErp.entity.BatteryEntity;
import com.batteryErp.entity.vo.PageVo;
import com.batteryErp.utils.DateUtil;
import com.batteryErp.utils.StringUtils;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.IndexOptions;
import com.star.common.utils.CommonGlobal;
import com.star.database.mongo.WebAbstractProvider;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.bson.Document;

import java.util.*;

public class BatteryProvider extends WebAbstractProvider {

    private static final Logger logger= Logger.getLogger(BatteryProvider.class);

    private static final String tableName = "t_battery";

    private static BatteryProvider instance = new BatteryProvider();

    private MongoCollection<Document> mongoCollection;

    public BatteryProvider() {
        super();
        mongoCollection = loadCollection();
    }

    public static BatteryProvider getInstance() {
        return instance;
    }

    @Override
    protected String getCollectionName() {
        return tableName;
    }

    @Override
    protected Map<Document, IndexOptions> getIndex() {
        Map<Document, IndexOptions> index = new HashMap<Document, IndexOptions>();
        index.put(new Document(BatteryEntity.BATTERY_ID, 1), new IndexOptions());
        return index;
    }

    public Document initDocument(BatteryEntity batteryEntity) {
        Document document = new Document();
        if (!StringUtils.isBlank(batteryEntity.getBatteryId())) {
            document.append(BatteryEntity.BATTERY_ID, batteryEntity.getBatteryId());
        }

        if (!StringUtils.isBlank(batteryEntity.getBatteryCompanyId())) {
            document.append(BatteryEntity.BATTERY_COMPANY_ID, batteryEntity.getBatteryCompanyId());
        }

        if (!StringUtils.isBlank(batteryEntity.getBatteryCompanyName())) {
            document.append(BatteryEntity.BATTERY_COMPANY_NAME, batteryEntity.getBatteryCompanyName());
        }

        if (!StringUtils.isBlank(batteryEntity.getCreateTime())) {
            document.append(BatteryEntity.CREATE_TIME, batteryEntity.getCreateTime());
        }

        if (!StringUtils.isBlank(batteryEntity.getUpdateTime())) {
            document.append(BatteryEntity.UPDATE_TIME, batteryEntity.getUpdateTime());
        }

        if (!StringUtils.isBlank(batteryEntity.getBatteryName())) {
            document.append(BatteryEntity.BATTERY_NAME, batteryEntity.getBatteryName());
        }

        if (!StringUtils.isBlank(batteryEntity.getBatteryRemainNum())) {
            document.append(BatteryEntity.BATTERY_REMAIN_NUM, batteryEntity.getBatteryRemainNum());
        }

            document.append(BatteryEntity.COMPANY, CommonConstant.COMPANY);

        if (!StringUtils.isBlank(batteryEntity.getModel())) {
            document.append(BatteryEntity.MODEL, batteryEntity.getModel());
        }

        if (!StringUtils.isBlank(batteryEntity.getBatteryPrice())){
            document.append(BatteryEntity.BATTERYP_RICE, batteryEntity.getBatteryPrice());
        }
        document.append(BatteryEntity.DELETE_STATE, CommonConstant.DELETE_STATE_NORMAL);


        return document;
    }

    public void  addorUpdateBatterInfo(BatteryEntity batteryEntity){
        logger.info("addorUpdateBatterInfo :"+batteryEntity);
        String batteryId = batteryEntity.getBatteryId();
        //添加
        if (StringUtils.isBlank(batteryId)){
            String moosNow = DateUtil.getMoosNow();
            batteryEntity.setBatteryId(CommonGlobal.getUUID());
            batteryEntity.setUpdateTime(moosNow);
            batteryEntity.setCreateTime(moosNow);
            mongoCollection.insertOne(initDocument(batteryEntity));
            return;
        }

        //编辑
        Document update=new Document();

        //名字
        if (!StringUtils.isBlank(batteryEntity.getBatteryName())) {
            update.append(BatteryEntity.BATTERY_NAME, batteryEntity.getBatteryName());
        }

        //库存
        if (!StringUtils.isBlank(batteryEntity.getBatteryRemainNum())) {
            update.append(BatteryEntity.BATTERY_REMAIN_NUM, batteryEntity.getBatteryRemainNum());
        }

        //型号
        if (!StringUtils.isBlank(batteryEntity.getModel())) {
            update.append(BatteryEntity.MODEL, batteryEntity.getModel());
        }

        //价格
        if (!StringUtils.isBlank(batteryEntity.getBatteryPrice())){
            update.append(BatteryEntity.BATTERYP_RICE, batteryEntity.getBatteryPrice());
        }
        batteryEntity.setUpdateTime(DateUtil.getMoosNow());
        Document $set = mongoCollection.findOneAndUpdate(new Document(BatteryEntity.BATTERY_ID, batteryEntity.getBatteryId()),
                new Document("$set", update));

        logger.info("更新返回参数："+$set);
    }

    public  void  deleteBatteryInfo(List<String> batteryIds){
        logger.info("删除参数："+batteryIds);
        for (String batteryId:batteryIds){
            Document update=new Document();
            update.append(BatteryEntity.DELETE_STATE, CommonConstant.DELETE_STATE_DELETE).append(BatteryEntity.UPDATE_TIME,DateUtil.getMoosNow());
            Document $set = mongoCollection.findOneAndUpdate(new Document(BatteryEntity.BATTERY_ID, batteryId),
                    new Document("$set", update));
        }
    }

    public  List<BatteryEntity> getBatterList(JSONObject jsonObject, PageVo pageVo){
        Document filter=new Document();
        filter.append(BatteryEntity.DELETE_STATE,CommonConstant.DELETE_STATE_NORMAL);
        if (!StringUtils.isBlank(jsonObject.getString(BatteryEntity.COMPANY))){
            filter.append(BatteryEntity.COMPANY,jsonObject.getString(BatteryEntity.COMPANY));
        }else {
            filter.append(BatteryEntity.COMPANY,CommonConstant.COMPANY);
        }

        MongoCursor<Document> iterator = null;
        if (pageVo != null) {

            iterator = mongoCollection.find(filter)
                    .skip((pageVo.getCurrPage() - 1) * pageVo.getPageSize())
                    .limit(pageVo.getPageSize())
                    .sort(new Document(BatteryEntity.CREATE_TIME, -1)).iterator();
        } else {
            iterator = mongoCollection.find(filter)
                    .sort(new Document(BatteryEntity.CREATE_TIME, -1)).iterator();
        }

        List<BatteryEntity> batteryEntityList = new ArrayList<>();
        while (iterator.hasNext()) {
            BatteryEntity campusEntity = docToEntity(iterator.next());
            batteryEntityList.add(campusEntity);
        }

        return batteryEntityList;
    }

    public  long getBatterCount(JSONObject jsonObject){
        Document filter=new Document();
        filter.append(BatteryEntity.DELETE_STATE,CommonConstant.DELETE_STATE_NORMAL);
        if (!StringUtils.isBlank(jsonObject.getString(BatteryEntity.COMPANY))){
            filter.append(BatteryEntity.COMPANY,jsonObject.getString(BatteryEntity.COMPANY));
        }else {
            filter.append(BatteryEntity.COMPANY,CommonConstant.COMPANY);
        }

        return mongoCollection.count(filter);

    }

    private BatteryEntity docToEntity(Document next) {
        BatteryEntity batteryEntity=new BatteryEntity();
        batteryEntity.setBatteryId(next.getString(BatteryEntity.BATTERY_ID));
        batteryEntity.setModel(next.getString(BatteryEntity.MODEL));
        batteryEntity.setBatteryName(next.getString(BatteryEntity.BATTERY_NAME));
        batteryEntity.setBatteryPrice(next.getString(BatteryEntity.BATTERYP_RICE));
        batteryEntity.setCreateTime(next.getString(BatteryEntity.CREATE_TIME));
        batteryEntity.setBatteryRemainNum(next.getString(BatteryEntity.BATTERY_REMAIN_NUM));

        return batteryEntity;
    }

    public  long getCountByCondtion(JSONObject jsonObject){
        Document filter=new Document();
        if (!StringUtils.isBlank(jsonObject.getString(BatteryEntity.BATTERY_NAME))){
            filter.append(BatteryEntity.BATTERY_NAME,jsonObject.getString(BatteryEntity.BATTERY_NAME));
        }

        if (!StringUtils.isBlank(jsonObject.getString(BatteryEntity.MODEL))){
            filter.append(BatteryEntity.MODEL,jsonObject.getString(BatteryEntity.MODEL));
        }

        return   mongoCollection.count(filter);
    }

}
