package sqlmanage;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import org.codehaus.jackson.map.ObjectMapper;


public class initialization {

	static String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	static String dbURL = "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=test;useunicode=true;characterEncoding=UTF-8";
	static String userName = "test";
	static String userPwd = "jyf123456";
	public static void main(String[] args) throws SQLException {
		ClearDataBase();
		InitDataBase();
		//read_sale_order_data();
		//read_purchase_order_data("22","text4");
		//read_purchase_order_data("2017-12-1");
		//read_payment_data("现金");
		//read_payment_data("承兑");                                                                                                                                                                                                                                 
		//read_allocate_data();
		//read_take_delivery_data("231");
		//read_discharge_data("231");
		//read_loss_data("2018-1-1");
		//read_receivables_data();
		//read_discharge_data("4412");
	}
	
	//清空数据库
	public static void ClearDataBase() {
		Connection conn = null;
		Statement stmt = null;
        try {
        	Class.forName(driverName);  
        	conn = DriverManager.getConnection(dbURL, userName, userPwd );  
    		stmt = conn.createStatement();
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rsTables = meta.getTables("test2", "dbo", "%", new String[] { "TABLE" });
            while (rsTables.next()) {
            	stmt.executeUpdate("DROP TABLE "+rsTables.getString("TABLE_NAME"));
            }
            rsTables.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            //finally block used to close resources
            try{
               if(stmt!=null)
                  conn.close();
            }catch(SQLException se){
            }// do nothing
            try{
               if(conn!=null)
                  conn.close();
            }catch(SQLException se){
               se.printStackTrace();
            }
        }//end finally try
    }
		

	
	//初始化创建数据库
	public static void InitDataBase(){
		//创建登陆表格
		CreateTables("test2","Login_Record","CREATE TABLE Login_Record"
				+ "("
				+ "user_name nvarchar(50) NOT NULL,"
				+ "position nvarchar(50) NOT NULL,"
				+ "login_time datetime NOT NULL"
				+ ")");
		//创建采购订单表格
		CreateTables("test2","Purchase_Order","CREATE TABLE Purchase_Order"
				+ "("
				+ "purchase_contract_number nvarchar(50) NOT NULL UNIQUE,"
				+ "orders_date date  NOT NULL,"
				+ "supply_co nvarchar(50) NOT NULL,"
				+ "product_name nvarchar(50) NOT NULL,"
				+ "purchase_amount int NOT NULL,"
				+ "purchase_singleprice money NOT NULL,"
				+ "purchase_totalprice money NOT NULL,"
				+ "payment_method nvarchar(50) NOT NULL,"
				+ "creater nvarchar(50) NOT NULL,"
				+ "create_time datetime NOT NULL"
				+ ")");
		//创建销售订单表格
		CreateTables("test2","Sale_Order","CREATE TABLE Sale_Order"
				+ "("
				+ "sale_contract_number nvarchar(50) NOT NULL UNIQUE,"
				+ "sales_date date  NOT NULL,"
				+ "receive_co nvarchar(50) NOT NULL,"
				+ "product_name nvarchar(50) NOT NULL,"
				+ "sale_amount int NOT NULL,"
				+ "sale_singleprice money NOT NULL,"
				+ "sale_totalprice money NOT NULL,"
				+ "creater nvarchar(50) NOT NULL,"
				+ "create_time datetime NOT NULL"
				+ ")");
		//创建供销关联表格
		CreateTables("test2","Sale_Associate_Purchase","CREATE TABLE Sale_Associate_Purchase"
				+ "("
				+ "purchase_contract_number nvarchar(50)  NOT NULL UNIQUE,"
				+ "sale_contract_number nvarchar(50) NOT NULL,"
				+ "creater nvarchar(50) NOT NULL,"
				+ "create_time datetime NOT NULL"
				+ ")");
		//创建付款表格
		CreateTables("test2","Payment","CREATE TABLE Payment"
				+ "("
				+ "supply_co nvarchar(50) NOT NULL,"
				+ "payment_time date  NOT NULL,"
				+ "payment_method nvarchar(50) NOT NULL,"
				+ "payment_amount money NOT NULL,"
				+ "creater nvarchar(50) NOT NULL,"
				+ "create_time datetime NOT NULL"
				+ ")");
		//创建收款表格
		CreateTables("test2","Receivables","CREATE TABLE Receivables"
				+ "("
				+ "receive_co nvarchar(50) NOT NULL,"
				+ "receivables_time date  NOT NULL,"
				+ "receivables_method nvarchar(50) NOT NULL,"
				+ "receivables_amount money NOT NULL,"
				+ "creater nvarchar(50) NOT NULL,"
				+ "create_time datetime NOT NULL"
				+ ")");
		//创建分配表格
		CreateTables("test2","Allocate_Boat","CREATE TABLE Allocate_Boat"
				+ "("
				+ "allocate_order_number nvarchar(50) NOT NULL UNIQUE,"
				+ "purchase_contract_number nvarchar(50) NOT NULL ,"
				+ "boat_number nvarchar(50) NOT NULL,"
				+ "allocate_amount int NOT NULL,"
				+ "creater nvarchar(50) NOT NULL,"
				+ "create_time datetime NOT NULL"
				+ ")");
		//创建提货表格
		CreateTables("test2","Take_Delivery","CREATE TABLE Take_Delivery"
				+ "("
				+ "take_delivery_order_number nvarchar(50) NOT NULL UNIQUE,"
				+ "allocate_order_number nvarchar(50) NOT NULL ,"
				+ "boat_number nvarchar(50) NOT NULL ,"
				+ "take_delivery_date date NOT NULL,"
				+ "delivery_amount int NOT NULL,"
				+ "creater nvarchar(50) NOT NULL,"
				+ "create_time datetime NOT NULL"
				+ ")");
		//创建卸货表格
		CreateTables("test2","Discharge","CREATE TABLE Discharge"
				+ "("
				+ "discharge_order_number nvarchar(50) NOT NULL UNIQUE,"
				+ "take_delivery_order_number nvarchar(50) NOT NULL UNIQUE,"
				+ "boat_number nvarchar(50) NOT NULL ,"
				+ "discharge_date date NOT NULL,"
				+ "discharge_amount int NOT NULL,"
				+ "creater nvarchar(50) NOT NULL,"
				+ "create_time datetime NOT NULL"
				+ ")");
	}
	
