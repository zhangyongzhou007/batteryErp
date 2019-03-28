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
import org.bson.BSON;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BatteryAddInfoProvider extends WebAbstractProvider {

    private static final Logger logger= Logger.getLogger(BatteryAddInfoProvider.class);

    private static final String tableName = "t_battery_repertory";

    private static BatteryAddInfoProvider instance = new BatteryAddInfoProvider();

    private MongoCollection<Document> mongoCollection;

    public BatteryAddInfoProvider() {
        super();
        mongoCollection = loadCollection();
    }

    public static BatteryAddInfoProvider getInstance() {
        return instance;
    }

    @Override
    protected String getCollectionName() {
        return tableName;
    }

    @Override
    protected Map<Document, IndexOptions> getIndex() {
        Map<Document, IndexOptions> index = new HashMap<Document, IndexOptions>();
        index.put(new Document(BatteryEntity.REPERTORY_RECORD_ID, 1), new IndexOptions());
        return index;
    }

    public Document initDocument(BatteryEntity batteryEntity) {
        Document document = new Document();
        if (!StringUtils.isBlank(batteryEntity.getRepertoryRecordId())) {
            document.append(BatteryEntity.REPERTORY_RECORD_ID, batteryEntity.getRepertoryRecordId());
        }


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

            document.append(BatteryEntity.REPERTORY_CHANGE_NUM, batteryEntity.getRepertoryChangeNum());

        return document;
    }

    public void  addRepertryRecode(BatteryEntity batteryEntity){
        logger.info("addorUpdateBatterInfo :"+batteryEntity);

            String moosNow = DateUtil.getMoosNow();
            batteryEntity.setRepertoryRecordId(CommonGlobal.getUUID());
            batteryEntity.setCreateTime(moosNow);
            mongoCollection.insertOne(initDocument(batteryEntity));
    }

    public  List<BatteryEntity> getBatteryRepertryRecodeList(JSONObject jsonObject, PageVo pageVo){
        String filterStr = jsonObject.getString("filter");
        Document filter=new Document();
        if (!StringUtils.isBlank(jsonObject.getString(BatteryEntity.COMPANY))){
            filter.append(BatteryEntity.COMPANY,jsonObject.getString(BatteryEntity.COMPANY));
        }else {
            filter.append(BatteryEntity.COMPANY,CommonConstant.COMPANY);
        }

        if (!StringUtils.isBlank(filterStr)){

            List<Document> bsonList=new ArrayList<>();
            bsonList.add(new Document(BatteryEntity.BATTERY_NAME,new Document("$regex",filterStr)));
            bsonList.add(new Document(BatteryEntity.MODEL,new Document("$regex",filterStr)));

            filter.append("$or",bsonList);
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

    public  long getBatteryRepertryRecodeCount(JSONObject jsonObject){
        String filterStr = jsonObject.getString("filter");
        Document filter=new Document();


        if (!StringUtils.isBlank(jsonObject.getString(BatteryEntity.COMPANY))){
            filter.append(BatteryEntity.COMPANY,jsonObject.getString(BatteryEntity.COMPANY));
        }else {
            filter.append(BatteryEntity.COMPANY,CommonConstant.COMPANY);
        }

        if (!StringUtils.isBlank(filterStr)){

            List<Document> bsonList=new ArrayList<>();
            bsonList.add(new Document(BatteryEntity.BATTERY_NAME,new Document("$regex",filterStr)));
            bsonList.add(new Document(BatteryEntity.MODEL,new Document("$regex",filterStr)));

            filter.append("$or",bsonList);
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
        batteryEntity.setRepertoryChangeNum(next.getInteger(BatteryEntity.REPERTORY_CHANGE_NUM));
        if (batteryEntity.getRepertoryChangeNum()<0){
            String saleSigle = -(Double.parseDouble(batteryEntity.getBatteryPrice()) * batteryEntity.getRepertoryChangeNum()) + "";
            batteryEntity.setSalesSingle(saleSigle);
        }

        return batteryEntity;
    }

}
