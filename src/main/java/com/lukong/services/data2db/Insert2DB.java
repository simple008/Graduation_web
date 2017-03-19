package com.lukong.services.data2db;

import java.sql.Connection;


public class Insert2DB implements Runnable{

    private String message;
    Connection connection;

    public Insert2DB(String message,Connection connection){
        super();
        this.message=message;
        this.connection=connection;
    }
    @Override
    public void run() {
        /*利用SQL将数据持久化*/
    }
}
