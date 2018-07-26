package com.zijinshen.datadef;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class DataEngine {
    public static SessionFactory RunDatabaseService () {

        //creating configuration object
        System.out.println("hello");
        Configuration cfg=new Configuration();
        cfg.configure("hibernate.cfg.xml");//populates the data of the configuration file
        //creating session factory object
        SessionFactory factory = cfg.buildSessionFactory();
        return factory;
        //creating session object
        // Session session = factory.openSession();
        // return session;

        // 事务处理
        // creating transaction object
        // Transaction t=session.beginTransaction();
        // return t; // 返回database服务的数据源

        // 下面为样例代码
        // Book e1=new Book();
        // e1.setId("400");
        // e1.setName("ktt");
        // session.persist(e1);//persisting the object
        // t.commit();//transaction is committed
        // session.close();
        // System.out.println("successfully saved");
    }
}
