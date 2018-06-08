package sqlmanage;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class insert_allocate_data extends HttpServlet {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        // 设置响应内容类型
	        response.setContentType("text/html;charset=UTF-8");
			int i = 0;
			try {
				String allocate_order_number = request.getParameter("allocate_order_number");
				String purchase_contract_number = request.getParameter("purchase_contract_number");
				String boat_number = request.getParameter("boat_number");
				String allocate_amount = request.getParameter("allocate_amount");
				String creater = request.getParameter("creater");
				String createtime = request.getParameter("createtime");
				i = initialization.insert_allocate_data(allocate_order_number,purchase_contract_number, boat_number, allocate_amount, creater, createtime);
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
