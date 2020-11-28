package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    public User findUserByUsername(String username);
    public void save(User user);
    public User findUserByCode(String code);
    public void updateStatus(User user);

    public User findUserByUsernameAndPassword(String username, String password);
}
