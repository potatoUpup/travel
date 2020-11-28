package cn.itcast.travel.service.UserServiceimpl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.daoimpl.RouteDaoImpl;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;
import org.junit.Test;

public class RouteServiceImpl implements RouteService {
    RouteDao routeDao=new RouteDaoImpl();
    @Override
    public Route getRoute(int rid) {
        Route route=new Route();
        Route get_route=routeDao.findOne(rid);

        //将route表查询的数据整合到当前route对象
        route.setRid(get_route.getRid());
        route.setRname(get_route.getRname());
        route.setPrice(get_route.getPrice());
        route.setRouteIntroduce(get_route.getRouteIntroduce());
        route.setRflag(get_route.getRflag());
        route.setRdate(get_route.getRdate());
        route.setIsThemeTour(get_route.getIsThemeTour());
        route.setCount(get_route.getCount());
        route.setCid(get_route.getCid());
        route.setRimage(get_route.getRimage());
        route.setSid(get_route.getSid());
        route.setSourceId(get_route.getSourceId());

        route.setRouteImgList(routeDao.findRouteImage(get_route.getRid()));
        route.setSeller(routeDao.findSeller(get_route.getSid()));
        route.setCategory(routeDao.findCategory(get_route.getCid()));


        return route;
    }

   @Test
    public void test()
    {
        Route route = this.getRoute(1);
        System.out.println(route);
    }
}