	public static void CreateTables(String BaseName ,String TableName ,String sql){
		Connection conn = null;
		Statement stmt = null;
		//先判断表是否存在
		if(JudgeTableExist(BaseName,TableName)==false){
			try {  
	        	Class.forName(driverName);  
	        	conn = DriverManager.getConnection(dbURL, userName, userPwd );  
				stmt = conn.createStatement();
				stmt.executeUpdate(sql);
				stmt.close();
			} catch (Exception e) {  
				e.printStackTrace();  
			}finally{
	            //finally block used to close resources
	            try{
	               if(stmt!=null)
	                  conn.close();
	            }catch(SQLException se){
	            }// do nothing
	            try{
	               if(conn!=null)
	                  conn.close();
	            }catch(SQLException se){
	               se.printStackTrace();
	            }
	        }//end finally try
		}
		else{
			System.out.println("already exist!");
		}
	}
	
	
	//判断表是否存在
	public static boolean JudgeTableExist(String BaseName ,String TableName) {
		Connection conn = null;
		Statement stmt = null;
		boolean flag = false;
		try {  
			Class.forName(driverName);  
			conn = DriverManager.getConnection(dbURL, userName, userPwd );  
			stmt = conn.createStatement();
			DatabaseMetaData meta = conn.getMetaData();  
			ResultSet rsTables = meta.getTables(BaseName, "dbo", TableName,  new String[] { "TABLE" });  
			flag = rsTables.next();
			rsTables.close();  
		} catch (Exception e) {  
			e.printStackTrace();  
		} finally{
            //finally block used to close resources
            try{
               if(stmt!=null)
                  conn.close();
            }catch(SQLException se){
            }// do nothing
            try{
               if(conn!=null)
                  conn.close();
            }catch(SQLException se){
               se.printStackTrace();
            }
        }//end finally try
		return flag;
	}
	
