package cn.itcast.travel.dao.daoimpl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserDaoImpl implements UserDao {
    JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public User findUserByUsername(String username) {
        String sql="select * from tab_user where username=?";
        User user;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),username);
            return user;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public void save(User user) {
        String sql="insert into tab_user(username,password,name,birthday,sex,telephone,email,code,status) values(?,?,?,?,?,?,?,?,?)";
        try
        {
            int update = template.update(sql,
                    user.getUsername(),
                    user.getPassword(),
                    user.getName(),
                    user.getBirthday(),
                    user.getSex(),
                    user.getTelephone(),
                    user.getEmail(),
                    user.getCode(),
                    user.getStatus());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public User findUserByCode(String code) {
        String sql="select * from tab_user where code=?";
        User user;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),code);
            return user;
        }
        catch (Exception e)
        {
            return null;
        }
    }


    @Override
    public void updateStatus(User user) {
        try
        {
            String sql="update tab_user set status = 'Y' where username = ?";
            int update = template.update(sql, user.getUsername());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        String sql="select * from tab_user where username=? and password=?";
        User user;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),username,password);
            return user;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    @Test
    public void test()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        String s = simpleDateFormat.format(date);
        User user=new User(1,"10010","123","张三",s,"男","10086","10086@qq.com","1","2");
        this.save(user);
    }

}
