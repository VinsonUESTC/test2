package sqlmanage;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class insert_pay_data extends HttpServlet {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        // 设置响应内容类型
	        response.setContentType("text/html;charset=UTF-8");
			int i = 0;
			try {
				String supply_co = request.getParameter("supply_co");
				String payment_time = request.getParameter("payment_time");
				String payment_method = request.getParameter("payment_method");
				String payment_amount = request.getParameter("payment_amount");
				String creater = request.getParameter("creater");
				String createtime = request.getParameter("createtime");
				i = initialization.insert_pay_data(supply_co, payment_time, payment_method, payment_amount, creater, createtime);
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
