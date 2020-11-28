package cn.itcast.travel.service.UserServiceimpl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.daoimpl.FavoriteDaoImpl;
import cn.itcast.travel.dao.daoimpl.RouteDaoImpl;
import cn.itcast.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {
    FavoriteDao favoriteDao=new FavoriteDaoImpl();
    @Override
    public boolean isFavorite(int uid, int rid) {
        return favoriteDao.isFavorite(uid,rid);
    }

    @Override
    public void addFavorite(int uid, int rid) {
        RouteDao routeDao=new RouteDaoImpl();
        favoriteDao.addFavorite(uid,rid);
        routeDao.updateCount(true,rid);
    }
}
