package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.PageBean;

import java.util.List;

public interface CategoryService {
    public List<Category> findCategory();
    public PageBean findRouteList(int currentPage, int size, int cid, String rname);
}
