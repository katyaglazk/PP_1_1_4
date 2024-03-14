package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static final SessionFactory sf = Util.getSessionFactory();
    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        Transaction trCreate = null;
        String sql = """
            CREATE TABLE IF NOT EXISTS users (
            `id` INT NOT NULL AUTO_INCREMENT,
            `name` VARCHAR(45) NULL,
            `lastName` VARCHAR(45) NULL,
            `age` INT NULL,
            PRIMARY KEY (`id`));""";
        try (Session session = sf.openSession()){
            trCreate = session.beginTransaction();
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (trCreate != null) {
                trCreate.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction trCreate = null;
        try (Session session = sf.openSession()) {
            trCreate = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            trCreate.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (trCreate != null) {
                trCreate.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction trCreate = null;
        try (Session session = sf.openSession()) {
            trCreate = session.beginTransaction();
            User user = session.load(User.class, id);
            session.delete(user);
            trCreate.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (trCreate != null) {
                trCreate.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sf.openSession()) {
            return session.createCriteria(User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction trCreate = null;
        try (Session session = sf.openSession()) {
            trCreate = session.beginTransaction();
            session.createSQLQuery("DELETE FROM users").executeUpdate();
            trCreate.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (trCreate != null) {
                trCreate.rollback();
            }
        }
    }
}
