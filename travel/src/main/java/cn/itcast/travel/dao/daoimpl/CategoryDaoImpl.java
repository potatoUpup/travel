package cn.itcast.travel.dao.daoimpl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    private JdbcTemplate jdbcTemplate=new JdbcTemplate(JDBCUtils.getDataSource());


    @Override
    public List<Category> findCategory() {
        try {
            String sql="select * from tab_category order by cid asc";
            List<Category> categories = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
            return categories;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public int findTotalCount(int cid, String rname) {
        String sql="select count(rid) from tab_route where 1 = 1 ";
        StringBuilder sb=new StringBuilder(sql);
        List params=new ArrayList<>();
        if(cid!=0)
        {
            sb.append(" and cid = ?");
            params.add(cid);
        }
        if(rname!=null && rname.length()!=0 && !"null".equals(rname))
        {
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }
        sql=sb.toString();

        int count = jdbcTemplate.queryForObject(sql, Integer.class, params.toArray());
        return count;
    }

    @Override
    public List<Route> findRouteList(int cid, int currentPage, int size, String rname) {
        //String sql="select * from tab_route where cid = ? limit ? , ?";
        String sql="select * from tab_route where 1 = 1 ";
        StringBuilder sb=new StringBuilder(sql);
        List params=new ArrayList<>();
        if(cid!=0)
        {
            sb.append(" and cid = ?");
            params.add(cid);
        }
        if(rname!=null && rname.length()!=0 && !"null".equals(rname))
        {
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }
        sb.append(" limit ? , ?");

        sql=sb.toString();

        int start=(currentPage-1)*size;
        params.add(start);
        params.add(size);
        List<Route> routeList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
        return routeList;
    }




    @Test
    public void test1()
    {
        String sql="select * from tab_route where rid =1" ;
        Route route = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class));
        System.out.println(route);
    }

    @Test
    public void test2()
    {
        /*int 西安 = this.findTotalCount(5, "西安");
        System.out.println(西安);*/

        List<Route> 西安 = this.findRouteList(0, 3, 8, "西安");
        for (Route route : 西安) {
            System.out.println(route);
        }
    }
}
