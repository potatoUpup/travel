package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface CategoryDao {
    public List<Category> findCategory();

    public int findTotalCount(int cid, String rname);

    public List<Route> findRouteList(int cid, int currentPage, int size, String rname);
}
