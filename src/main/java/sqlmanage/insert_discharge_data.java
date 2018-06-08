package sqlmanage;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class insert_discharge_data extends HttpServlet {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        // 设置响应内容类型
	        response.setContentType("text/html;charset=UTF-8");
			int i = 0;
			try {
				String take_delivery_order_number = request.getParameter("take_delivery_order_number");
				String discharge_order_number = request.getParameter("discharge_order_number");
				String boat_number = request.getParameter("boat_number");
				String discharge_date = request.getParameter("discharge_date");
				String discharge_amount = request.getParameter("discharge_amount");
				String creater = request.getParameter("creater");
				String createtime = request.getParameter("createtime");
				i = initialization.insert_discharge_data(take_delivery_order_number, discharge_order_number, boat_number, discharge_date, discharge_amount, creater, createtime);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String resultset = "{\"i\":"+i+"}";
			response.getWriter().write(resultset);
	    }
	    
	    // 处理 POST 方法请求的方法
	    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        doGet(request, response);
	    }
}
