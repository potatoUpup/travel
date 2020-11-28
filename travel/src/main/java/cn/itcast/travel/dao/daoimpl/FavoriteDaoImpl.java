package cn.itcast.travel.dao.daoimpl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate jdbcTemplate=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public boolean isFavorite(int uid, int rid) {
        Favorite favorite =null;
        try {
            String sql="select * from tab_favorite where uid = ? and rid = ? ";
            favorite = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), uid, rid);
        }
        catch (Exception e)
        {

        }
        return favorite==null ? false : true;
    }



    public void addFavorite(int uid, int rid)
    {
        try {
            String sql="insert into tab_favorite values(?, ? , ?)";
            jdbcTemplate.update(sql,rid,new Date(),uid);
        }
        catch (Exception e)
        {
            System.out.println("添加失败");
        }
    }






@Test
    public void test()
    {
        /*boolean favorite = this.isFavorite("46", 1);
        System.out.println(favorite);*/
        this.addFavorite(46,1);
    }
}
