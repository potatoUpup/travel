package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;

import java.util.List;

public interface RouteDao {
    public Route findOne(int rid);

    public List<RouteImg> findRouteImage(int rid);

    public Seller findSeller(int sid);

    public Category findCategory(int cid);

    public void updateCount(boolean add,int rid);
}
