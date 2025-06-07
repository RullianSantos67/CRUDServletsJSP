package model.dao;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import model.ModelException;
import model.User;

public class MySQLUserDAO implements UserDAO {

    @Override
    public boolean save(User user) throws ModelException {
        DBHandler db = new DBHandler();
        String sqlInsert = "INSERT INTO users VALUES (DEFAULT, ?, ?, ?, ?);";
        db.prepareStatement(sqlInsert);
        db.setString(1, user.getNome());
        db.setString(2, user.getSexo());
        db.setString(3, user.getEmail());
        db.setString(4, user.getPassword());
        return db.executeUpdate() > 0;
    }

    @Override
    public boolean update(User user) throws ModelException {
        DBHandler db = new DBHandler();
        String sqlUpdate = ""
            + "UPDATE users SET "
            + "nome = ?, "
            + "sexo = ?, "
            + "email = ?, "
            + "password = ? "
            + "WHERE id = ?";
        db.prepareStatement(sqlUpdate);
        db.setString(1, user.getNome());
        db.setString(2, user.getSexo());
        db.setString(3, user.getEmail());
        db.setString(4, user.getPassword());
        db.setInt(5, user.getId());
        return db.executeUpdate() > 0;
    }

    @Override
    public boolean delete(User user) throws ModelException {
        DBHandler db = new DBHandler();
        String sqlDelete = "DELETE FROM users WHERE id = ?;";
        db.prepareStatement(sqlDelete);
        db.setInt(1, user.getId());
        try {
            return db.executeUpdate() > 0;
        } catch (ModelException e) {
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                return false;
            }
            throw e;
        }
    }

    @Override
    public List<User> listAll() throws ModelException {
        return getAll();
    }


    @Override
    public User findById(int id) throws ModelException {
        DBHandler db = new DBHandler();
        String sql = "SELECT * FROM users WHERE id = ?";
        db.prepareStatement(sql);
        db.setInt(1, id);
        db.executeQuery();
        if (db.next()) {
            return createUser(db);
        }
        return null;
    }

    private User createUser(DBHandler db) throws ModelException {
        User u = new User(db.getInt("id"), null, null, null, null);
        u.setNome(db.getString("nome"));
        u.setSexo(db.getString("sexo"));
        u.setEmail(db.getString("email"));
        u.setPassword(db.getString("password"));
        return u;
    }

    @Override
    public User get(int id) throws ModelException {
        DBHandler db = new DBHandler();
        String sql = "SELECT * FROM users WHERE id = ?";
        db.prepareStatement(sql);
        db.setInt(1, id);
        db.executeQuery();
        if (db.next()) {
            return createUser(db);
        }
        return null;
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws ModelException {
        DBHandler db = new DBHandler();
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        db.prepareStatement(sql);
        db.setString(1, email);
        db.setString(2, password);
        db.executeQuery();
        if (db.next()) {
            return createUser(db);
        }
        return null;
    }

    @Override
    public List<User> getAll() throws ModelException {
        DBHandler db = new DBHandler();
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        db.createStatement();
        db.executeQuery(sql);
        while (db.next()) {
            users.add(createUser(db));
        }
        return users;
    }

}
