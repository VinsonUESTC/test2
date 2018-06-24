package sqlmanage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class delete_boat_name_data extends HttpServlet {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 设置响应内容类型
		response.setContentType("text/html;charset=UTF-8");
		int i = 0;
		String boat_name = request.getParameter("boat_name");
		String boat_manager = request.getParameter("boat_manager");
		i = initialization.delete_boat_name_data(boat_name,boat_manager);
		String resultset = "{\"i\":"+i+"}";
			response.getWriter().write(resultset);
	}
	    
	// 处理 POST 方法请求的方法
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
