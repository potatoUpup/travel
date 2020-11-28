package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String methodName = uri.substring(uri.lastIndexOf('/')+1);


        try {
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, req, res);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void writeValue(HttpServletResponse response,Object object)
    {
        try
        {
            ObjectMapper objectMapper=new ObjectMapper();
            response.setContentType("application/json;charset=utf-8");
            objectMapper.writeValue(response.getOutputStream(),object);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public String writeValueAsString(Object object)
    {
        try {
            ObjectMapper objectMapper=new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }
}
