package auth;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

public class Auth2 extends HttpServlet {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			//设置参数session
			HttpSession session = request.getSession();
			String code = request.getParameter("code");
			System.out.println(code);
	        // 设置响应内容类型
	        response.setContentType("text/html;charset=UTF-8");
			JSONObject user_detail = null;
			String power = null;
			String username = null;
			String userid = null;
			String site = null;
			//获取当前时间
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
			String dateString = dateFormat.format(new Date());
			try {
				String access_token = WeixinUtil.getAccessToken().getToken();
				JSONObject user_ticket = WeixinUtil.getUserTicket(access_token, code);
				user_detail = WeixinUtil.getUserDetail(access_token, user_ticket);
				username = user_detail.get("name").toString();
				userid = user_detail.get("userid").toString();
				power = WeixinUtil.getUserPosition(access_token, userid).getString("position");
            	sqlmanage.initialization.insert_login_data(username,power,dateString);
            	 } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (user_detail!=null) {
             	session.setAttribute("power", power);
            	session.setAttribute("username", username);
            	// 设置响应内容类型
            	response.setContentType("text/html;charset=UTF-8");
                // 要重定向的新位置
                switch (power){
                case "系统管理员":
             	   site = "../index.jsp";
             	   break;
                case "财务经理":
             	   site = "../finance_manager.jsp";
             	   break;
                case "出纳员":
              	   site = "../cashier.jsp";
              	   break;
                case "总经理":
              	   site = "../general_manager.jsp";
              	   break;
                case "董事长":
               	   site = "../chairman.jsp";
               	   break;
                case "船老大":
             	   site = "../boat_manager.jsp";
             	   break;
                case "运输经理":
             	   site = "../transport_manager.jsp";
             	   break;
                case "业务经理":
             	   site = "../sale_manager.jsp";
             	   break;
                }
                System.out.println(site);
                response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
                response.setHeader("Location", site);    
                PrintWriter out = response.getWriter();
                /*
                 * 在servlet中实现服务器端跳转，并向跳转页面传递参数
                 */
                //request.setAttribute("name", "haiyun"); // 为request对象添加参数
                
             }else{
				//session.setAttribute("msg", "授权出错！");
				 //设置响应内容类型
				response.setContentType("text/html;charset=UTF-8");
				// 要重定向的新位置
				site = "../login.jsp";
				response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
				response.setHeader("Location", site);    
             } 
			//RequestDispatcher dispatcher = request.getRequestDispatcher(site);    // 使用req对象获取RequestDispatcher对象
            //dispatcher.forward(request, response); 
			//response.getWriter().write(site);
	    }
	    
	    // 处理 POST 方法请求的方法
	    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        doGet(request, response);
	    }
}
