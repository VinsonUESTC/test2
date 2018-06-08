package sqlmanage;
import sqlmanage.initialization;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String validationCode = req.getParameter("validationCode");
        HttpSession session = req.getSession();
        String validation_code = (String)session.getAttribute("validation_code");
        String result = null;
		try {
			result = initialization.checkUser(username,password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
        if (result!=null) {
            if(validationCode.equalsIgnoreCase(validation_code)){
            	String site = null;
            	session.setAttribute("power", result);
            	session.setAttribute("username", username);
                // 设置响应内容类型
               resp.setContentType("text/html;charset=UTF-8");
               // 要重定向的新位置
               switch (result){
               case "super_manager":
            	   site = new String("../index.jsp");
            	   break;
               case "finance_manager":
            	   site = new String("../finance_manager.jsp");
            	   break;
               case "boat_manager":
            	   site = new String("../boat_manager.jsp");
            	   break;
               case "transport_manager":
            	   site = new String("../transport_manager.jsp");
            	   break;
               case "sale_manager":
            	   site = new String("../sale_manager.jsp");
            	   break;
               }
               System.out.println(site);
               resp.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
               resp.setHeader("Location", site);   
            }else{
            	session.setAttribute("msg", "验证码错误！");
                // 设置响应内容类型
               resp.setContentType("text/html;charset=UTF-8");
               // 要重定向的新位置
               String site = new String("../login.jsp");
               resp.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
               resp.setHeader("Location", site);    
            }         
        }else{
        	session.setAttribute("msg", "用户名或密码错误！");
            // 设置响应内容类型
           resp.setContentType("text/html;charset=UTF-8");
           // 要重定向的新位置
           String site = new String("../login.jsp");
           resp.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
           resp.setHeader("Location", site);    
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }   
}