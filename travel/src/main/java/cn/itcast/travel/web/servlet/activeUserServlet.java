package cn.itcast.travel.web.servlet;

import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.UserServiceimpl.UserServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@WebServlet("/activeUserServlet")
public class activeUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        resp.setContentType("text/html;charset=utf-8");
        if(code==null)
        {
            resp.getWriter().write("激活失败，请联系管理员");
        }
        else
        {
            UserService us=new UserServiceImpl();
            if(us.updateStatus(code))
            {
                resp.getWriter().write("激活成功，即将跳转至登录页面");
                resp.sendRedirect(req.getContextPath()+"/login.html");
            }
            else {
                resp.getWriter().write("激活失败，请联系管理员");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
