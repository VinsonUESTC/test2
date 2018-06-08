<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String power = (String)session.getAttribute("power");
String username = (String)session.getAttribute("username");
if(power==null){
	response.sendRedirect("login.jsp");
	return;
}
 %>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
		<title>Start</title>
		<link rel="stylesheet" type="text/css" href="resource/jquery-easyui-1.5.3/themes/metro/easyui.css">
		<link rel="stylesheet" type="text/css" href="resource/jquery-easyui-1.5.3/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="resource/jquery-easyui-1.5.3/themes/mobile.css">
		<script type="text/javascript" src="resource/jquery-easyui-1.5.3/jquery.min.js"></script>
		<script type="text/javascript" src="resource/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="resource/jquery-easyui-1.5.3/jquery.easyui.mobile.js"></script>
		<script type="text/javascript" src="resource/jquery-easyui-1.5.3/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="page_js/public.js"></script>
		<script type="text/javascript" src="page_js/receive.js"></script>
		<script type="text/javascript" src="page_js/pay.js"></script>
		<script type="text/javascript" src="page_js/quest.js"></script>
		<script type="text/javascript" src="page_js/delivery.js"></script>
		<script type="text/javascript" src="page_js/order.js"></script>
	<body data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-514" data-genuitec-path="/test2/WebRoot/sale_manager.jsp">
		<div id="main_page" class="easyui-navpanel" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-514" data-genuitec-path="/test2/WebRoot/sale_manager.jsp">
	        <header>
	            <div class="m-toolbar">
	                <div class="m-title">主菜单</div>
	            </div>
	        </header>
	        <footer>
	        	<div class="m-toolbar">
	                <div id="usertitle" class="m-title">你好：<%=username %></div>
	            </div>
	        </footer>
	        <ul class="easyui-datalist" data-options="
                fit: true,
                lines: true,
                border: false,
                textFormatter: function(value){
                    return '<a href\'javascript:void(0)\' class=\'datalist-link\'>' + value + '</a>';
                },
                onClickRow: function(index,row){
                    $('#'+row.value+'_title').html(row.text);
                    $.mobile.go('#'+row.value);
                    switch(row.value){
                    	case 'allocate':
							InitAllocateData();
							break;
						case 'associate_purchase_to_sale':
							InitAssociateData();
							break;
						case 'payment':
							ReadPayTable();
							break;
						case 'receivables':
							ReadReceiveTable();
							break;
                    }
                }
                ">
	            <li value="quest_purchase_order">查询已有采购单</li>
	            <li value="quest_sale_order">查询已有销售单</li>
	        </ul>
		</div>
		
	    <!-- 采购单查询 -->
		<div id="quest_purchase_order"class="easyui-navpanel" style="position:relative;">
			<header>
				<div class="m-toolbar">
					<div id="quest_purchase_order_title" class="m-title"></div>
					 <div class="m-left">
                    	<a href="javascript:void(0)" class="easyui-linkbutton m-back" plain="true" outline="true" style="width:50px" onclick="$.mobile.go('#main_page','slide','right')">返回</a>
                	</div>
				</div>
			</header>
			<div style="text-align:center;padding:5px">
				<input class="date-month" label="选择月份：" data-options="required:true" style="width:70%;">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="height:32px" onclick="QuestPurchaseOrder()">查询</a>
			</div>
			<table id="quest_purchase_order_table" class="easyui-datagrid" data-options="singleSelect:true,border:false,fitColumns:true"  style="width:100%;height:80%;"  rownumbers="true" pagination="true">
				<thead>
					<tr>
						<th field="purchase_contract_number">采购合同号</th>
						<th field="sale_contract_number">关联销售单号</th>
						<th field="orders_date" >采购日期</th>
						<th field="supply_co" >采购方</th>
						<th field="product_name" >品名</th>
						<th field="purchase_amount" >采购数量</th>
						<th field="purchase_singleprice"  data-options="formatter:fmoney">采购单价</th>
						<th field="purchase_totalprice"  data-options="formatter:fmoney">采购总价</th>
						<th field="payment_method" >付款方式</th>
						<th field="creater" >创建人</th>
						<th field="create_time" >创建时间</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<!-- 销售单查询 -->
		<div id="quest_sale_order"class="easyui-navpanel" style="position:relative;">
			<header>
				<div class="m-toolbar">
					<div id="quest_sale_order_title" class="m-title"></div>
					 <div class="m-left">
                    	<a href="javascript:void(0)" class="easyui-linkbutton m-back" plain="true" outline="true" style="width:50px" onclick="$.mobile.go('#main_page','slide','right')">返回</a>
                	</div>
				</div>
			</header>
			<div style="text-align:center;padding:5px">
				<input class="date-month2" label="选择月份：" data-options="required:true" style="width:70%;">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="height:32px" onclick="QuestsaleOrder()">查询</a>
			</div>
			<table id="quest_sale_order_table" class="easyui-datagrid" data-options="singleSelect:true,border:false,fitColumns:true"  style="width:100%;height:80%;"  rownumbers="true" pagination="true">
				<thead>
					<tr>
						<th field="sale_contract_number" >销售合同号</th>
						<th field="sales_date" >销售日期</th>
						<th field="receive_co" >销售方</th>
						<th field="product_name" >品名</th>
						<th field="sale_amount" >销售数量</th>
						<th field="sale_singleprice"  data-options="formatter:fmoney">销售单价</th>
						<th field="sale_totalprice"  data-options="formatter:fmoney">销售总价</th>
						<th field="creater" >创建人</th>
						<th field="create_time" >创建时间</th>
					</tr>
				</thead>
			</table>
		</div>
		
	</body>
	<script type="text/javascript">
		var supply_co_data = [{
		    "id":"泰州梅兰化工有限公司",
		    "text":"泰州梅兰化工有限公司"
		},{
		    "id":"江苏大和氯碱化工有限公司",
		    "text":"江苏大和氯碱化工有限公司"
		},{
		    "id":"江苏海兴化工有限公司",
		    "text":"江苏海兴化工有限公司",
		}];
		var receive_co_data = [{
		    "id":"阜宁澳洋科技股份有限公司",
		    "text":"阜宁澳洋科技股份有限公司"
		}];
		var product_name_data = [{
		    "id":"液碱（32%）",
		    "text":"液碱（32%）"
		}];
		$(function(){
			InitForm();
			var date_month = null;
		});
	</script>
</html>
