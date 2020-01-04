package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 用户
 */
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    /**
     * 定义成员变量
     */
    private UserService service = new UserServiceImpl();

    /**
     * 注册功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //验证码校验
        String check = request.getParameter("check");
        //获取session生成的验证码
        HttpSession session = request.getSession();
        String checkcode_session =(String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");    //保证每个验证码只用一次
        if (checkcode_session == null ||  !checkcode_session.equalsIgnoreCase(check)){
            //验证码错误
            ResultInfo info = new ResultInfo();     //信息实体类 ,提示信息
            info.setErrorMsg("验证码错误！！！");

            //将info对象序列化为json
            /*ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);*/
            String json = writeValueAsString((Object)  info);     //调用BaseServlet中自定义的方法

            //将json数据写回客户端
            //设置content-type
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;

        }else {//验证码正确

            //1.获取数据
            Map<String, String[]> map = request.getParameterMap();
            //2.封装数据
            User user = new User();
            try {
                BeanUtils.populate(user,map);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            //3.调用service 完成注册
            //UserService service = new UserServiceImpl();
            boolean flag = service.register(user);
            ResultInfo info = new ResultInfo();     //信息实体类
            //4.响应结果
            if (flag ){
                //注册成功
                info.setFlag(true);
            }else {
                //注册失败
                info.setFlag(false);
                info.setErrorMsg("注册失败！！！");
            }

            //将info对象序列化为json
            /*ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);*/
            String json = writeValueAsString( info);

            //将json数据写回客户端
            //设置content-type
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
        }
    }

    /**
     * 登陆功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //校验验证码
        String check = request.getParameter("check");    //输入的验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");   //服务器端生成的验证码
        session.removeAttribute("CHECKCODE_SERVER");               //保证验证码只能使用一次
        if (checkcode_server ==null || !checkcode_server.equalsIgnoreCase(check)){
            //验证码错误
            //调用消息类， 记录错误信息
            ResultInfo info = new ResultInfo();
            info.setErrorMsg("验证码错误！！！");
            info.setFlag(false);
            //将info序列化为json对象
            /*ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);*/
            String json = writeValueAsString( info);

            //将json数据写回客户端
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
        }else{
            //验证码正确
            //获取登陆信息
            Map<String, String[]> map = request.getParameterMap();
            //封装信息
            User user = new User();
            try {
                BeanUtils.populate(user,map);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            //调用service层完成登陆
         //   UserService service = new UserServiceImpl();
            User u = service.login(user);
            //创建消息类
            ResultInfo info = new ResultInfo();
            if (u == null){
                //用户名或密码错误
                info.setFlag(false);
                info.setErrorMsg("用户名或密码错误");
            }

            if ( u != null && "N".equals(u.getStatus())){
                //用户尚未激活
                info.setFlag(false);
                info.setErrorMsg("您尚未激活");
            }
            if (u != null && "Y".equals(u.getStatus())){
                //将数据库查询的用户信息，存入session中
                request.getSession().setAttribute("user",u);//登录成功标记
                //登陆成功
                info.setFlag(true);
            }
            //将info序列化为json数据
            /*ObjectMapper mapper =  new ObjectMapper();
            String json = mapper.writeValueAsString(info);*/
            String json = writeValueAsString(info);

            //将json数据写回客户端
            //设置编码格式
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
        }
    }

    /**
     * 查找单个用户
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从session中获取登陆用户
        Object user = (Object) request.getSession().getAttribute("user");

        //将user写回客户端
        /*ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),user);*/
        writeValue(user,response);
    }

    /**
     * 退出功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.销毁session
        request.getSession().invalidate();
        //2.跳转页面
        response.sendRedirect(request.getContextPath()+"/login.html");   //重定向到login页面 ， 所以需要获取虚拟路径
    }

    /**
     * 激活用户
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取激活码
        String code = request.getParameter("code");
        if (code != null){
            //2.调用service完成激活
         //   UserService service = new UserServiceImpl();
            boolean flag = service.active(code);
            //3.标记判断
            String msg = null;
            if (flag){
                //激活成功
                msg = "激活成功,请<a href ='login.html'>登陆</a>";
            }else {
                //激活失败
                msg= "激活失败，请联系管理员";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }
}
