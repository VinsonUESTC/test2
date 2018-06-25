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
			<li value="take_delivery">提货</li>
			<li value="discharge">卸货</li>
			<li value="take_delivery_history">提货记录</li>
			<li value="discharge_history">卸货记录</li>
		</ul>
	</div>

	<!-- 提货 -->
	<div id="take_delivery"class="easyui-navpanel" style="position:relative;">
		<header>
			<div class="m-toolbar">
				<div id="take_delivery_title" class="m-title"></div>
				<div class="m-left">
					<a href="javascript:void(0)" class="easyui-linkbutton m-back" plain="true" outline="true" style="width:50px" onclick="$.mobile.go('#main_page','slide','right')">返回</a>
				</div>
			</div>
		</header>
		<table id="take_delivery_table" class="easyui-datagrid" data-options="singleSelect:true,border:false,fitColumns:true"  style="width:100%;height:70%;"  rownumbers="true" pagination="true">
			<thead>
			<tr>
				<th field="allocate_order_number" >分配单号</th>
				<th field="supply_co" >供货方</th>
				<th field="receive_co" >收货方</th>
				<th field="allocate_amount" >分配数量</th>
				<th field="create_time">分配时间</th>
			</tr>
			</thead>
		</table>
		<div style="margin:50px 0 0;text-align:center">
			<a href="javascript:void(0)" class="easyui-linkbutton" style="width:100px;height:30px" onclick="TakeDeliveryConfirm()">提货</a>
		</div>
		<div id="take_delivery_window" class="easyui-window"
			 data-options="
	   			modal:true,
	   			closed:true,
	   			collapsible:false,
	   			minimizable:false,
	   			maximizable:false
	  		" style="width:90%;height:auto;padding:0px;">
			<header>
				<div class="m-toolbar">
					<div class="m-title">提货登记表</div>
				</div>
			</header>
			<div style="margin:10px">
				<input class="easyui-textbox" label="船号：" id="take_delivery_boat_number"  prompt="请输入船号" data-options="readonly:true" style="width:100%">
			</div>
			<div style="margin:10px">
				<input class="easyui-textbox" label="出库单号："  prompt="请输入出库单号"  id="take_delivery_order_number" data-options="required:true" style="width:100%">
			</div>
			<div style="margin:10px">
				<input class="easyui-datebox" label="提货日期：" id="take_delivery_date"  data-options="readonly:true" style="width:100%">
			</div>
			<div style="margin:10px">
				<input id="take_delivery_amount" class="easyui-numberbox" label="提货数量：" prompt="请输入数量" data-options="min:0,precision:3,required:true"  style="width:100%">
			</div>
			<div style="text-align:center;padding:5px">
				<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="ClickDelivery()">确认</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="$('#take_delivery_window').window('close');">关闭</a>
			</div>
		</div>
		<div id="submit_delivery_alert" class="easyui-window" title="提示"
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
				<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="SubmitDelivery()">确认</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="$('#submit_delivery_alert').window('close');">关闭</a>
			</div>
		</div>
	</div>

	<!-- 卸货 -->
	<div id="discharge"class="easyui-navpanel" style="position:relative;">
		<header>
			<div class="m-toolbar">
				<div id="discharge_title" class="m-title"></div>
				<div class="m-left">
					<a href="javascript:void(0)" class="easyui-linkbutton m-back" plain="true" outline="true" style="width:50px" onclick="$.mobile.go('#main_page','slide','right')">返回</a>
				</div>
			</div>
		</header>
		<table id="discharge_table" class="easyui-datagrid" data-options="singleSelect:true,border:false,fitColumns:true"  style="width:100%;height:70%;"  rownumbers="true" pagination="true">
			<thead>
			<tr>
				<th field="take_delivery_order_number">出库单号</th>
				<th field="supply_co" >供货方</th>
				<th field="receive_co" >收货方</th>
				<th field="delivery_amount" >提货数量</th>
				<th field="take_delivery_date" >提货时间</th>
			</tr>
			</thead>
		</table>
		<div style="margin:50px 0 0;text-align:center">
			<a href="javascript:void(0)" class="easyui-linkbutton" style="width:100px;height:30px" onclick="DischargeConfirm()">卸货</a>
		</div>
		<div id="discharge_window" class="easyui-window"
			 data-options="
	   			modal:true,
	   			closed:true,
	   			collapsible:false,
	   			minimizable:false,
	   			maximizable:false
	  		" style="width:90%;height:auto;padding:0px;">
			<header>
				<div class="m-toolbar">
					<div class="m-title">卸货登记表</div>
				</div>
			</header>
			<div style="margin:10px">
				<input class="easyui-textbox" label="船号：" id="discharge_boat_number"  prompt="请输入船号" data-options="readonly:true" style="width:100%">
			</div>
			<div style="margin:10px">
				<input class="easyui-textbox" label="入库单号："  prompt="请输入入库单号"  id="discharge_order_number" data-options="required:true" style="width:100%">
			</div>
			<div style="margin:10px">
				<input class="easyui-datebox" label="卸货日期：" id="discharge_date"  prompt="请选择卸货日期" data-options="readonly:true" style="width:100%">
			</div>
			<div style="margin:10px">
				<input id="discharge_amount" class="easyui-numberbox" label="卸货数量：" prompt="请输入数量" data-options="min:0,precision:3,required:true"  style="width:100%">
			</div>
			<div style="text-align:center;padding:5px">
				<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="ClickDischarge()">确认</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="$('#discharge_window').window('close');">关闭</a>
			</div>
		</div>
		<div id="submit_discharge_alert" class="easyui-window" title="提示"
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
				<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="SubmitDischarge()">确认</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="$('#submit_discharge_alert').window('close');">关闭</a>
			</div>
		</div>
	</div>

	<!-- 提货记录查询 -->
	<div id="take_delivery_history"class="easyui-navpanel" style="position:relative;">
		<header>
			<div class="m-toolbar">
				<div id="take_delivery_history_title" class="m-title"></div>
				<div class="m-left">
					<a href="javascript:void(0)" class="easyui-linkbutton m-back" plain="true" outline="true" style="width:50px" onclick="$.mobile.go('#main_page','slide','right')">返回</a>
				</div>
			</div>
		</header>
		<table id="quest_take_delivery_history_table" class="easyui-datagrid" data-options="singleSelect:true,border:false,fitColumns:true"  style="width:100%;height:80%;"  rownumbers="true" pagination="true">
			<thead>
			<tr>
				<th field="take_delivery_order_number">出库单号</th>
				<th field="supply_co" >提货单位</th>
				<th field="delivery_amount">提货数量</th>
				<th field="take_delivery_date" >提货日期</th>
			</tr>
			</thead>
		</table>
	</div>

	<!-- 卸货记录查询 -->
	<div id="discharge_history"class="easyui-navpanel" style="position:relative;">
		<header>
			<div class="m-toolbar">
				<div id="discharge_history_title" class="m-title"></div>
				<div class="m-left">
					<a href="javascript:void(0)" class="easyui-linkbutton m-back" plain="true" outline="true" style="width:50px" onclick="$.mobile.go('#main_page','slide','right')">返回</a>
				</div>
			</div>
		</header>
		<table id="quest_discharge_history_table" class="easyui-datagrid" data-options="singleSelect:true,border:false,fitColumns:true"  style="width:100%;height:80%;"  rownumbers="true" pagination="true">
			<thead>
			<tr>
				<th field="discharge_order_number">入库单号</th>
				<th field="receive_co" >卸货单位</th>
				<th field="discharge_amount">卸货数量</th>
				<th field="discharge_date" >卸货日期</th>
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
