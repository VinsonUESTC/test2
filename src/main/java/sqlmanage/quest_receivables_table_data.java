package sqlmanage;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class quest_receivables_table_data extends HttpServlet {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        // 设置响应内容类型
	        response.setContentType("text/html;charset=UTF-8");
			String quest_receivalbes_table_data = null;
			try {
				String receive_co =request.getParameter("receive_co_receive");
				String start_receivables_date =request.getParameter("receive_start_date");
				String end_receivables_date =request.getParameter("receive_end_date");
				quest_receivalbes_table_data = initialization.read_receivables_table_data(start_receivables_date, end_receivables_date, receive_co);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String resultset = "{\"quest_receivalbes_table_data\":"+quest_receivalbes_table_data+"}";
			response.getWriter().write(resultset);
	    }
	    
	    // 处理 POST 方法请求的方法
	    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        doGet(request, response);
	    }
}