	//录入登陆数据
	public static int insert_login_data(String user_name,String position,String login_time)
	{
		Connection conn = null;
		Statement stmt = null;
		int lines = 0;
		try {  
			Class.forName(driverName);  
			conn = DriverManager.getConnection(dbURL, userName, userPwd );  
			stmt = conn.createStatement();
			lines =stmt.executeUpdate("INSERT INTO [dbo].[Login_Record] VALUES ('"+user_name+"', '"+position+"', '"+login_time+"')");
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null)
				conn.close();
			}catch(SQLException se){
			}// do nothing
			try{
				if(conn!=null)
				conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}//end finally try
		return lines;
	}
	
	//录入采购订单数据
	public static int insert_purchase_order_data(String purchase_contract_number,String orders_date,String supply_co,String product_name,String purchase_amount,String purchase_singleprice,String purchase_totalprice,String payment_method,String creater,String createtime)
	{
		Connection conn = null;
		Statement stmt = null;
		int lines = 0;
		try {  
			Class.forName(driverName);  
			conn = DriverManager.getConnection(dbURL, userName, userPwd );  
			stmt = conn.createStatement();
			lines =stmt.executeUpdate("INSERT INTO [dbo].[Purchase_Order] VALUES ('"+purchase_contract_number+"', '"+orders_date+"', '"+supply_co+"', '"+product_name+"','"+purchase_amount+"','"+purchase_singleprice+"','"+purchase_totalprice+"','"+payment_method+"','"+creater+"','"+createtime+"')");
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null)
				conn.close();
			}catch(SQLException se){
			}// do nothing
			try{
				if(conn!=null)
				conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}//end finally try
		return lines;
	}
	//录入销售订单数据
	public static int insert_sale_order_data(String sale_contract_number,String sales_date,String receive_co,String product_name,String sale_amount,String sale_singleprice,String sale_totalprice,String creater,String createtime) throws SQLException
	{
		Connection conn = null;
		Statement stmt = null;
		int lines = 0;
		try {  
			Class.forName(driverName);  
			conn = DriverManager.getConnection(dbURL, userName, userPwd );  
			stmt = conn.createStatement();
			lines =stmt.executeUpdate("INSERT INTO [dbo].[Sale_Order] "
					+ "VALUES ('"+sale_contract_number+"', '"+sales_date+"', '"+receive_co+"', '"+product_name+"','"+sale_amount+"','"+sale_singleprice+"','"+sale_totalprice+"','"+creater+"','"+createtime+"')");
		}catch (Exception e) {
		e.printStackTrace();
		}finally{
			conn.close();
		}//end finally try
		return lines;
	}
	
	//录入关联数据
	public static int insert_associate_data(String sale_contract_number,String purchase_contract_number,String creater,String createtime) throws SQLException
	{
		Connection conn = null;
		Statement stmt = null;
		int lines = 0;
		try {  
			Class.forName(driverName);  
			conn = DriverManager.getConnection(dbURL, userName, userPwd );  
			stmt = conn.createStatement();
			lines =stmt.executeUpdate("INSERT INTO [dbo].[Sale_Associate_Purchase] "
					+ "VALUES ('"+purchase_contract_number+"', '"+sale_contract_number+"', '"+creater+"','"+createtime+"')");
		}catch (Exception e) {
		e.printStackTrace();
		}finally{
			conn.close();
		}//end finally try
		return lines;
	}

	//录入分配数据
	public static int insert_allocate_data(String allocate_order_number, String purchase_contract_number,String boat_number,String allocate_amount,String creater,String createtime) throws SQLException
	{
		Connection conn = null;
		Statement stmt = null;
		int lines = 0;
		try {  
			Class.forName(driverName);  
			conn = DriverManager.getConnection(dbURL, userName, userPwd );  
			stmt = conn.createStatement();
			lines =stmt.executeUpdate("INSERT INTO [dbo].[Allocate_Boat] "
					+ "VALUES ('"+allocate_order_number+"', '"+purchase_contract_number+"', '"+boat_number+"', '"+allocate_amount+"','"+creater+"','"+createtime+"')");
		}catch (Exception e) {
		e.printStackTrace();
		}finally{
			conn.close();
		}//end finally try
		return lines;
	}	
	
	//录入提货数据
	public static int insert_take_delivery_data(String take_delivery_order_number, String allocate_order_number,String boat_number,String take_delivery_date,String delivery_amount,String creater,String createtime) throws SQLException
	{
		Connection conn = null;
		Statement stmt = null;
		int lines = 0;
		try {  
			Class.forName(driverName);  
			conn = DriverManager.getConnection(dbURL, userName, userPwd );  
			stmt = conn.createStatement();
			lines =stmt.executeUpdate("INSERT INTO [dbo].[Take_Delivery] "
					+ "VALUES ('"+take_delivery_order_number+"', '"+allocate_order_number+"', '"+boat_number+"', '"+take_delivery_date+"', '"+delivery_amount+"','"+creater+"','"+createtime+"')");
		}catch (Exception e) {
		e.printStackTrace();
		}finally{
			conn.close();
		}//end finally try
		return lines;
	}	
	
	//录入卸货数据
	public static int insert_discharge_data(String take_delivery_order_number, String discharge_order_number,String boat_number,String discharge_date,String discharge_amount,String creater,String createtime) throws SQLException
	{
		Connection conn = null;
		Statement stmt = null;
		int lines = 0;
		try {  
			Class.forName(driverName);  
			conn = DriverManager.getConnection(dbURL, userName, userPwd );  
			stmt = conn.createStatement();
			lines =stmt.executeUpdate("INSERT INTO [dbo].[Discharge] "
					+ "VALUES ('"+discharge_order_number+"', '"+take_delivery_order_number+"', '"+boat_number+"', '"+discharge_date+"', '"+discharge_amount+"','"+creater+"','"+createtime+"')");
		}catch (Exception e) {
		e.printStackTrace();
		}finally{
			conn.close();
		}//end finally try
		return lines;
	}	
	
	//录入付款数据
	public static int insert_pay_data(String supply_co,String payment_time,String payment_method,String payment_amount,String creater,String createtime) throws SQLException
	{
		Connection conn = null;
		Statement stmt = null;
		int lines = 0;
		try {  
			Class.forName(driverName);  
			conn = DriverManager.getConnection(dbURL, userName, userPwd );  
			stmt = conn.createStatement();
			lines =stmt.executeUpdate("INSERT INTO [dbo].[Payment] "
					+ "VALUES ('"+supply_co+"', '"+payment_time+"', '"+payment_method+"','"+payment_amount+"','"+creater+"','"+createtime+"')");
		}catch (Exception e) {
		e.printStackTrace();
		}finally{
			conn.close();
		}//end finally try
		return lines;
	}	
	
	//录入付款数据
	public static int insert_receive_data(String receive_co,String receivables_time,String receivables_method,String receivables_amount,String creater,String createtime) throws SQLException
	{
		Connection conn = null;
		Statement stmt = null;
		int lines = 0;
		try {  
			Class.forName(driverName);  
			conn = DriverManager.getConnection(dbURL, userName, userPwd );  
			stmt = conn.createStatement();
			lines =stmt.executeUpdate("INSERT INTO [dbo].[Receivables] "
					+ "VALUES ('"+receive_co+"', '"+receivables_time+"', '"+receivables_method+"','"+receivables_amount+"','"+creater+"','"+createtime+"')");
		}catch (Exception e) {
		e.printStackTrace();
		}finally{
			conn.close();
		}//end finally try
		return lines;
	}	
	
	
	//读取关联采购订单数据
	public static String read_associate_purchase_order_data(String sale_amount,String product_name) throws SQLException
	{
		Connection conn = null;
		@SuppressWarnings("rawtypes")
		ArrayList<HashMap> list = new ArrayList<HashMap>();
		Statement stmt = null;
		try {  
			Class.forName(driverName);  
			conn = DriverManager.getConnection(dbURL, userName, userPwd );  
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT purchase_contract_number, supply_co, purchase_amount "
					+ "FROM  [dbo].[Purchase_Order] WHERE product_name= '"+product_name+"' AND purchase_amount <= "+sale_amount
					+" AND purchase_contract_number not in (select purchase_contract_number from [dbo].[Sale_Associate_Purchase])");
			while(rs.next()){
				HashMap<String, String> temp = new HashMap<String,String>();
				temp.put("purchase_contract_number", rs.getString(1));
				temp.put("supply_co", rs.getString(2));
				temp.put("purchase_amount", rs.getString(3));
				list.add(temp);
			}
		}catch (Exception e) {
		e.printStackTrace();
		}finally{
			conn.close();
		}//end finally try
		System.out.println(Object2JasonStr(list));
		return Object2JasonStr(list);
	}
	
	//读取关联销售订单数据
	public static String read_associate_sale_order_data() throws SQLException
	{
		Connection conn = null;
		@SuppressWarnings("rawtypes")
		ArrayList<HashMap> list = new ArrayList<HashMap>();
		Statement stmt = null;
		Statement stmt2 = null;
		try {  
			Class.forName(driverName);  
			conn = DriverManager.getConnection(dbURL, userName, userPwd );  
			stmt = conn.createStatement();
			stmt2 = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT sale_contract_number, receive_co, product_name, sale_amount FROM  [dbo].[Sale_Order]");
			while(rs.next()){
				int i = 0;
				HashMap<String, String> temp = new HashMap<String,String>();
				temp.put("sale_contract_number", rs.getString(1));
				temp.put("receive_co", rs.getString(2));
				temp.put("product_name", rs.getString(3));
				temp.put("sale_amount", rs.getString(4));
				ResultSet rs2 = stmt2.executeQuery("SELECT [dbo].[Sale_Order].sale_contract_number, "
					+ "Sum([dbo].[Purchase_Order].purchase_amount) AS purchase_amount_sum "+
					"FROM [dbo].[Purchase_Order] INNER JOIN ([dbo].[Sale_Associate_Purchase] INNER JOIN [dbo].[Sale_Order] ON "
					+ "[dbo].[Sale_Associate_Purchase].sale_contract_number = [dbo].[Sale_Order].sale_contract_number) ON "
					+ "[dbo].[Purchase_Order].purchase_contract_number = [dbo].[Sale_Associate_Purchase].purchase_contract_number "
					+"WHERE [dbo].[Sale_Order].sale_contract_number = '"+rs.getString(1)+"' AND [dbo].[Sale_Order].product_name = [dbo].[Purchase_Order].product_name"
					+ " GROUP BY [dbo].[Sale_Order].sale_contract_number ;"
				);
				while(rs2.next()){
					i += rs2.getInt(2);
				}
				temp.put("sale_amount_used", String.valueOf(i));
				temp.put("sale_amount_left", String.valueOf(rs.getInt(4)-i));
				if(rs.getInt(4)-i>0){
					list.add(temp);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			conn.close();
		}//end finally try
		System.out.println(Object2JasonStr(list));
		return Object2JasonStr(list);
	}

	//读取销售订单数据
	public static String read_sale_order_data(String month) throws SQLException
	{
		Connection conn = null;
		@SuppressWarnings("rawtypes")
		ArrayList<HashMap> list = new ArrayList<HashMap>();
		Statement stmt = null;
		try {  
			Class.forName(driverName);  
			conn = DriverManager.getConnection(dbURL, userName, userPwd );  
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT [sale_contract_number] ,[sales_date] ,[receive_co] ,[product_name] ,[sale_amount] ,[sale_singleprice] ,[sale_totalprice] ,[creater] ,[create_time]"
					+ " FROM [test2].[dbo].[Sale_Order]"
					+ "WHERE datediff(month,[dbo].[Sale_Order].sales_date,'"+month+"')=0");
			while(rs.next()){
				HashMap<String, String> temp = new HashMap<String,String>();
				temp.put("sale_contract_number", rs.getString(1));
				temp.put("sales_date", rs.getString(2));
				temp.put("receive_co", rs.getString(3));
				temp.put("product_name", rs.getString(4));
				temp.put("sale_amount", rs.getString(5));
				temp.put("sale_singleprice", rs.getString(6));
				temp.put("sale_totalprice", rs.getString(7));
				temp.put("creater", rs.getString(8));
				temp.put("create_time", rs.getString(9));
				list.add(temp);
			}
		}catch (Exception e) {
		e.printStackTrace();
		}finally{
			conn.close();
		}//end finally try
		System.out.println(Object2JasonStr(list));
		return Object2JasonStr(list);
	}	
	
	//读取销售订单数据
	public static String read_pay_table_data(String start_pay_date, String end_pay_date ,String supply_co) throws SQLException
	{
		Connection conn = null;
		@SuppressWarnings("rawtypes")
		ArrayList<HashMap> list = new ArrayList<HashMap>();
		Statement stmt = null;
		try {  
			Class.forName(driverName);  
			conn = DriverManager.getConnection(dbURL, userName, userPwd );  
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM [test2].[dbo].[Payment]	"
					+ "WHERE [payment_time] BETWEEN '"+start_pay_date+"' AND '"+end_pay_date+"' AND [supply_co] = '"+supply_co+"'");
			while(rs.next()){
				HashMap<String, String> temp = new HashMap<String,String>();
				temp.put("payment_time", rs.getString(2));
				temp.put("payment_method", rs.getString(3));
				temp.put("payment_amount", rs.getString(4));
				list.add(temp);
			}
		}catch (Exception e) {
		e.printStackTrace();
		}finally{
			conn.close();
		}//end finally try
		System.out.println(Object2JasonStr(list));
		return Object2JasonStr(list);
	}	
	
	//读取销售订单数据
	public static String read_receivables_table_data(String start_receivables_date, String end_receivables_date ,String receive_co) throws SQLException
	{
		Connection conn = null;
		@SuppressWarnings("rawtypes")
		ArrayList<HashMap> list = new ArrayList<HashMap>();
		Statement stmt = null;
		try {  
			Class.forName(driverName);  
			conn = DriverManager.getConnection(dbURL, userName, userPwd );  
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM [test2].[dbo].[Receivables]	"
					+ "WHERE [receivables_time] BETWEEN '"+start_receivables_date+"' AND '"+end_receivables_date+"' AND [supply_co] = '"+receive_co+"'");
			while(rs.next()){
				HashMap<String, String> temp = new HashMap<String,String>();
				temp.put("receivables_time", rs.getString(2));
				temp.put("receivables_method", rs.getString(3));
				temp.put("receivables_amount", rs.getString(4));
				list.add(temp);
			}
		}catch (Exception e) {
		e.printStackTrace();
		}finally{
			conn.close();
		}//end finally try
		System.out.println(Object2JasonStr(list));
		return Object2JasonStr(list);
	}	
	
	//读取已分配数据
	public static String read_allocated_data(String month) throws SQLException
	{
		Connection conn = null;
		@SuppressWarnings("rawtypes")
		ArrayList<HashMap> list = new ArrayList<HashMap>();
		Statement stmt = null;
		try {  
			Class.forName(driverName);  
			conn = DriverManager.getConnection(dbURL, userName, userPwd );  
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT [allocate_order_number] ,[purchase_contract_number] ,[boat_number] ,[allocate_amount] ,[create_time] "
					+ " FROM [test2].[dbo].[Allocate_Boat]"
					+ "WHERE datediff(month,[dbo].[Allocate_Boat].create_time,'"+month+"')=0");
			while(rs.next()){
				HashMap<String, String> temp = new HashMap<String,String>();
				temp.put("allocate_order_number", rs.getString(1));
				temp.put("purchase_contract_number", rs.getString(2));
				temp.put("boat_number", rs.getString(3));
				temp.put("allocate_amount", rs.getString(4));
				temp.put("create_time", rs.getString(5));
				list.add(temp);
			}
		}catch (Exception e) {
		e.printStackTrace();
		}finally{
			conn.close();
		}//end finally try
		System.out.println(Object2JasonStr(list));
		return Object2JasonStr(list);
	}	
	
	//读取采购订单数据
	public static String read_purchase_order_data(String month) throws SQLException
	{
		Connection conn = null;
		@SuppressWarnings("rawtypes")
		ArrayList<HashMap> list = new ArrayList<HashMap>();
		Statement stmt = null;
		try {  
			Class.forName(driverName);  
			conn = DriverManager.getConnection(dbURL, userName, userPwd );  
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT [dbo].[Purchase_Order].purchase_contract_number, [dbo].[Sale_Associate_Purchase].sale_contract_number, [dbo].[Purchase_Order].orders_date, "
					+ "[dbo].[Purchase_Order].supply_co, [dbo].[Purchase_Order].product_name, [dbo].[Purchase_Order].purchase_amount, [dbo].[Purchase_Order].purchase_singleprice, "
					+ "[dbo].[Purchase_Order].purchase_totalprice, [dbo].[Purchase_Order].payment_method, [dbo].[Purchase_Order].creater, [dbo].[Purchase_Order].create_time "
					+ "FROM  [dbo].[Purchase_Order] LEFT JOIN [dbo].[Sale_Associate_Purchase] ON [dbo].[Purchase_Order].purchase_contract_number = [dbo].[Sale_Associate_Purchase].purchase_contract_number "
					+ "WHERE datediff(month,[dbo].[Purchase_Order].orders_date,'"+month+"')=0");
			while(rs.next()){
				HashMap<String, String> temp = new HashMap<String,String>();
				temp.put("purchase_contract_number", rs.getString(1));
				temp.put("sale_contract_number", rs.getString(2));
				temp.put("orders_date", rs.getString(3));
				temp.put("supply_co", rs.getString(4));
				temp.put("product_name", rs.getString(5));
				temp.put("purchase_amount", rs.getString(6));
				temp.put("purchase_singleprice", rs.getString(7));
				temp.put("purchase_totalprice", rs.getString(8));
				temp.put("payment_method", rs.getString(9));
				temp.put("creater", rs.getString(10));
				temp.put("create_time", rs.getString(11));
				list.add(temp);
			}
		}catch (Exception e) {
		e.printStackTrace();
		}finally{
			conn.close();
		}//end finally try
		System.out.println(Object2JasonStr(list));
		return Object2JasonStr(list);
	}	
	
	//读取提货数据
	public static String read_take_delivery_data(String boat_number) throws SQLException
	{
		Connection conn = null;
		@SuppressWarnings("rawtypes")
		ArrayList<HashMap> list = new ArrayList<HashMap>();
		Statement stmt = null;
		try {  
			Class.forName(driverName);  
			conn = DriverManager.getConnection(dbURL, userName, userPwd );  
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Allocate_Boat.allocate_order_number, Allocate_Boat.allocate_amount, Purchase_Order.supply_co, Sale_Order.receive_co, "
					+ "Datename(YEAR,Allocate_Boat.create_time)+'-'+Datename(MONTH,Allocate_Boat.create_time)+'-'+Datename(DAY,Allocate_Boat.create_time) as 'create_time' "
					+ "FROM Allocate_Boat INNER JOIN (Sale_Order INNER JOIN (Purchase_Order INNER JOIN Sale_Associate_Purchase "
					+ "ON Purchase_Order.purchase_contract_number = Sale_Associate_Purchase.purchase_contract_number) "
					+ "ON Sale_Order.sale_contract_number = Sale_Associate_Purchase.sale_contract_number) "
					+ "ON Allocate_Boat.purchase_contract_number = Sale_Associate_Purchase.purchase_contract_number "
					+ "WHERE Allocate_Boat.boat_number = '"+boat_number+"';");
			while(rs.next()){
				int deliveried_amount =0;
				HashMap<String, String> temp = new HashMap<String,String>();
				temp.put("allocate_order_number", rs.getString(1));
				temp.put("supply_co", rs.getString(3));
				temp.put("receive_co", rs.getString(4));
				temp.put("create_time", rs.getString(5).replace("-0", "-"));
				Statement stmt2 = conn.createStatement();
				ResultSet rs2 = stmt2.executeQuery("SELECT [delivery_amount] FROM [test2].[dbo].[Take_Delivery]"
						+ "WHERE [allocate_order_number] = '"+rs.getString(1)+"'"
						+ "AND  [boat_number] ='"+boat_number+"'");
				while(rs2.next()){
					deliveried_amount +=rs2.getInt(1);
				}
				temp.put("allocate_amount", String.valueOf(rs.getInt(2)-deliveried_amount));
				if(rs.getInt(2)-deliveried_amount>deliveried_amount)
					list.add(temp);
			}
		}catch (Exception e) {
		e.printStackTrace();
		}finally{
			conn.close();
		}//end finally try
		System.out.println(Object2JasonStr(list));
		return Object2JasonStr(list);
	}	
	
	//读取卸货数据
	public static String read_discharge_data(String boat_number) throws SQLException
	{
		Connection conn = null;
		@SuppressWarnings("rawtypes")
		ArrayList<HashMap> list = new ArrayList<HashMap>();
		Statement stmt = null;
		try {  
			Class.forName(driverName);  
			conn = DriverManager.getConnection(dbURL, userName, userPwd );  
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Take_Delivery.take_delivery_order_number, Take_Delivery.delivery_amount, "
					+ "Purchase_Order.supply_co, Sale_Order.receive_co, Take_Delivery.take_delivery_date "
					+ "FROM Take_Delivery INNER JOIN (Allocate_Boat INNER JOIN ((Sale_Associate_Purchase INNER JOIN Purchase_Order ON "
					+ "Sale_Associate_Purchase.purchase_contract_number = Purchase_Order.purchase_contract_number) INNER JOIN Sale_Order ON "
					+ "Sale_Associate_Purchase.sale_contract_number = Sale_Order.sale_contract_number) ON "
					+ "Allocate_Boat.purchase_contract_number = Purchase_Order.purchase_contract_number) ON "
					+ "Take_Delivery.allocate_order_number = Allocate_Boat.allocate_order_number "
					+ "WHERE Take_Delivery.boat_number = '"+boat_number+"';");
			while(rs.next()){
				int discharged_amount = 0;
				HashMap<String, String> temp = new HashMap<String,String>();
				temp.put("take_delivery_order_number", rs.getString(1));
				temp.put("supply_co", rs.getString(3));
				temp.put("receive_co", rs.getString(4));
				temp.put("take_delivery_date", rs.getString(5));
				Statement stmt2 = conn.createStatement();
				ResultSet rs2 = stmt2.executeQuery("SELECT [discharge_amount] FROM [test2].[dbo].[Discharge]"
						+ "WHERE [take_delivery_order_number] = '"+rs.getString(1)+"'"
						+ " AND  [boat_number] ='"+boat_number+"'");
				while(rs2.next()){
					discharged_amount += rs2.getInt(1);
				}
				temp.put("delivery_amount", String.valueOf(rs.getInt(2)-discharged_amount));
				if(discharged_amount==0)
				list.add(temp);
			}
		}catch (Exception e) {
		e.printStackTrace();
		}finally{
			conn.close();
		}//end finally try
		System.out.println(Object2JasonStr(list));
		return Object2JasonStr(list);
	}	
	
	//读取付款数据
	public static String read_payment_data(String type) throws SQLException
	{
		Connection conn = null;
		@SuppressWarnings("rawtypes")
		ArrayList<HashMap> list = new ArrayList<HashMap>();
		Statement stmt = null;
		try {  
			Class.forName(driverName);  
			conn = DriverManager.getConnection(dbURL, userName, userPwd );  
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT supply_co, Sum(purchase_totalprice) AS purchase_totalprice之合计"
					+ " FROM [dbo].[Purchase_Order]"
					+ " WHERE payment_method = '"+type+"'"
					+ " GROUP BY supply_co;"
					);
			while(rs.next()){
				float money_paid=0;
				Statement stmt2 = conn.createStatement();
				HashMap<String, String> temp = new HashMap<String,String>();
				temp.put("supply_co", rs.getString(1));
				temp.put("total_price", String.valueOf((float)(Math.round(rs.getFloat(2)*100/100))));
				ResultSet rs2 = stmt2.executeQuery("SELECT Sum([payment_amount]) AS payment_amount之合计"
					+ " FROM [dbo].[Payment]"
					+ " WHERE payment_method = '"+type+"'"
					+ " AND supply_co = '"+rs.getString(1)+"'");
				while(rs2.next()){
					money_paid += rs2.getFloat(1);
				}
				temp.put("total_paid", String.valueOf((float)(Math.round(money_paid*100/100))));
				temp.put("need_to_pay", String.valueOf((float)(Math.round((rs.getFloat(2)-money_paid)*100/100))));
				if(rs.getFloat(2)-money_paid>0){
					list.add(temp);
				}
			}
		}catch (Exception e) {
		e.printStackTrace();
		}finally{
			conn.close();
		}//end finally try
		System.out.println(Object2JasonStr(list));
		return Object2JasonStr(list);
	}	
	
	//读取收款数据
	public static String read_receivables_data() throws SQLException
	{
		Connection conn = null;
		@SuppressWarnings("rawtypes")
		ArrayList<HashMap> list = new ArrayList<HashMap>();
		Statement stmt = null;
		try {  
			Class.forName(driverName);  
			conn = DriverManager.getConnection(dbURL, userName, userPwd ); 
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Sale_Order.receive_co, Sum(Discharge.discharge_amount) AS discharge_amount之合计 "
					+ "FROM ((Allocate_Boat INNER JOIN Take_Delivery ON Allocate_Boat.allocate_order_number = Take_Delivery.allocate_order_number)"
					+ " INNER JOIN Discharge ON Take_Delivery.take_delivery_order_number = Discharge.take_delivery_order_number) "
					+ "INNER JOIN ((Purchase_Order INNER JOIN Sale_Associate_Purchase "
					+ "ON Purchase_Order.purchase_contract_number = Sale_Associate_Purchase.purchase_contract_number) "
					+ "INNER JOIN Sale_Order ON Sale_Associate_Purchase.sale_contract_number = Sale_Order.sale_contract_number) "
					+ "ON Allocate_Boat.purchase_contract_number = Purchase_Order.purchase_contract_number "
					+ "GROUP BY Sale_Order.receive_co;");
			while(rs.next()){
				HashMap<String, String> temp = new HashMap<String,String>();
				temp.put("receive_co", rs.getString(1));
				float money_received=0;
				float total_price = 0;
				Statement stmt2 = conn.createStatement();
				ResultSet rs2 = stmt2.executeQuery("SELECT Sum(Discharge.discharge_amount) AS discharge_amount之合计, Sale_Order.receive_co, Sale_Order.sale_singleprice "
						+ "FROM (((Purchase_Order INNER JOIN Allocate_Boat ON "
						+ "Purchase_Order.purchase_contract_number = Allocate_Boat.purchase_contract_number) "
						+ "INNER JOIN (Sale_Associate_Purchase INNER JOIN Sale_Order "
						+ "ON Sale_Associate_Purchase.sale_contract_number = Sale_Order.sale_contract_number) "
						+ "ON Purchase_Order.purchase_contract_number = Sale_Associate_Purchase.purchase_contract_number) "
						+ "INNER JOIN Take_Delivery ON Allocate_Boat.allocate_order_number = Take_Delivery.allocate_order_number) "
						+ "INNER JOIN Discharge ON Take_Delivery.take_delivery_order_number = Discharge.take_delivery_order_number "
						+ "where Sale_Order.receive_co = '"+rs.getString(1)+"' "
						+ "GROUP BY Sale_Order.receive_co, Sale_Order.sale_singleprice;"
						);
				while(rs2.next()){
					total_price += (float)(Math.round(rs2.getFloat(1)*rs2.getFloat(3)*100/100));
				}
				Statement stmt3 = conn.createStatement();
				ResultSet rs3 = stmt3.executeQuery("SELECT Sum([receivables_amount]) AS receivables_amount之合计"
					+ " FROM [dbo].[Receivables]"
					+ " WHERE receive_co = '"+rs.getString(1)+"'");
				while(rs3.next()){
					money_received += rs3.getFloat(1);
				}
				temp.put("total_received", String.valueOf((float)(Math.round(money_received*100/100))));
				temp.put("need_to_receive", String.valueOf((float)(Math.round((total_price-money_received)*100/100))));
				temp.put("total_price", String.valueOf((float)total_price));
				if(total_price-money_received>0){
					list.add(temp);
				}
			}
		}catch (Exception e) {
		e.printStackTrace();
		}finally{
			conn.close();
		}//end finally try
		System.out.println(Object2JasonStr(list));
		return Object2JasonStr(list);
	}	
	
	//读取分配数据
	public static String read_allocate_data() throws SQLException
	{
		Connection conn = null;
		@SuppressWarnings("rawtypes")
		ArrayList<HashMap> list = new ArrayList<HashMap>();
		Statement stmt = null;
		try {  
			Class.forName(driverName);  
			conn = DriverManager.getConnection(dbURL, userName, userPwd );  
			stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT Purchase_Order.purchase_contract_number, Sale_Order.receive_co, Purchase_Order.supply_co,"
					+ "Purchase_Order.purchase_amount, Purchase_Order.purchase_singleprice , Purchase_Order.payment_method ,Purchase_Order.product_name "
					+ "FROM Sale_Order INNER JOIN (Purchase_Order INNER JOIN Sale_Associate_Purchase ON "
					+ "Purchase_Order.purchase_contract_number = Sale_Associate_Purchase.purchase_contract_number) ON "
					+ "Sale_Order.sale_contract_number = Sale_Associate_Purchase.sale_contract_number;"
					);
			while(rs.next()){
				float money_paid = 0;
				float money_allocated = 0;
				int amount_allocated = 0;
				int amount_left = 0;
				HashMap<String, String> temp = new HashMap<String,String>();
				temp.put("purchase_contract_number", rs.getString(1));
				temp.put("receive_co", rs.getString(2));
				temp.put("supply_co", rs.getString(3));
				//temp.put("purchase_amount", rs.getString(4));
				//temp.put("purchase_singleprice", rs.getString(5));
				//temp.put("payment_method", rs.getString(6));
				temp.put("product_name", rs.getString(7));
				Statement stmt2 = conn.createStatement();
				Statement stmt3 = conn.createStatement();
				Statement stmt4 = conn.createStatement();
				ResultSet rs2 = stmt2.executeQuery("SELECT Sum([payment_amount]) AS payment_amount之合计"
					+ " FROM [dbo].[Payment]"
					+ " WHERE supply_co = '"+rs.getString(3)+"'"
					+ " AND payment_method ='"+rs.getString(6)+"'");
				while(rs2.next()){
					money_paid += rs2.getFloat(1);  //获取已付款总金额
				}
				ResultSet rs3 = stmt3.executeQuery("SELECT Sum(Take_Delivery.delivery_amount) AS delivery_amount之合计 "
						+ "FROM (Take_Delivery INNER JOIN Allocate_Boat ON Take_Delivery.allocate_order_number = Allocate_Boat.allocate_order_number) "
						+ "INNER JOIN Purchase_Order ON Allocate_Boat.purchase_contract_number = Purchase_Order.purchase_contract_number "
						+ "WHERE Purchase_Order.purchase_contract_number = '"+rs.getString(1)+"'");
				while(rs3.next()){
					amount_allocated +=rs3.getInt(1);  //获取已分配总量（按合同编号）
				}
				ResultSet rs4 = stmt4.executeQuery("SELECT Allocate_Boat.purchase_contract_number, Sum(Allocate_Boat.allocate_amount) AS allocate_amount之合计, Purchase_Order.purchase_singleprice "
						+ "FROM Allocate_Boat INNER JOIN Purchase_Order ON Allocate_Boat.purchase_contract_number = Purchase_Order.purchase_contract_number "
						+ "WHERE Purchase_Order.supply_co = '"+rs.getString(3)+"' "
						+ "AND Purchase_Order.payment_method = '"+rs.getString(6)+"'"
						+ "GROUP BY Allocate_Boat.purchase_contract_number, Purchase_Order.purchase_singleprice;");
				while(rs4.next()){
					money_allocated += rs4.getFloat(2)*rs4.getFloat(3);  //获取已经分配总金额
				}
				float money_left = money_paid-money_allocated;  //余款
				if((money_left/rs.getFloat(5))-(rs.getInt(4)-amount_allocated)>0){
					amount_left = rs.getInt(4)-amount_allocated;
				}else{
					amount_left = (int) (money_left/rs.getFloat(5));
				}
				temp.put("amount_left", String.valueOf(amount_left));
				//temp.put("money_left", String.valueOf((float)(Math.round(money_left*100/100))));
				if(amount_left>0){
					list.add(temp);
				}
			}
		}catch (Exception e) {
		e.printStackTrace();
		}finally{
			conn.close();
		}//end finally try
		System.out.println(Object2JasonStr(list));
		return Object2JasonStr(list);
	}	
	
	
	//读取损耗数据
	public static String read_loss_data(String month) throws SQLException
	{
		Connection conn = null;
		@SuppressWarnings("rawtypes")
		ArrayList<HashMap> list = new ArrayList<HashMap>();
		Statement stmt = null;
		try {  
			Class.forName(driverName);  
			conn = DriverManager.getConnection(dbURL, userName, userPwd );  
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Take_Delivery.boat_number, Sum(Take_Delivery.delivery_amount) AS delivery_amount之合计, Sum(Discharge.discharge_amount) AS discharge_amount之合计 "
					+ "FROM Take_Delivery INNER JOIN Discharge ON Take_Delivery.take_delivery_order_number = Discharge.take_delivery_order_number "
					+ "WHERE datediff(month,[dbo].[Discharge].discharge_date,'"+month+"')=0 "
					+ "GROUP BY Take_Delivery.boat_number, Discharge.discharge_date ");
			while(rs.next()){
				HashMap<String, String> temp = new HashMap<String,String>();
				temp.put("boat_number", rs.getString(1));
				temp.put("loss_amount", String.valueOf(rs.getInt(2)-rs.getInt(3)));
				float loss_amount = rs.getFloat(2)-rs.getFloat(3);
				float loss_rate = (loss_amount/rs.getFloat(2))*100;
				temp.put("loss_rate", String.valueOf(loss_rate)+"%");
				list.add(temp);
			}
		}catch (Exception e) {
		e.printStackTrace();
		}finally{
			conn.close();
		}//end finally try
		System.out.println(Object2JasonStr(list));
		return Object2JasonStr(list);
	}	
	
	
	//读发货数据
	public static String read_delivery_data(String start_delivery_date, String end_delivery_date ,String supply_co) throws SQLException
	{
		Connection conn = null;
		@SuppressWarnings("rawtypes")
		ArrayList<HashMap> list = new ArrayList<HashMap>();
		Statement stmt = null;
		try {  
			Class.forName(driverName);  
			conn = DriverManager.getConnection(dbURL, userName, userPwd );  
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Take_Delivery.boat_number, Sum(Take_Delivery.delivery_amount) AS delivery_amount之合计 "
					+ "FROM (Allocate_Boat INNER JOIN Take_Delivery ON Allocate_Boat.allocate_order_number = Take_Delivery.allocate_order_number) "
					+ "INNER JOIN Purchase_Order ON Allocate_Boat.purchase_contract_number = Purchase_Order.purchase_contract_number "
					+ "WHERE (((Purchase_Order.supply_co)='"+supply_co+"')AND Take_Delivery.take_delivery_date BETWEEN '"+start_delivery_date+"' and '"+end_delivery_date+"')"
							+ " GROUP BY Take_Delivery.boat_number;");
			while(rs.next()){
				HashMap<String, String> temp = new HashMap<String,String>();
				temp.put("boat_number", rs.getString(1));
				temp.put("delivery_amount", rs.getString(2));
				list.add(temp);
			}
		}catch (Exception e) {
		e.printStackTrace();
		}finally{
			conn.close();
		}//end finally try
		System.out.println(Object2JasonStr(list));
		return Object2JasonStr(list);
	}	
	
	//读卸货数据
	public static String read_discharge_amounnt_data(String start_discharge_date, String end_discharge_date ,String receive_co) throws SQLException
	{
		Connection conn = null;
		@SuppressWarnings("rawtypes")
		ArrayList<HashMap> list = new ArrayList<HashMap>();
		Statement stmt = null;
		try {  
			Class.forName(driverName);  
			conn = DriverManager.getConnection(dbURL, userName, userPwd );  
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Discharge.discharge_amount, Discharge.boat_number FROM"
					+ " ((Sale_Associate_Purchase INNER JOIN Sale_Order ON Sale_Associate_Purchase.sale_contract_number = Sale_Order.sale_contract_number)"
					+ " INNER JOIN Allocate_Boat ON Sale_Associate_Purchase.purchase_contract_number = Allocate_Boat.purchase_contract_number) INNER JOIN"
					+ " (Take_Delivery INNER JOIN Discharge ON Take_Delivery.take_delivery_order_number = Discharge.take_delivery_order_number) ON "
					+ "Allocate_Boat.allocate_order_number = Take_Delivery.allocate_order_number WHERE (((Sale_Order.receive_co)='"+receive_co+"') AND ((Discharge.discharge_date) "
					+ "Between '"+start_discharge_date+"' And '"+end_discharge_date+"')) GROUP BY Discharge.discharge_amount, Discharge.boat_number;");
			while(rs.next()){
				HashMap<String, String> temp = new HashMap<String,String>();
				temp.put("boat_number", rs.getString(1));
				temp.put("discharge_amount", rs.getString(2));
				list.add(temp);
			}
		}catch (Exception e) {
		e.printStackTrace();
		}finally{
			conn.close();
		}//end finally try
		System.out.println(Object2JasonStr(list));
		return Object2JasonStr(list);
	}	
	
	public static String checkUser(String username,String password) throws ClassNotFoundException, SQLException{
		Connection conn = null;
		Statement stmt = null;
		String power=null;
		System.out.println(username+password);
        try {
        	Class.forName(driverName);  
			conn = DriverManager.getConnection(dbURL, userName, userPwd );  
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT [power]  FROM [test2].[dbo].[users]"
					+ "  where PWDCOMPARE('"+username+"',user_name) = 1 "
					+ "  and PWDCOMPARE('"+password+"',[password_md5]) = 1");
			try {
	           while(rs.next()){
	        	   power = rs.getString(1);
        	   }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return null;
	        }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }finally{
			conn.close();
		}//end finally try
 	   System.out.println(power);
        return power;
    }
	
	//对象能转换成JSON对象
	public static String Object2JasonStr(Object obj) {
		String re = "";
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			re = objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}


}
