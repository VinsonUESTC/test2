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
	<body data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-3" data-genuitec-path="/test2/WebRoot/index.jsp">
		<div id="main_page" class="easyui-navpanel" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-3" data-genuitec-path="/test2/WebRoot/index.jsp">
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
	            <li value="sale_order">新增销售订单</li>
	            <li value="purchase_order">新增采购订单</li>
	            <li value="associate_purchase_to_sale">关联销售单</li>
	            <li value="quest_purchase_order">查询已有采购单</li>
	            <li value="quest_sale_order">查询已有销售单</li>
	            <li value="quest_allocate">查询已分配船只</li>
	            <li value="loss">损耗查询</li>
	            <li value="quest_delivery">发货查询</li>
	            <li value="quest_discharge">卸货查询</li>
	        </ul>
		</div>
	
	
		<!-- 销售订单 -->
		<div id="sale_order"class="easyui-navpanel" style="position:relative;padding:20px">
			<header>
				<div class="m-toolbar">
					<div id="sale_order_title" class="m-title"></div>
					<div class="m-right">
						<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" outline="true" onclick="ResetSaleOrder()" style="width:60px">重置</a>
					</div>
					 <div class="m-left">
                    	<a href="javascript:void(0)" class="easyui-linkbutton m-back" plain="true" outline="true" style="width:50px" onclick="$.mobile.go('#main_page','slide','right')">返回</a>
                	</div>
				</div>
			</header>
			<form id="sale_order_form" action="ajax/insert_sale_order_data" method="post">
				<div style="margin-bottom:10px">
					<input id="sale_contract_number_form"class="easyui-textbox" label="销售单号："   name="sale_contract_number" data-options="readonly:true" style="width:100%">
				</div>
				<div style="margin-bottom:10px">
					<input class="easyui-datebox" label="销售日期：" name="sales_date"  prompt="请输入日期" data-options="required:true,editable:false,panelWidth:220,panelHeight:240,iconWidth:30" style="width:100%">
				</div>
				<div style="margin-bottom:10px">
					<select id="receive_co_form" label="收货方：" name ="receive_co" class="easyui-combobox"   data-options="valueField:'id',textField:'text',required:true"  prompt="选择收货方"  style="width:100%"></select>
				</div>
				<div style="margin-bottom:10px">
					<select id="sale_product_form"  label="产品名称："  name ="sale_product" class="easyui-combobox"   data-options="valueField:'id',textField:'text',required:true"   prompt="选择产品类型"  style="width:100%"></select>
				</div>
				<div style="margin-bottom:10px">
					<input id="sale_amount_form" class="easyui-numberbox"  label="销售数量：" name="sale_amount" data-options="required:true"  prompt="请输入数量" style="width:100%">
				</div>
				<div style="margin-bottom:10px">
					<input id="sale_singleprice_form" class="easyui-numberbox" label="销售单价：" name="sale_singleprice" prompt="请输入单价" prefix="￥"   data-options="min:0,precision:2,required:true"  style="width:100%">
				</div>
				<div style="margin-bottom:10px">
					<input id="sale_totalprice_form" class="easyui-numberbox" label="销售总价："  name="sale_totalprice"  value="0"prefix="￥"   data-options="min:0,precision:2,readonly:true"  style="width:100%">
				</div>
				<div style="margin:50px 0 0;text-align:center">
		            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:100px;height:30px" onclick="SubmitFormsale()">提交</a>
		        </div>
			</form>
		</div>
		<div id="submit_sale_alert" class="easyui-window" title="提示" 
   		data-options="
   			modal:true,
   			closed:true,
   			collapsible:false,
   			minimizable:false,
   			maximizable:false
  		" style="width:90%;height:auto;padding:0px;">
  			<div style="text-align:center;padding:5px">
  				是否确认提交？
	    	</div>
	    	<div style="text-align:center;padding:5px">
  				<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="$('#sale_order_form').submit();">确认</a>
  				<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="$('#submit_sale_alert').window('close');">关闭</a>
	    	</div>
	    </div>
		
		<!-- 采购订单 -->
		<div id="purchase_order"class="easyui-navpanel" style="position:relative;padding:20px">
			<header>
				<div class="m-toolbar">
					<div id="purchase_order_title" class="m-title"></div>
					<div class="m-right">
						<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" outline="true" onclick="ResetPurchaseOrder()" style="width:60px">重置</a>
					</div>
					 <div class="m-left">
                    	<a href="javascript:void(0)" class="easyui-linkbutton m-back" plain="true" outline="true" style="width:50px" onclick="$.mobile.go('#main_page','slide','right')">返回</a>
                	</div>
				</div>
			</header>
			<form id="purchase_order_form" action="ajax/insert_purchase_order_data" method="post" >
				<div style="margin-bottom:10px">
					<input id="purchase_contract_number_form"class="easyui-textbox" label="采购单号："   name="purchase_contract_number" data-options="readonly:true" style="width:100%">
				</div>
				<div style="margin-bottom:10px">
					<input class="easyui-datebox" label="采购日期：" name="orders_date"  prompt="请输入日期" data-options="required:true,editable:false,panelWidth:220,panelHeight:240,iconWidth:30" style="width:100%">
				</div>
				<div style="margin-bottom:10px">
					<select id="supply_co_form" label="供货方：" name ="supply_co" class="easyui-combobox"   data-options="valueField:'id',textField:'text',required:true"  prompt="选择供货方"  style="width:100%"></select>
				</div>
				<div style="margin-bottom:10px">
					<select id="purchase_product_form"  label="产品名称："  name ="purchase_product" class="easyui-combobox"   data-options="valueField:'id',textField:'text',required:true"  prompt="选择产品类型"  style="width:100%"></select>
				</div>
				<div style="margin-bottom:10px">
					<input id="purchase_amount_form" class="easyui-numberbox"  label="采购数量：" name="purchase_amount" data-options="required:true"  prompt="请输入数量" style="width:100%">
				</div>
				<div style="margin-bottom:10px">
					<input id="purchase_singleprice_form" class="easyui-numberbox" label="采购单价：" name="purchase_singleprice" prompt="请输入单价" prefix="￥"   data-options="min:0,precision:2,required:true"  style="width:100%">
				</div>
				<div style="margin-bottom:10px">
					<input id="purchase_totalprice_form" class="easyui-numberbox" label="采购总价："  name="purchase_totalprice"  value="0"prefix="￥"   data-options="min:0,precision:2,readonly:true"  style="width:100%">
				</div>
				<div style="margin-bottom:10px">
					<select name ="payment_method" class="easyui-combobox"   data-options="valueField:'id',textField:'text',required:true"  prompt="选择付款方式" label="付款方式：" style="width:100%">
						<option value="现金">现金</option>
						<option value="承兑">承兑</option>
					</select>
				</div>
				<div style="margin:50px 0 0;text-align:center">
		            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:100px;height:30px" onclick="SubmitFormpurchase()">提交</a>
		        </div>
			</form>
		</div>
		<div id="submit_purchase_alert" class="easyui-window" title="提示" 
   		data-options="
   			modal:true,
   			closed:true,
   			collapsible:false,
   			minimizable:false,
   			maximizable:false
  		" style="width:90%;height:auto;padding:0px;">
  			<div style="text-align:center;padding:5px">
  				是否确认提交？
	    	</div>
	    	<div style="text-align:center;padding:5px">
  				<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="$('#purchase_order_form').submit();">确认</a>
  				<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="$('#submit_purchase_alert').window('close');">关闭</a>
	    	</div>
	    </div>
		    
		<!-- 关联采购单 -->
		<div id="associate_purchase_to_sale"class="easyui-navpanel" style="position:relative;">
			<header>
				<div class="m-toolbar">
					<div id="associate_purchase_to_sale_title" class="m-title"></div>
					 <div class="m-left">
                    	<a href="javascript:void(0)" class="easyui-linkbutton m-back" plain="true" outline="true" style="width:50px" onclick="$.mobile.go('#main_page','slide','right')">返回</a>
                	</div>
				</div>
			</header>
			<table id="associate_sale_order_table" class="easyui-datagrid" data-options="singleSelect:true,border:false,fitColumns:true"  style="width:100%;height:80%;"  rownumbers="true" pagination="true">
				<thead>
					<tr>
						<th field="sale_contract_number" >销售合同号</th>
						<th field="receive_co" >收货方</th>
						<th field="sale_amount_left" >剩余销售数量</th>
						<th field="sale_amount_used">已销售数量</th>
						<th field="sale_amount" >总销售数量</th>
						<th field="product_name" >销售品名</th>
					</tr>
				</thead>
			</table>
			<div style="margin:50px 0 0;text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:100px;height:30px" onclick="AssociatePurchaseOrder()">关联</a>
	        </div>
		</div>
		<div id="associate_sale_order_window" class="easyui-window" 
   		data-options="
   			modal:true,
   			closed:true,
   			collapsible:false,
   			minimizable:false,
   			maximizable:false
  		" style="width:90%;height:500px;padding:0px;">
	  		<header>
				<div class="m-toolbar">
					<div class="m-title">采购订单列表</div>
				</div>
			</header>
  			<table id="associate_purchase_order_table" class="easyui-datagrid" data-options="singleSelect:true,border:false,nowrap:true,fitColumns:true"  style="width:100%;height:80%;"  rownumbers="true" pagination="true">
				<thead>
					<tr>
						<th field="purchase_contract_number" >采购合同号</th>
						<th field="purchase_amount" >采购数量</th>
						<th field="supply_co" >供货商</th>
					</tr>
				</thead>
			</table>
	    	<div style="text-align:center;padding:5px">
  				<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="ClickAssociate()">确认</a>
  				<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="$('#associate_sale_order_window').window('close');">关闭</a>
	    	</div>
	    </div>
	    <div id="associate_alert" class="easyui-window" title="提示" 
   		data-options="
   			modal:true,
   			closed:true,
   			collapsible:false,
   			minimizable:false,
   			maximizable:false
  		" style="width:90%;height:auto;padding:0px;">
  			<div style="text-align:center;padding:5px">
  				是否确认关联？
	    	</div>
	    	<div style="text-align:center;padding:5px">
  				<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="ConfirmAssociate()">确认</a>
  				<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="$('#associate_alert').window('close');">关闭</a>
	    	</div>
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
