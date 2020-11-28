package cn.itcast.travel.service.UserServiceimpl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.daoimpl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    CategoryDao categoryDao=new CategoryDaoImpl();
    @Override
    public List<Category> findCategory() {
        Jedis jedis =null;
        Set<Tuple> Tuples=null;
        try {
            jedis = JedisUtil.getJedis();
            Tuples=jedis.zrangeWithScores("category",0,-1);
        }
        catch (Exception e)
        {
            jedis=null;
            Tuples=null;
        }
        List<Category> categories=null;
        if(Tuples==null || Tuples.size()==0)
        {
            //System.out.println(jedis);
            System.out.println("正从数据库中获取数据。。。。。。。");
            categories = categoryDao.findCategory();
            if(jedis!=null)
            {
                for (Category category:categories)
                {
                    jedis.zadd("category",category.getCid(),category.getCname());
                }
            }
        }
        else
        {
            System.out.println("正从redis中获取数据...............");
            categories=new ArrayList<Category>();
            for(Tuple tuple:Tuples)
            {
                int id = (int)tuple.getScore();
                String name=tuple.getElement();
                categories.add(new Category(id,name));
            }
        }
        return categories;
    }

    @Override
    public PageBean findRouteList(int currentPage, int size, int cid, String rname) {
        PageBean pb=new PageBean();
        pb.setCurrentPage(currentPage);
        pb.setSize(size);
        int totalCount=categoryDao.findTotalCount(cid,rname);
        pb.setTotalCount(totalCount);
        int totalPage= totalCount % size==0 ? totalCount/size : (totalCount/size)+1;
        pb.setTotalPage(totalPage);
        List<Route> route=categoryDao.findRouteList(cid,currentPage,size,rname);
        pb.setList(route);
        System.out.println(pb);
        return pb;

    }
    /*@Test
    public void test1()
    {
        PageBean routeList = this.findRouteList(1, 2, 5, rname);
        System.out.println(routeList);
    }*/
    @Test
    public void test()
    {
        Jedis jedis =null;
        Set<Tuple> Tuples=null;
        try {
            jedis = JedisUtil.getJedis();
            jedis.zrangeWithScores("category",0,-1);
        }
        catch (Exception e)
        {
            Tuples=null;
        }
        System.out.println(jedis);
    }

    @Test
    public void test03()
    {
        PageBean routeList = this.findRouteList(1, 8, 5, "西安");
        System.out.println(routeList);
    }
}
