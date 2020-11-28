package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.UserServiceimpl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;


@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet{
    CategoryService categoryService=new CategoryServiceImpl();

    public void findCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        List<Category> category = categoryService.findCategory();
        writeValue(resp,category);
    }

    public void findRouteList(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException
    {
        String cid_str = req.getParameter("cid");

        int cid=0;
        if(cid_str!=null && cid_str.length()!=0 && !"null".equals(cid_str))
        {
            cid = Integer.parseInt(cid_str);
        }//获取cid值
        System.out.println("当前cid值："+cid);


        String currentpage_str = req.getParameter("currentpage");
        System.out.println("当前页面值："+currentpage_str);


        int currentpage =1;
        if(currentpage_str!=null && currentpage_str.length()!=0 && currentpage_str!="")
        {
            currentpage=Integer.parseInt(currentpage_str);
        }//获取当前页面值


        int size =8;
        String size_str=req.getParameter("size");
        System.out.println("当前页面大小："+size_str);
        if(size_str!=null&&size_str.length()!=0)
        {
            size=Integer.parseInt(size_str);
        }

        String rname = req.getParameter("rname");
        rname= URLDecoder.decode(rname,"utf-8");
        if("".equals(rname))
        {
            rname=null;
        }
        //rname = new String(rname.getBytes("ISO-8859-1"),"utf-8");
        System.out.println("当前查询线路："+rname);

        //Integer.parseInt(size_str);
        PageBean pb=categoryService.findRouteList(currentpage,size,cid,rname);
        writeValue(resp,pb);
    }
}
