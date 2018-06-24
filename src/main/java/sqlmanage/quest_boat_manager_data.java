package sqlmanage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


public class quest_boat_manager_data extends HttpServlet {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        // 设置响应内容类型
	        response.setContentType("text/html;charset=UTF-8");
	        String boat_manager = request.getParameter("boat_manager");
			String boat_name_data = null;
			try {
				boat_name_data = initialization.read_boat_manager_data(boat_manager);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.getWriter().write(boat_name_data);
	    }
	    
	    // 处理 POST 方法请求的方法
	    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        doGet(request, response);
	    }
}
