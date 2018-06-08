/**
 * 
 */
$(function(){
	//选择月份函数-查询损耗
		$('.date-month4').datebox({    
	        onShowPanel : function() {// 显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层    
	            span4.trigger('click'); // 触发click事件弹出月份层    
	            if (!tds4)    
	                setTimeout(function() {// 延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔    
	                    tds4 = p4.find('div.calendar-menu-month-inner td');    
	                    tds4.click(function(e) {    
	                        e.stopPropagation(); // 禁止冒泡执行easyui给月份绑定的事件    
	                        var month2 = 0;
	                        var year = /\d{4}/.exec(span4.html())[0]// 得到年份    
	                        , month = parseInt($(this).attr('abbr'), 10) + 1; // 月份    
	                        $('.date-month4').datebox('hidePanel')// 隐藏日期对象    
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
	    var p4 = $('.date-month4').datebox('panel'), // 日期选择对象    
	    tds4 = false, // 日期选择对象中月份    
	    span4 = p4.find('span.calendar-text'); // 显示月份层的触发控件 
})
