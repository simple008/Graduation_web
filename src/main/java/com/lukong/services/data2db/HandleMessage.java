package com.lukong.services.data2db;

import java.sql.Connection;


public class HandleMessage {

    public static void handle(String message){
        Connection connection=
                ConnectionFactory.getConnection();
        Insert2DB insert2DB=new Insert2DB(message,connection);
        insert2DB.run();
    }
}
