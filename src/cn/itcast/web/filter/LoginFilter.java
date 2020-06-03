package cn.itcast.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 判断登陆状态的过滤器
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        /**
         *判断是否是登录相关资源
         */
        //1.强制转换成HttServletRequest和HttServletReponse对象，因为只有这俩对象里面才有对应的方法
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        //2.获取资源请求路径
        String uri = request.getRequestURI();
        //3.判断是否有登录相关资源  注意排除登陆页面用到的CSS，js等文件
        if (uri.contains("/login.jsp") || uri.contains("/loginServlet")
                || uri.contains("/css/") || uri.contains("/js/")
                || uri.contains("/fonts/") || uri.contains("/checkCodeServlet")) {
            //包含   证明用户就是想登陆       放行
            chain.doFilter(req, resp);
        } else {
            //不包含   需要去验证用户是否登录  从Session中获取User对象
            Object user = request.getSession().getAttribute("user");
            if (user != null) {
                //登陆了   放行
                chain.doFilter(req, resp);
            } else {
                //没登陆，去登陆页面吧
                request.setAttribute("login_msg", "没有登陆，来登陆吧！");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

    public void destroy() {
    }

}
