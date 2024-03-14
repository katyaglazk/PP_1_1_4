package jm.task.core.jdbc;


import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        Util.getConnection();
        UserDao userDaoHibernate = new UserDaoJDBCImpl();
        //UserDao userDaoHibernate = new UserDaoHibernateImpl();
        userDaoHibernate.createUsersTable();

        userDaoHibernate.saveUser("Name1", "LastName1", (byte) 20);
        userDaoHibernate.saveUser("Name2", "LastName2", (byte) 25);
        userDaoHibernate.saveUser("Name3", "LastName3", (byte) 31);
        userDaoHibernate.saveUser("Name4", "LastName4", (byte) 38);

        userDaoHibernate.removeUserById(1);
        userDaoHibernate.getAllUsers().stream().forEach(System.out::println);
        userDaoHibernate.cleanUsersTable();
        userDaoHibernate.dropUsersTable();


    }
}
