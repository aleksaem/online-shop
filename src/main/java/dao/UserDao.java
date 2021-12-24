package dao;

import entity.User;

public interface UserDao {
    void addUser(User user);

    User findUserByEmail(String email);
}
