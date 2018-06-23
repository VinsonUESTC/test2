/**
 * 
 */
//查询分配函数
function QuestAllocate(){
	$.post(
		"ajax/quest_allocate_data",//请求的地址
		{
			"month":date_month,
			"randomnumber":Math.random()+""
		},//需要提交到请求地址的参数
		function( returnedString )     //回调
		{
			var data = returnedString;
			var jsonObj = eval("("+data+")");
			$('#quest_allocate_table').datagrid('loadData',jsonObj.quest_allocate_data);
		}
	);
}
//查询采购订单函数
function QuestPurchaseOrder(){
	if(date_month == null){
		alert('请选择月份！');
	}else{
		$.post(
			"ajax/quest_purchase_order_data",//请求的地址
			{
				"month":date_month,
				"randomnumber":Math.random()+""
			},//需要提交到请求地址的参数
			function( returnedString )     //回调
			{
				var data = returnedString;
				var jsonObj = eval("("+data+")");
				$('#quest_purchase_order_table').datagrid('loadData',jsonObj.quest_purchase_table_data);
			}
		);
		date_month = null;
	}
}

//查询损耗
function QuestLoss(){
	if(date_month == null){
		alert('请选择月份！');
	}else{
		$.post(
			"ajax/quest_loss_data",//请求的地址
			{
				"month":date_month,
				"randomnumber":Math.random()+""
			},//需要提交到请求地址的参数
			function( returnedString )     //回调
			{
				var data = returnedString;
				var jsonObj = eval("("+data+")");
				$('#quest_loss_table').datagrid('loadData',jsonObj.quest_loss_table_data);
			}
		);
	}
}


//查销售订单函数
function QuestsaleOrder(){
	if(date_month == null){
		alert('请选择月份！');
	}else{
		$.post(
			"ajax/quest_sale_order_data",//请求的地址
			{
				"month":date_month,
				"randomnumber":Math.random()+""
			},//需要提交到请求地址的参数
			function( returnedString )     //回调
			{
				var data = returnedString;
				var jsonObj = eval("("+data+")");
				$('#quest_sale_order_table').datagrid('loadData',jsonObj.quest_sale_table_data);
			}
		);
		date_month = null;
	}
}

//查询提货函数
function QuestTakeDelivery(){
	$.post(
		"ajax/quest_delivery_data",//请求的地址
		{
			"boat_number":$('#take_delivery_boat_number').textbox('getValue'),
			"randomnumber":Math.random()+""
		},//需要提交到请求地址的参数
		function( returnedString )     //回调
		{
			var data = returnedString;
			var jsonObj = eval("("+data+")");
			$('#take_delivery_table').datagrid('loadData',jsonObj.quest_delivery_data);
		}
	);
}

//查询卸货函数
function QuestDischarge(){
	$.post(
		"ajax/quest_discharge_data",//请求的地址
		{
			"boat_number":$('#discharge_boat_number').textbox('getValue'),
			"randomnumber":Math.random()+""
		},//需要提交到请求地址的参数
		function( returnedString )     //回调
		{
			var data = returnedString;
			var jsonObj = eval("("+data+")");
			$('#discharge_table').datagrid('loadData',jsonObj.quest_discharge_data);
		}
	);
}

//查询已发货函数
function QuestDeliveryAmount(){
	if($('#delivery_start_date').datebox('getValue')!=null&&$('#delivery_end_date').datebox('getValue')!=null&&$('#supply_co_delivery').combobox('getValue')!=null){
		$.post(
			"ajax/quest_delivery_amount_data",//请求的地址
			{
				"delivery_start_date":$('#delivery_start_date').datebox('getValue'),
				"delivery_end_date":$('#delivery_end_date').datebox('getValue'),
				"supply_co":$('#supply_co_delivery').combobox('getValue'),
				"randomnumber":Math.random()+""
			},//需要提交到请求地址的参数
			function( returnedString )     //回调
			{
				var data = returnedString;
				var jsonObj = eval("("+data+")");
				$('#quest_delivery_table').datagrid('loadData',jsonObj.quest_delivery_amount_data);
			}
		);
	}
}

//查询已卸货函数
function QuestDischargeAmount(){
	if($('#delivery_start_date').datebox('getValue')!=null&&$('#delivery_end_date').datebox('getValue')!=null&&$('#supply_co_delivery').combobox('getValue')!=null){
		$.post(
			"ajax/quest_discharge_amount_data",//请求的地址
			{
				"discharge_start_date":$('#discharge_start_date').datebox('getValue'),
				"discharge_end_date":$('#discharge_end_date').datebox('getValue'),
				"receive_co":$('#receive_co_discharge').combobox('getValue'),
				"randomnumber":Math.random()+""
			},//需要提交到请求地址的参数
			function( returnedString )     //回调
			{
				var data = returnedString;
				var jsonObj = eval("("+data+")");
				$('#quest_discharge_table').datagrid('loadData',jsonObj.quest_discharge_amount_data);
			}
		);
	}
}

//查询已付款函数
function QuestPayTable(){
	if($('#pay_start_date').datebox('getValue')!=null&&$('#pay_end_date').datebox('getValue')!=null&&$('#receive_co_pay').combobox('getValue')!=null){
		$.post(
			"ajax/quest_payment_table_data",//请求的地址
			{
				"pay_start_date":$('#pay_start_date').datebox('getValue'),
				"pay_end_date":$('#pay_end_date').datebox('getValue'),
				"receive_co_pay":$('#receive_co_pay').combobox('getValue'),
				"randomnumber":Math.random()+""
			},//需要提交到请求地址的参数
			function( returnedString )     //回调
			{
				var data = returnedString;
				var jsonObj = eval("("+data+")");
				$('#quest_pay_table').datagrid('loadData',jsonObj.quest_pament_table_data);
			}
		);
	}
}

//查询已收款函数
function QuestReceiveTable(){
	if($('#receive_start_date').datebox('getValue')!=null&&$('#receive_end_date').datebox('getValue')!=null&&$('#receive_co_receive').combobox('getValue')!=null){
		$.post(
			"ajax/quest_receivables_table_data",//请求的地址
			{
				"receive_start_date":$('#receive_start_date').datebox('getValue'),
				"receive_end_date":$('#receive_end_date').datebox('getValue'),
				"receive_co_receive":$('#receive_co_receive').combobox('getValue'),
				"randomnumber":Math.random()+""
			},//需要提交到请求地址的参数
			function( returnedString )     //回调
			{
				var data = returnedString;
				var jsonObj = eval("("+data+")");
				$('#quest_receive_table').datagrid('loadData',jsonObj.quest_receivalbes_table_data);
			}
		);
	}
}
