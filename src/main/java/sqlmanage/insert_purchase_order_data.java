package sqlmanage;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class insert_purchase_order_data extends HttpServlet {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        // 设置响应内容类型
	        response.setContentType("text/html;charset=UTF-8");
			int i = 0;
			String purchase_contract_number = request.getParameter("purchase_contract_number");
			String orders_date = request.getParameter("orders_date");
			String supply_co = request.getParameter("supply_co");
			String product_name = request.getParameter("purchase_product");
			String purchase_amount = request.getParameter("purchase_amount");
			String purchase_singleprice = request.getParameter("purchase_singleprice");
			String purchase_totalprice = request.getParameter("purchase_totalprice");
			String payment_method = request.getParameter("payment_method");
			String creater = request.getParameter("creater");
			String createtime = request.getParameter("createtime");
			i = initialization.insert_purchase_order_data(purchase_contract_number, orders_date, supply_co, product_name, purchase_amount, purchase_singleprice, purchase_totalprice, payment_method, creater, createtime);
			String resultset = "{\"i\":"+i+"}";
			response.getWriter().write(resultset);
	    }
	    
	    // 处理 POST 方法请求的方法
	    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        doGet(request, response);
	    }
}
