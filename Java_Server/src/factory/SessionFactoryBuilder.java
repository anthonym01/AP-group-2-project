//8. Create a new class called SessionFactoryBuilder inside the factories package.

package factory;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import models.*;

public class SessionFactoryBuilder {
    /*
     * 9. Inside the SessionFactoryBuilder create a private static attribute of type
     * SessionFactory (org.hibernate.SessionFactory) and two public static methods
     * called getSessionFactory (accepts no parameters and returns a
     * SessionFactory);
     * and closeSessionFactory (accepts no parameters and returns nothing).
     */

    private static SessionFactory sessionFactory = null;

    public static SessionFactory getSessionFactory() {
        /*
         * 10. Complete the getSessionFactory method by first checking if the
         * SessionFactory
         * attribute is null, if it is, then instantiate it using the following
         * statement: new
         * Configuration().configure("hibernate.cfg.xml").addAnnotatedClass
         * (Student.class).buildSessionFactory();
         * (org.hibernate.cfg.Configuration). Complete the method by returning the
         * factory object.
         */
        try {
            if (sessionFactory == null) {
                sessionFactory = new Configuration().configure("hibernate.cfg.xml")
                        .buildSessionFactory();
            }
        } catch (RuntimeException e) {
            System.out.println(e.toString());
        }
        return sessionFactory;
    }

    public static  void closeSessionFactory() {
        /*
         * 11. Complete the closeSessionFactory method by checking if the SessionFactory
         * attribute is not null, if it is, then using the attribute, call its close
         * method.
         */
        // conveyor_belt.close();
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
