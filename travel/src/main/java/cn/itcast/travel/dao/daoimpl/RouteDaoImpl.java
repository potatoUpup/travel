package cn.itcast.travel.dao.daoimpl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteDaoImpl implements RouteDao {
    JdbcTemplate jdbcTemplate=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Route findOne(int rid) {
        String sql="select * from tab_route where rid = ?";
        Route route = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
        return route;
    }

    public List<RouteImg> findRouteImage(int rid)
    {
        String sql="select * from tab_route_img where rid = ? ";
        List<RouteImg> routeImgList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class),rid);
        return routeImgList;
    }

    @Override
    public Seller findSeller(int sid) {
        String sql ="select * from tab_seller where sid = ?";
        Seller seller = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Seller>(Seller.class), sid);
        return seller;
    }

    @Override
    public Category findCategory(int cid) {
        String sql="select * from tab_category where cid = ?";
        Category category = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Category>(Category.class), cid);
        return category;
    }

    public void updateCount(boolean add,int rid)
    {
        String sql1="UPDATE tab_route SET COUNT = COUNT + 1 WHERE rid = ?";
        String sql2="UPDATE tab_route SET COUNT = COUNT - 1 WHERE rid = ?";
        if(add)
        {
            jdbcTemplate.update(sql1,rid);
        }
        else
        {
            jdbcTemplate.update(sql2,rid);
        }
    }


    @Test
    public void test12()
    {
        this.updateCount(false,1);
    }




    @Test
    public void test()
    {
        List<RouteImg> routeImage = this.findRouteImage(1);
        Object[] objects = routeImage.toArray();
        //RouteImg[] routeImgs=(RouteImg)objects;
        System.out.println(objects[1]);
    }


    @Test
    public void test1()
    {
        /*Seller seller = this.findSeller(1);
        System.out.println(seller);*/
        Category category = this.findCategory(1);
        System.out.println(category);

    }
}
