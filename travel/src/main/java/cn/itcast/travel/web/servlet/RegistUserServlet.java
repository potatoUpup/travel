package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.UserServiceimpl.UserServiceImpl;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;


@WebServlet("/registUserServlet")
public class RegistUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String checkcode_server = (String) req.getSession().getAttribute("checkcode_server");
        HttpSession session=req.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
//获取服务器的验证码   删除已有的验证码
        Map<String, String[]> parameterMap = req.getParameterMap();
        UserService us=new UserServiceImpl();
        User user=new User();
        ResultInfo Info = new ResultInfo();

        ObjectMapper mapper=new ObjectMapper();
        String checkcode=parameterMap.get("check")[0];

        resp.setContentType("application/json;charset=utf-8");
        try {
            BeanUtils.populate(user,parameterMap);
        }
        catch (Exception e)
        {
        }
        if(!checkcode.equalsIgnoreCase(checkcode_server))
        {
            Info.setFlag(false);
            Info.setErrorMsg("验证码错误");
            String s=mapper.writeValueAsString(Info);
            System.out.println(s);
            resp.getWriter().write(s);
            return;
        }
        System.out.println(parameterMap.get("username")[0]);
        if(!us.findUserByUsername(parameterMap.get("username")[0]))
        {
            Info.setFlag(true);
            String code= UuidUtil.getUuid();
            user.setCode(code);
            user.setStatus("N");
            MailUtils.sendMail(user.getEmail(),"<a href=http://localhost:8080/travel/activeUserServlet?code="+code+">请激活账号</a>","激活账号");
            us.save(user);
        }
        else
        {
            Info.setFlag(false);
            Info.setErrorMsg("用户已存在");
        }
        String s=mapper.writeValueAsString(Info);
        System.out.println(s);
        resp.getWriter().write(s);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
