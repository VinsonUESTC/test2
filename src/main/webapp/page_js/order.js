/**
 * 
 */

$(function(){
	//提交销售订单表单动作
	$("#sale_order_form").submit(function (event) {
	  event.preventDefault();
	  var form = $(this);
	  console.log(form.serialize().search(/=&/));
	  if(form.serialize().search(/=&/)>0){
	  	alert('请填写完整！');
	  }else{
	    $.ajax({
	      type: form.attr('method'),
	      url: form.attr('action'),
	      data: form.serialize()+"&creater="+document.getElementById("usertitle").innerHTML.substring(3)+"&createtime="+new Date().Format("yyyy-MM-dd hh:mm:ss")
	    }).success(function () {
	    	alert('提交成功！');
			$('#submit_sale_alert').window('close');
			ResetSaleOrder();
	      //成功提交
	    }).fail(function (jqXHR, textStatus, errorThrown) {
	      //错误信息
	      alert('提交错误'+textStatus);
	    });}
	}); 
	//提交采购订单表单动作
	$("#purchase_order_form").submit(function (event) {
	  event.preventDefault();
	  var form = $(this);
	  if(form.serialize().search(/=&/)>0){
	  	alert('请填写完整！');
	  }else{
	    $.ajax({
	      type: form.attr('method'),
	      url: form.attr('action'),
	      data: form.serialize()+"&creater="+document.getElementById("usertitle").innerHTML.substring(3)+"&createtime="+new Date().Format("yyyy-MM-dd hh:mm:ss")
	    }).success(function () {
	    	alert('提交成功！');
			$('#submit_purchase_alert').window('close');
			ResetPurchaseOrder();
	      //成功提交
	    }).fail(function (jqXHR, textStatus, errorThrown) {
	      //错误信息
	      alert('提交错误'+textStatus);
	    });}
	}); 

	//选择月份函数-查询采购
		$('.date-month').datebox({    
	        onShowPanel : function() {// 显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层    
	            span.trigger('click'); // 触发click事件弹出月份层    
	            if (!tds)    
	                setTimeout(function() {// 延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔    
	                    tds = p.find('div.calendar-menu-month-inner td');    
	                    tds.click(function(e) {    
	                        e.stopPropagation(); // 禁止冒泡执行easyui给月份绑定的事件    
	                        var month2 = 0;
	                        var year = /\d{4}/.exec(span.html())[0]// 得到年份    
	                        , month = parseInt($(this).attr('abbr'), 10) + 1; // 月份    
	                        $('.date-month').datebox('hidePanel')// 隐藏日期对象    
	                        .datebox('setValue', year + '-' + month); // 设置日期的值   
	                        month2 = month-1;
	                        date_month = year+'-'+month2+'-1'; 
	                        console.log(date_month);
	                    });    
	                }, 0);    
	        },    
	        parser : function(s) {// 配置parser，返回选择的日期    
	            if (!s)    
	                return new Date();    
	            var arr = s.split('-');    
	            return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);    
	        },    
	        formatter : function(d) {    
	            var month = d.getMonth();  
	            if(month<10){  
	                month = "0"+month;  
	            }  
	            if (d.getMonth() == 0) {    
	                return d.getFullYear()-1 + '-' + 12;    
	            } else {    
	                return d.getFullYear() + '-' + month;    
	            }    
	        }// 配置formatter，只返回年月    
	    });    
	    var p = $('.date-month').datebox('panel'), // 日期选择对象    
	    tds = false, // 日期选择对象中月份    
	    span = p.find('span.calendar-text'); // 显示月份层的触发控件   
	    
	    //选择月份函数-查询销售
		$('.date-month2').datebox({    
	        onShowPanel : function() {// 显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层    
	            span2.trigger('click'); // 触发click事件弹出月份层    
	            if (!tds2)    
	                setTimeout(function() {// 延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔    
	                    tds2 = p2.find('div.calendar-menu-month-inner td');    
	                    tds2.click(function(e) {    
	                        e.stopPropagation(); // 禁止冒泡执行easyui给月份绑定的事件    
	                        var month2 = 0;
	                        var year = /\d{4}/.exec(span2.html())[0]// 得到年份    
	                        , month = parseInt($(this).attr('abbr'), 10) + 1; // 月份    
	                        $('.date-month2').datebox('hidePanel')// 隐藏日期对象    
	                        .datebox('setValue', year + '-' + month); // 设置日期的值   
	                        month2 = month-1;
	                        date_month = year+'-'+month2+'-1'; 
	                        console.log(date_month);
	                    });    
	                }, 0);    
	        },    
	        parser : function(s) {// 配置parser，返回选择的日期    
	            if (!s)    
	                return new Date();    
	            var arr = s.split('-');    
	            return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);    
	        },    
	        formatter : function(d) {    
	            var month = d.getMonth();  
	            if(month<10){  
	                month = "0"+month;  
	            }  
	            if (d.getMonth() == 0) {    
	                return d.getFullYear()-1 + '-' + 12;    
	            } else {    
	                return d.getFullYear() + '-' + month;    
	            }    
	        }// 配置formatter，只返回年月    
	    });    
	    var p2 = $('.date-month2').datebox('panel'), // 日期选择对象    
	    tds2 = false, // 日期选择对象中月份    
	    span2 = p2.find('span.calendar-text'); // 显示月份层的触发控件   
	
})

//重置采购订单
function ResetPurchaseOrder(){
	$('#purchase_order_form').form('reset');
	InitForm();
}
//重置销售订单
function ResetSaleOrder(){
	$('#sale_order_form').form('reset');
	InitForm();
}
//提交采购订单
function SubmitFormpurchase(){
	$('#submit_purchase_alert').window('center');
	$('#submit_purchase_alert').window('open');
}

//提交销售订单
function SubmitFormsale(){
	$('#submit_sale_alert').window('open');
	$('#submit_sale_alert').window('center');
}
