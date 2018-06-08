package sqlmanage;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class quest_delivery_amount_data extends HttpServlet {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        // 设置响应内容类型
	        response.setContentType("text/html;charset=UTF-8");
			String quest_delivery_amount_data = null;
			try {
				String supply_co =request.getParameter("supply_co");
				String start_delivery_date =request.getParameter("delivery_start_date");
				String end_delivery_date =request.getParameter("delivery_end_date");
				quest_delivery_amount_data = initialization.read_delivery_data(start_delivery_date, end_delivery_date, supply_co);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String resultset = "{\"quest_delivery_amount_data\":"+quest_delivery_amount_data+"}";
			response.getWriter().write(resultset);
	    }
	    
	    // 处理 POST 方法请求的方法
	    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        doGet(request, response);
	    }
}
