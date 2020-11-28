package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.UserServiceimpl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;


@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService us=new UserServiceImpl();
        Map<String, String[]> parameterMap = req.getParameterMap();
        User user =new User();
        HttpSession session = req.getSession();
        resp.setContentType("application/json;charset=utf-8");
        try {
            BeanUtils.populate(user,parameterMap);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        User login_user = us.login(user);
        session.setAttribute("user",login_user);
        ResultInfo info=new ResultInfo();
        if(login_user==null)
        {
            info.setFlag(false);
            info.setErrorMsg("用户名或密码有误");
        }
        if(login_user!=null && !"Y".equalsIgnoreCase(login_user.getStatus()))
        {
            info.setFlag(false);
            info.setErrorMsg("您尚未激活");
        }
        if (login_user!=null && "Y".equalsIgnoreCase(login_user.getStatus()))
        {
            info.setFlag(true);
        }
        ObjectMapper mapper=new ObjectMapper();
        String s = mapper.writeValueAsString(info);

        resp.getWriter().write(s);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
