package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.UserServiceimpl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/routeServlet/*")
public class RouteServlet extends BaseServlet {
    public void findRoute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String rid_str = req.getParameter("rid");
        int rid = Integer.parseInt(rid_str);
        RouteService routeService=new RouteServiceImpl();
        Route route = routeService.getRoute(rid);
        writeValue(resp,route);
    }
}
