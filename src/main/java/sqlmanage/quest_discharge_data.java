package sqlmanage;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class quest_discharge_data extends HttpServlet {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        // 设置响应内容类型
	        response.setContentType("text/html;charset=UTF-8");
			String quest_discharge_data = null;
			try {
				String boat_number =request.getParameter("boat_number");
				quest_discharge_data = initialization.read_discharge_data(boat_number);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String resultset = "{\"quest_discharge_data\":"+quest_discharge_data+"}";
			response.getWriter().write(resultset);
	    }
	    
	    // 处理 POST 方法请求的方法
	    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        doGet(request, response);
	    }
}
