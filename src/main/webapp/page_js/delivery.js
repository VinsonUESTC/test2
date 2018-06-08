/**
 * 
 */
//点击卸货触发事件
function DischargeConfirm(){
	if($('#discharge_table').datagrid('getSelected')==null){
		alert('请选择一行数据！');
	}else{
		$('#discharge_window').window('center');
		$('#discharge_window').window('open');
		$('#boat_number_discharge').textbox('setValue',$('#discharge_boat_number').textbox('getValue'));
	}
}

//确认卸货触发事件
function ClickDischarge(){
	if($('#discharge_order_number').textbox('getValue')!=""){
		if($('#discharge_date').datebox('getValue')!=""){
			if($('#discharge_amount').numberbox('getValue')!=""){
				if($('#discharge_amount').numberbox('getValue')-$('#discharge_table').datagrid('getSelected').delivery_amount>0){
					alert('卸货数量大于分配数量！');
					$('#discharge_amount').numberbox('clear');
				}else{
					$('#submit_discharge_alert').window('center');
					$('#submit_discharge_alert').window('open');
				}
			}else{
				alert('请填写完整！');
			}
		}else{
			alert('请填写完整！');
		}
	}else{
		alert('请填写完整！');
	}
}

//写入卸货数据
function SubmitDischarge(){
	$.post(
		"ajax/insert_discharge_data",//请求的地址
		{
			"discharge_order_number":$('#discharge_order_number').textbox('getValue'),
			"take_delivery_order_number":$('#discharge_table').datagrid('getSelected').take_delivery_order_number,
			"boat_number":$('#discharge_boat_number').textbox('getValue'),
			"discharge_date":$('#discharge_date').datebox('getValue'),
			"discharge_amount":$('#discharge_amount').numberbox('getValue'),
			"creater":document.getElementById("usertitle").innerHTML.substring(3),
			"createtime":new Date().Format("yyyy-MM-dd hh:mm:ss") ,
			"randomnumber":Math.random()+""
		},//需要提交到请求地址的参数
			function( returnedString )     //回调
			{
				var data = returnedString;
				var jsonObj = eval("("+data+")");
				if(jsonObj.i>0){
					alert('提交成功！');
					QuestDischarge();
				}else{
					alert('提交失败！');
				}
			}
	);
	$('#discharge_order_number').textbox('clear');
	$('#discharge_date').datebox('clear');
	$('#discharge_amount').textbox('clear');
	$('#submit_discharge_alert').window('close');
	$('#discharge_window').window('close');
}


//点击提货触发事件
function TakeDeliveryConfirm(){
	if($('#take_delivery_table').datagrid('getSelected')==null){
		alert('请选择一行数据！');
	}else{
		$('#take_delivery_window').window('center');
		$('#take_delivery_window').window('open');
		$('#boat_number_take_delivery').textbox('setValue',$('#take_delivery_boat_number').textbox('getValue'));
	}
}

//确认提货触发事件
function ClickDelivery(){
	if($('#take_delivery_order_number').textbox('getValue')!=""){
		if($('#take_delivery_date').datebox('getValue')!=""){
			if($('#take_delivery_amount').numberbox('getValue')!=""){
				if($('#take_delivery_amount').numberbox('getValue')-$('#take_delivery_table').datagrid('getSelected').allocate_amount>0){
					alert('提货数量大于分配数量！');
					$('#take_delivery_amount').numberbox('clear');
				}else{
					$('#submit_delivery_alert').window('center');
					$('#submit_delivery_alert').window('open');
				}
			}else{
				alert('请填写完整！');
			}
		}else{
			alert('请填写完整！');
		}
	}else{
		alert('请填写完整！');
	}
}

//写入提货数据
function SubmitDelivery(){
	$.post(
		"ajax/insert_delivery_data",//请求的地址
		{
			"take_delivery_order_number":$('#take_delivery_order_number').textbox('getValue'),
			"allocate_order_number":$('#take_delivery_table').datagrid('getSelected').allocate_order_number,
			"boat_number":$('#take_delivery_boat_number').textbox('getValue'),
			"take_delivery_date":$('#take_delivery_date').datebox('getValue'),
			"delivery_amount":$('#take_delivery_amount').numberbox('getValue'),
			"creater":document.getElementById("usertitle").innerHTML.substring(3),
			"createtime":new Date().Format("yyyy-MM-dd hh:mm:ss") ,
			"randomnumber":Math.random()+""
		},//需要提交到请求地址的参数
			function( returnedString )     //回调
			{
				var data = returnedString;
				var jsonObj = eval("("+data+")");
				if(jsonObj.i>0){
					alert('提交成功！');
					QuestTakeDelivery();
				}else{
					alert('提交失败！');
				}
			}
	);
	$('#take_delivery_order_number').textbox('clear');
	$('#take_delivery_date').datebox('clear');
	$('#take_delivery_amount').textbox('clear');
	$('#submit_delivery_alert').window('close');
	$('#take_delivery_window').window('close');
}
