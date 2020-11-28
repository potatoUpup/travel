package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    public boolean findUserByUsername(String username);

    public void save(User user);

    public boolean updateStatus(String code);

    public User login(User user);
}
