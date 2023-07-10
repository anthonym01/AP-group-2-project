package com.flowapp.factories;

import com.flowapp.model.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import com.flowapp.logging.Log;


public class SessionFactoryBuilder {
    private static SessionFactory sessionFactory;
    private static Configuration config;


    public static SessionFactory getSessionFactory(){
        if(sessionFactory == null){
            config = new Configuration();

            // Setup hibernate from the config file
            config.configure("hibernate.config.xml");

            // Add the necessary annotated classes
            config.addAnnotatedClass(User.class);
            config.addAnnotatedClass(Employee.class);
            config.addAnnotatedClass(Customer.class);
            config.addAnnotatedClass(Message.class);
            config.addAnnotatedClass(Response.class);
            config.addAnnotatedClass(TemporaryChat.class);

            sessionFactory = config.buildSessionFactory();
        }

        Log.info("Connected to database successfully! [Hibernate]");

        return sessionFactory;
    }

    public static void closeSessionFactory(){
        if(sessionFactory != null){
            sessionFactory.close();
            sessionFactory = null;

            Log.info("Disconnected from the database... [Hibernate]");
        }
    }
}
