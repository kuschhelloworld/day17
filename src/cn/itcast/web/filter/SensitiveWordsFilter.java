package cn.itcast.web.filter;

import com.alibaba.druid.support.logging.Resources;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.xml.xpath.XPath;
import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

@WebFilter("/*")
public class SensitiveWordsFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //创建代理对象
        ServletRequest req_proxy = (ServletRequest) Proxy.newProxyInstance(req.getClass().getClassLoader(), req.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //增强getParameter对象
                if (method.getName().equals("getParameter")) {    //增强了这个也需要增强getparameterMap  和getParameterValue  以后再写！！！
                    //增强返回值
                    //获取输入的字符串返回值
                    String value = (String) method.invoke(req, args);
                    if (value!=null){
                        for (String str : list) {
                            if (value.contains(str)){
                                value=value.replaceAll(str,"***");
                            }
                        }
                    }
                    return value;
                }
                return method.invoke(req, args);//不是这个方法就传递真实对象和参数
            }
        });


        chain.doFilter(req_proxy, resp);

    }

    private List<String> list = new ArrayList<String>();//敏感词集合

    public void init(FilterConfig config) throws ServletException {
        try {
            //1.获取文件真实路径
            String realPath = config.getServletContext().getRealPath("/WEB-INF/classes/敏感词汇.txt");
            //2.读取文件
//*********************本 地 流 默 认 创 建 都 是 GBK 的 流！！ 注意文本的格式！！！！！！！*****************************************
            BufferedReader br = new BufferedReader(new FileReader(realPath));
            //3.将文件的每一行数据添加到list中
            String line = null;
            while((line=br.readLine())!=null){
                list.add(line);
            }
            br.close();
            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
    }

}
