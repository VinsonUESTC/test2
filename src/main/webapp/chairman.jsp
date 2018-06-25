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
		<script type="text/javascript" src="page_js/associate.js"></script>
		<script type="text/javascript" src="page_js/allocate.js"></script>
		<script type="text/javascript" src="page_js/order.js"></script>
		<script type="text/javascript" src="page_js/loss.js"></script>
		<script type="text/javascript" src="page_js/manage.js"></script>
	<body data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-3" data-genuitec-path="/test2/WebRoot/index.jsp">
	<div id="main_page" class="easyui-navpanel" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-3" data-genuitec-path="/test2/WebRoot/index.jsp">
		<header>
			<div class="m-toolbar">
				<div class="m-title">主菜单</div>
			</div>
		</header>
		<footer>
			<div class="m-toolbar">
				<div class="m-title" id="usertitle">你好：<%=username %></div>
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
						case 'receivables':
							ReadReceiveTable();
							break;
						case 'take_delivery':
							InitTransportData();
							break;
						case 'discharge':
							InitTransportData();
							break;
						case 'take_delivery_history':
							InitTransportData();
							break;
						case 'discharge_history':
							InitTransportData();
							break;
                    }
                }
                ">
	            <li value="quest_purchase_order">查询已有采购单</li>
	            <li value="quest_sale_order">查询已有销售单</li>
	            <li value="payment">待付款查询</li>
	            <li value="receivables">待收款查询</li>
	            <li value="quest_allocate">查询已分配船只</li>
	            <li value="loss">损耗查询</li>
	            <li value="quest_delivery">发货查询</li>
	            <li value="quest_discharge">卸货查询</li>
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

	<!-- 付款 -->
	<div id="payment"class="easyui-navpanel" style="position:relative;">
		<header>
			<div class="m-toolbar">
				<div id="payment_title" class="m-title"></div>
				<div class="m-left">
					<a href="javascript:void(0)" class="easyui-linkbutton m-back" plain="true" outline="true" style="width:50px" onclick="$.mobile.go('#main_page','slide','right')">返回</a>
				</div>
			</div>
		</header>
		<div  class="easyui-tabs"  data-options="fit:true,border:false,pill:true,justified:true,tabWidth:80,tabHeight:35">
			<div title="现金" style="padding:10px">
				<div style="text-align:center;padding:5px">
					<table style="width:100%;">
						<tr>
							<td>
								<select id="payment_cash_supply" label="收款方：" class="easyui-combobox"   data-options="valueField:'id',textField:'text',required:true"  prompt="选择收款方"  style="width:100%"></select>
							</td>
							<td rowspan="2">
								<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="height:32px" onclick="QuestPaymentOrder('cash')">查询</a>
							</td>
						</tr>
						<tr>
							<td>
								<input id="cash_remain" class="easyui-numberbox" label="当前余款：" prefix="￥" data-options="min:0,precision:2,readonly:true"style="width:100%">
							</td>
						</tr>
					</table>
				</div>
				<table id="payment_cash_table" class="easyui-datagrid" data-options="singleSelect:true,border:false,fitColumns:true"  style="width:100%;height:80%;"  rownumbers="true" pagination="true">
					<thead>
					<tr>
						<th field="purchase_order_number" >采购订单号</th>
						<th field="supply_co" >供应商</th>
						<th field="total_price" data-options="formatter:fmoney">总金额</th>
						<th field="orders_date" >采购日期</th>
						<th field="creater" >创建人</th>
						<th field="createtime" >创建时间</th>
					</tr>
					</thead>
				</table>
			</div>
			<div title="承兑" style="padding:10px">
				<div style="text-align:center;padding:5px">
					<table style="width:100%;">
						<tr>
							<td>
								<select id="payment_bill_supply" label="收款方：" class="easyui-combobox"   data-options="valueField:'id',textField:'text',required:true"  prompt="选择收款方"  style="width:100%"></select>
							</td>
							<td rowspan="2">
								<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="height:32px" onclick="QuestPaymentOrder('bill')">查询</a>
							</td>
						</tr>
						<tr>
							<td>
								<input id="bill_remain" class="easyui-numberbox" label="当前余款：" prefix="￥" data-options="min:0,precision:2,readonly:true" style="width:100%">
							</td>
						</tr>
					</table>
				</div>
				<table id="payment_bill_table" class="easyui-datagrid" data-options="singleSelect:true,border:false,fitColumns:true"  style="width:100%;height:80%;"  rownumbers="true" pagination="true">
					<thead>
					<tr>
						<th field="purchase_order_number" >采购订单号</th>
						<th field="supply_co" >供应商</th>
						<th field="total_price" data-options="formatter:fmoney">总金额</th>
						<th field="orders_date" >采购日期</th>
						<th field="creater" >创建人</th>
						<th field="createtime" >创建时间</th>
					</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
		
		<!-- 查询已分配船只 -->
		<div id="quest_allocate"class="easyui-navpanel" style="position:relative;">
			<header>
				<div class="m-toolbar">
					<div id="quest_allocate_title" class="m-title"></div>
					 <div class="m-left">
                    	<a href="javascript:void(0)" class="easyui-linkbutton m-back" plain="true" outline="true" style="width:50px" onclick="$.mobile.go('#main_page','slide','right')">返回</a>
                	</div>
				</div>
			</header>
			<div style="text-align:center;padding:5px">
				<input class="date-month3" label="选择月份：" data-options="required:true" style="width:70%;">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="height:32px" onclick="QuestAllocate()">查询</a>
			</div>
			<table id="quest_allocate_table" class="easyui-datagrid" data-options="singleSelect:true,border:false,fitColumns:true"  style="width:100%;height:80%;"  rownumbers="true" pagination="true">
				<thead>
					<tr>
						<th field="purchase_contract_number" >采购合同号</th>
						<th field="boat_number" >船只编号</th>
						<th field="allocate_amount" >分配数量</th>
						<th field="create_time" >分配时间</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<!-- 损耗 -->
		<div id="loss"class="easyui-navpanel" style="position:relative;">
			<header>
				<div class="m-toolbar">
					<div id="loss_title" class="m-title"></div>
					 <div class="m-left">
                    	<a href="javascript:void(0)" class="easyui-linkbutton m-back" plain="true" outline="true" style="width:50px" onclick="$.mobile.go('#main_page','slide','right')">返回</a>
                	</div>
				</div>
			</header>
			<div style="text-align:center;padding:5px">
				<input class="date-month4" label="选择月份：" data-options="required:true" style="width:70%;">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="height:32px" onclick="QuestLoss()">查询</a>
			</div>
			<table id="quest_loss_table" class="easyui-datagrid" data-options="singleSelect:true,border:false,fitColumns:true"  style="width:100%;height:80%;"  rownumbers="true" pagination="true">
				<thead>
					<tr>
						<th field="boat_number" >船号</th>
						<th field="loss_amount" >损耗数量</th>
						<th field="loss_rate" >损耗比例</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<!-- 收款 -->
		<div id="receivables"class="easyui-navpanel" style="position:relative;">
			<header>
				<div class="m-toolbar">
					<div id="receivables_title" class="m-title"></div>
					 <div class="m-left">
                    	<a href="javascript:void(0)" class="easyui-linkbutton m-back" plain="true" outline="true" style="width:50px" onclick="$.mobile.go('#main_page','slide','right')">返回</a>
                	</div>
				</div>
			</header>
			<table id="receivables_table" class="easyui-datagrid" data-options="singleSelect:true,border:false,fitColumns:true"  style="width:100%;height:80%;"  rownumbers="true" pagination="true">
				<thead>
					<tr>
						<th field="receive_co" >收货方</th>
						<th field="total_price" data-options="formatter:fmoney" >总金额</th>
						<th field="total_received"  data-options="formatter:fmoney">已经收到金额</th>
						<th field="need_to_receive"  data-options="formatter:fmoney">剩余待收金额</th>
					</tr>
				</thead>
			</table>
		</div>
				
		<!-- 发货查询 -->
		<div id="quest_delivery"class="easyui-navpanel" style="position:relative;">
			<header>
				<div class="m-toolbar">
					<div id="quest_delivery_title" class="m-title"></div>
					 <div class="m-left">
                    	<a href="javascript:void(0)" class="easyui-linkbutton m-back" plain="true" outline="true" style="width:50px" onclick="$.mobile.go('#main_page','slide','right')">返回</a>
                	</div>
				</div>
			</header>
			<div style="text-align:center;padding:5px">
			<table style="border:0px;width:100%">
				<tr>
					<td style="width:80%;">
						<input class="easyui-datebox" label="起始日期：" id="delivery_start_date"  prompt="请输入日期" data-options="required:true,editable:false,panelWidth:220,panelHeight:240,iconWidth:30"  data-options="required:true" style="width:100%;">
					</td>
					<td rowspan="3" style="text-align:center;">
						<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="height:30px;" onclick="QuestDeliveryAmount()">查询</a>
					</td>
				</tr>
				<tr>
					<td>
						<input class="easyui-datebox" label="截止日期：" id="delivery_end_date"  prompt="请输入日期" data-options="required:true,editable:false,panelWidth:220,panelHeight:240,iconWidth:30"  data-options="required:true" style="width:100%;">
					</td>
				</tr>
				<tr>
					<td>
						<select id="supply_co_delivery" label="供货方：" id ="supply_co" class="easyui-combobox"   data-options="valueField:'id',textField:'text',required:true"  prompt="选择供货方"  style="width:100%"></select>
					</td>
				</tr>
			</table>
			</div>
			<table id="quest_delivery_table" class="easyui-datagrid" data-options="singleSelect:true,border:false,fitColumns:true"  style="width:100%;height:80%;"  rownumbers="true" pagination="true">
				<thead>
					<tr>
						<th field="boat_number">船号</th>
						<th field="delivery_amount" >提货数量</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<!-- 卸货查询 -->
		<div id="quest_discharge"class="easyui-navpanel" style="position:relative;">
			<header>
				<div class="m-toolbar">
					<div id="quest_discharge_title" class="m-title"></div>
					 <div class="m-left">
                    	<a href="javascript:void(0)" class="easyui-linkbutton m-back" plain="true" outline="true" style="width:50px" onclick="$.mobile.go('#main_page','slide','right')">返回</a>
                	</div>
				</div>
			</header>
			<div style="text-align:center;padding:5px">
			<table style="border:0px;width:100%">
				<tr>
					<td style="width:80%;">
						<input class="easyui-datebox" label="起始日期：" id="discharge_start_date"  prompt="请输入日期" data-options="required:true,editable:false,panelWidth:220,panelHeight:240,iconWidth:30"  data-options="required:true" style="width:100%;">
					</td>
					<td rowspan="3" style="text-align:center;">
						<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="height:30px;" onclick="QuestDischargeAmount()">查询</a>
					</td>
				</tr>
				<tr>
					<td>
						<input class="easyui-datebox" label="截止日期：" id="discharge_end_date"  prompt="请输入日期" data-options="required:true,editable:false,panelWidth:220,panelHeight:240,iconWidth:30"  data-options="required:true" style="width:100%;">
					</td>
				</tr>
				<tr>
					<td>
						<select id="receive_co_discharge" label="收货方：" name ="supply_co" class="easyui-combobox"   data-options="valueField:'id',textField:'text',required:true"  prompt="选择供货方"  style="width:100%"></select>
					</td>
				</tr>
			</table>
			</div>
			<table id="quest_discharge_table" class="easyui-datagrid" data-options="singleSelect:true,border:false,fitColumns:true"  style="width:100%;height:80%;"  rownumbers="true" pagination="true">
				<thead>
					<tr>
						<th field="boat_number">船号</th>
						<th field="discharge_amount" >卸货数量</th>
					</tr>
				</thead>
			</table>
		</div>
		
	</body>
	<script type="text/javascript">
        $(function(){
            var user = window.sessionStorage.getItem("username");
            InitForm();
            var date_month = null;
        });
	</script>
</html>
