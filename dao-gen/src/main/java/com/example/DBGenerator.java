package com.example;


import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class DBGenerator {

    public static void main(String[] args) throws Exception {

        // TODO: 17-4-24 创建数据库
        Schema schema = new Schema(1, "database");
        schema.setDefaultJavaPackageDao("database.dao");
        schema.setDefaultJavaPackageTest("database.test");
        addLocaCity(schema);
        new DaoGenerator().generateAll(schema, "app/src-gen");
    }

    private static void addLocaCity(Schema schema) {
        Entity localCity = schema.addEntity("LocalCity");
        localCity.addIdProperty().autoincrement().primaryKey();
        localCity.addStringProperty("city");

        localCity.setHasKeepSections(true);
    }


}
