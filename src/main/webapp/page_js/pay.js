/**
 * 
 */
 
//点击付款
function PayConfirm(type){
	if($('#payment_'+type+'_table').datagrid('getSelected')==null){
		alert('请选择一行数据！');
	}else{
		$('#pay_window_'+type).window('center');
		$('#pay_window_'+type).window('open');
	}
}
//确认提交付款数据
function ClickPay(type){
	if($('#pay_date_'+type).datebox('getValue')!=""){
		if($('#pay_amount_'+type).numberbox('getValue')!=""){
			if($('#pay_amount_'+type).numberbox('getValue')<0||$('#pay_amount_'+type).numberbox('getValue')-$('#payment_'+type+'_table').datagrid('getSelected').need_to_pay>0){
				alert('金额大于待付金额！');
				$('#submit_pay_alert_'+type).window('close');
				$('#pay_amount_'+type).numberbox('clear');
			}else{
				$('#submit_pay_alert_'+type).window('center');
				$('#submit_pay_alert_'+type).window('open');
				}
		}else{
			alert("请填写完整！");
			$('#submit_pay_alert_'+type).window('close');
		}
	}else{
		alert("请填写完整！");
		$('#submit_pay_alert_'+type).window('close');
	}
}

//插入付款数据
function SubmitPay(type,name){
	$.post(
		"ajax/insert_pay_data",//请求的地址
		{
			"supply_co":$('#payment_'+name+'_table').datagrid('getSelected').supply_co,
			"payment_time":$('#pay_date_'+name).datebox('getValue'),
			"payment_method":type,
			"payment_amount":$('#pay_amount_'+name).numberbox('getValue'),
			"creater":document.getElementById("usertitle").innerHTML.substring(3),
			"createtime":new Date().Format("yyyy-MM-dd hh:mm:ss") ,
			"randomnumber":Math.random()+""
		},//需要提交到请求地址的参数
		function( returnedString )     //回调
		{
			var data = returnedString;
			var jsonObj = eval("("+data+")");
			if(jsonObj.i=0){
				alert('提交错误！');
			}else{
				alert('提交成功！');
			};
			$('#submit_pay_alert_'+name).window('close');
			$('#pay_date_'+name).datebox('clear');
			$('#pay_amount_'+name).numberbox('clear');
			$('#pay_window_'+name).window('close');
			ReadPayTable();
		}
	);
}

//读取带付款数据
function ReadPayTable(){
	$.post(
		"ajax/pay_table_data",//请求的地址
		{
			"randomnumber":Math.random()+""
		},//需要提交到请求地址的参数
		function( returnedString )     //回调
		{
			var data = returnedString;
			var jsonObj = eval("("+data+")");
			$('#payment_cash_table').datagrid('loadData',jsonObj.pay_table_data_cash);
			$('#payment_bill_table').datagrid('loadData',jsonObj.pay_table_data_bill);
		}
	);
}