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
		<script type="text/javascript" src="page_js/loss.js"></script>
	<body data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-515" data-genuitec-path="/test2/WebRoot/transport_manager.jsp">
		<div id="main_page" class="easyui-navpanel" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-515" data-genuitec-path="/test2/WebRoot/transport_manager.jsp">
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
	            <li value="allocate">分配船只</li>
	            <li value="quest_allocate">查询已分配船只</li>
	            <li value="loss">损耗查询</li>
	        </ul>
		</div>
	
		
		<!-- 分配船只 -->
		<div id="allocate"class="easyui-navpanel" style="position:relative;">
			<header>
				<div class="m-toolbar">
					<div id="allocate_title" class="m-title"></div>
					 <div class="m-left">
                    	<a href="javascript:void(0)" class="easyui-linkbutton m-back" plain="true" outline="true" style="width:50px" onclick="$.mobile.go('#main_page','slide','right')">返回</a>
                	</div>
				</div>
			</header>
			<table id="allocate_boat_table" class="easyui-datagrid" data-options="singleSelect:true,border:false,fitColumns:true"  style="width:100%;height:80%;"  rownumbers="true" pagination="true">
				<thead>
					<tr>
						<th field="purchase_contract_number" >采购合同号</th>
						<th field="amount_left" >可分配数量</th>
						<th field="supply_co" >提货方</th>
						<th field="receive_co" >收货方</th>
						<th field="product_name" >品名</th>
					</tr>
				</thead>
			</table>
			<div style="margin:50px 0 0;text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:100px;height:30px" onclick="AllocateConfirm()">分配</a>
	        </div>
	        <div id="allocate_window" class="easyui-window" 
	   		data-options="
	   			modal:true,
	   			closed:true,
	   			collapsible:false,
	   			minimizable:false,
	   			maximizable:false
	  		" style="width:90%;height:500px;padding:0px;">
		  		<header>
					<div class="m-toolbar">
						<div class="m-title">分配表</div>
					</div>
				</header>
				<div style="margin:10px">
					<input class="easyui-textbox" label="分配单号：" id="allocate_order_number" data-options="readonly:true" style="width:100%">
				</div>
	  			<div style="margin:10px">
					<input class="easyui-textbox" label="船号：" id="boat_number"  prompt="请输入船号" data-options="required:true" style="width:100%">
				</div>
				<div style="margin:10px">
					<input id="allocate_amount_form" class="easyui-numberbox" label="分配数量：" prompt="请输入数量" data-options="min:0,precision:0,required:true"  style="width:100%">
				</div>
		    	<div style="text-align:center;padding:5px">
	  				<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="ClickAllocate()">确认</a>
	  				<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="$('#allocate_window').window('close');">关闭</a>
		    	</div>
		    </div>
		<div id="submit_allocate_alert" class="easyui-window" title="提示" 
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
	  				<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="SubmitAllocate()">确认</a>
	  				<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="$('#submit_allocate_alert').window('close');">关闭</a>
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
