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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;


@WebServlet("/user/*")
public class UserServlet extends BaseServlet{
    private UserService us=new UserServiceImpl();
    /**
     * 登录方法
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //UserService us=new UserServiceImpl();
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
    /**
     * 注册方法
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session=req.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
//获取服务器的验证码   删除已有的验证码
        Map<String, String[]> parameterMap = req.getParameterMap();
        //UserService us=new UserServiceImpl();
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
            //System.out.println(s);
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
            MailUtils.sendMail(user.getEmail(),"<a href=http://localhost:8080/travel/user/active?code="+code+">请激活账号</a>","激活账号");
            us.save(user);
        }
        else
        {
            Info.setFlag(false);
            Info.setErrorMsg("用户已存在");
        }
        String s=mapper.writeValueAsString(Info);
        resp.getWriter().write(s);
    }

    /**
     * 激活方法
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        resp.setContentType("text/html;charset=utf-8");
        if(code==null)
        {
            resp.getWriter().write("激活失败，请联系管理员");
        }
        else
        {
            //UserService us=new UserServiceImpl();
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


    /**
     * 退出方法
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath()+"/login.html");
    }


    public void findUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");

        resp.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper=new ObjectMapper();
        mapper.writeValue(resp.getOutputStream(),user);
    }


}
