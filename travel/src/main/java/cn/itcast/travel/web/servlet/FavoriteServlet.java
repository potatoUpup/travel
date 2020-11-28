package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.UserServiceimpl.FavoriteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/favoriteServlet/*")
public class FavoriteServlet extends BaseServlet{
    FavoriteService favoriteService=new FavoriteServiceImpl();
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        User user = (User) request.getSession().getAttribute("user");
        int uid=0;
        String rid_str = request.getParameter("rid");
        int rid = Integer.parseInt(rid_str);
        if(user==null)
        {
            uid=0;
        }
        else if(user!=null)
        {
             uid = user.getUid();
        }
        boolean flag = favoriteService.isFavorite(uid, rid);

        writeValue(response,flag);
    }


    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid_str = request.getParameter("rid");
        int rid = Integer.parseInt(rid_str);
        User user=(User)request.getSession().getAttribute("user");
        int uid=0;
        if(user==null)
        {
            return;
        }
        else
        {
            uid=user.getUid();
            favoriteService.addFavorite(uid,rid);
        }
    }
}
