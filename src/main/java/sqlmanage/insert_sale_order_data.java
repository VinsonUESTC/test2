package sqlmanage;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class insert_sale_order_data extends HttpServlet {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        // 设置响应内容类型
	        response.setContentType("text/html;charset=UTF-8");
			int i = 0;
			try {
				String sale_contract_number = request.getParameter("sale_contract_number");
				String sales_date = request.getParameter("sales_date");
				String receive_co = request.getParameter("receive_co");
				String product_name = request.getParameter("sale_product");
				String sale_amount = request.getParameter("sale_amount");
				String sale_singleprice = request.getParameter("sale_singleprice");
				String sale_totalprice = request.getParameter("sale_totalprice");
				String creater = request.getParameter("creater");
				String createtime = request.getParameter("createtime");
				i = initialization.insert_sale_order_data(sale_contract_number, sales_date, receive_co, product_name, sale_amount, sale_singleprice, sale_totalprice, creater, createtime);
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
