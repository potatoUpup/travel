package cn.itcast.travel.service.UserServiceimpl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.daoimpl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import org.junit.Test;

public class UserServiceImpl implements UserService {
    UserDao ud = new UserDaoImpl();

    /*

    false 用户不存在
    true  用户已经存在

     */
    @Override
    public boolean findUserByUsername(String username) {
        User user = ud.findUserByUsername(username);
        if (user == null)
            return false;
        else
            return true;
    }

    @Override
    public void save(User user) {
        ud.save(user);
    }

    @Override
    public boolean updateStatus(String code) {
        User user = ud.findUserByCode(code);
        if (user == null)
            return false;
        else {
            ud.updateStatus(user);
            return true;
        }
    }

    @Override
    public User login(User user) {
        return ud.findUserByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

    /*@Test
    public void test()
    {
        boolean zhangsan = this.findUserByUsername("zhangsan");
        System.out.println(zhangsan);
    }*/
}
