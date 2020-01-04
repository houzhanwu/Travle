package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 方法分发
 */
@WebServlet("/baseServlet")
public class BaseServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //System.out.println("baseServlet 中的service被执行了。。。");

        //完成方法分法
        //1.获取请求路径
        String uri = req.getRequestURI();      // /user/add
       // System.out.println("请求uir：" +uri);   // /user/add
        //2.获取方法名称
        String methodName = uri.substring(uri.lastIndexOf("/") + 1);   //  获取‘/’ 后面加一则为方法名称
       // System.out.println("方法名称： " +methodName);  //
        //3.执行方法对象method
        // this 谁调用我 ，我代表谁
        //  System.out.println(this);  //Userservlet的对象
        //使用反射执行方法
        try {
            //忽略访问权限修饰符， 获取方法  getDeclaredMethod()
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);  // this 谁调用我 ，我代表谁
            //4.执行方法
            //暴力反射
           // method.setAccessible(true);
            method.invoke(this,req,resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 直接将传入的对象序列化为json， 并且写回客户端
     */
    public void writeValue(Object obj , HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),obj);
    }

    /**
     * 将传入的对象序列化为json， 返回
     * @return
     */
    public String writeValueAsString(Object obj ) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

}
