var drawerPayTable;
//初始化表格
initdrawerPayTable();
function initdrawerPayTable() {
	drawerPayTable = $('#drawerPayTable').DataTable({
    	"searching":false,
    	"bLengthChange": false,
        "processing": true,
        "serverSide": true,
        "initComplete": function( settings, json ) {
        	autoHighLoad($(this));
        },
        "infoCallback": function( settings, start, end, max, total, pre ) {
          	autoHighLoad($(this));
  			return '显示第 '+start+' 至 '+end+' 条结果，共 '+total+' 条 (每页显示 '+max+' 条)';
        },
        "stripeClasses": [ 'strip1','strip2' ],
        "language": {
            "url": BASE_PATH + "/public/plugins/datatables/cn.json"
        },
        "ajax": {
            "url": BASE_PATH + "/admin/international/internationalReceiveListData.html",
            "type": "post",
            "data": function (d) {
            	
            }
        },
        "fnDrawCallback" : function(){
        	var api = this.api();
        	var startIndex= api.context[0]._iDisplayStart;
   	       　　  api.column(1).nodes().each(function(cell, i) {
   	       　　　　cell.innerHTML = startIndex + i + 1; 
   	       　　});
      	},
        "columns": [{"data": "id", "bSortable": false,
			        	"render": function (data, type, row, meta) {
			        		var result = '';
			        		var hiddenval = $('#checkedboxval2').val();
			        		var splits = hiddenval.split(',');
			        		var flag = false;
			        		for(var i=0;i<splits.length;i++){
			        			if(splits[i] == row.ordernumber){
			        				flag = true;
			        			}
			        		}	
			        		if(flag){
			        			result = '<input type="checkbox"  class="checkchild2" checked="true" value="' + row.ordernumber + '" />';
			        		}else{
			        			result = '<input type="checkbox"  class="checkchild2" value="' + row.ordernumber + '" />';
			        		}
			                return result;
			            }
			        },
			        {"data": "xuhao", "bSortable": false},
                    {"data": "ordersnum", "bSortable": false},
                    {"data": "leavesdate", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var result = '<ul>';
                    		var MM = ['JAN', 'FEB', 'MAR', 'APR', 'MAY', 'JUN', 'JUL', 'AUG', 'SEPT', 'OCT', 'NOV', 'DEC'];
                    		var week = ['MO','TU','WE','TH','FR','SA','SU'];
                    		$.each(row.airinfo, function(name, value) {
                    			var leavedate = new Date(value.leavedate);
                    			result += '<li style="list-style:none;">'+(week[leavedate.getUTCDay()]+leavedate.getDate() + MM[leavedate.getMonth()])+'</li>';
                    		});
                    		result += '</ul>';
                    		return result;
                    	}
                    },
                    {"data": "leavescity", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var result = '<ul>';
                    		$.each(row.airinfo, function(name, value) {
                    			result += '<li style="list-style:none;">'+value.ailinenum+'</li>';
                    		});
                    		result += '</ul>';
                    		return result;
                    	}
                    },
                    {"data": "backsdate", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var result = '<ul>';
                    		$.each(row.airinfo, function(name, value) {
                    			result += '<li style="list-style:none;">'+(value.leavecity+'/'+value.arrvicity)+'</li>';
                    		});
                    		result += '</ul>';
                    		return result;
                    	}
                    },
                    {"data": "backscity", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var result = '<ul>';
                    		$.each(row.airinfo, function(name, value) {
                    			result += '<li style="list-style:none;">';
                    			if(value.leavetime && value.leavetime != undefined){
                    				result += value.leavetime;
                    			}
                    			result += '/';
                    			if(value.arrivetime && value.arrivetime != undefined){
                    				result += value.arrivetime;
                    			}
                    			result += '</li>';
                    		});
                    		result += '</ul>';
                    		return result;
                    	}	
                    },
                    {"data": "peoplecount", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var result='';
                    		if(row.peoplecount && row.peoplecount != undefined){
                    			result = row.peoplecount;
                    		}
                    		return result;
                    	}
                    },
                    {"data": "foc", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var result = '否';
                    		if(row.foc == 1){
                    			result = '16/1';
                    		}
                    		return result;
                    	}
                    },
                    {"data": "dayscount", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var result='';
                    		if(row.dayscount && row.dayscount != undefined){
                    			result = row.dayscount;
                    		}
                    		return result;
                    	}
                    },
                    {"data": "ordersstatus", "bSortable": false,
                    	render:function(data, type, row, meta) {
                      		var result = '';
                      		/*$.each(row.orderstatusenum, function(name, value) {
                      			if(row.ordersstatus == name){
                      				result = value;
                      			}
                      		});*/
                      		if(row.statusname && row.statusname != undefined){
                    			result = row.statusname;
                    		}
                      		return result;
                      	  }
                    },
                    {"data": "travelname", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var result='';
                    		if(row.travelname){
                    			result = row.travelname;
                    		}
                    		return result;
                    	}
                    },
                    {"data": "unioncity", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var result='';
                    		if(row.unioncity){
                    			result = row.unioncity;
                    		}
                    		return result;
                    	}
                    }
            ],
        columnDefs: [{
            //   指定第一列，从0开始，0表示第一列，1表示第二列……
        	targets: 1,
            render: function(data, type, row, meta) {
                return null
            }
        }],
        "infoCallback": function (settings, start, end, max, total, pre) {
    		var length = $(".checkchild2:checked").length;
    		if(drawerPayTable.page.len() == length){
    			$(".checkall2").prop("checked", true);
    		}else{
    			$(".checkall2").prop("checked", false);
    			
    		}
    		return '显示第 '+start+' 至 '+end+' 条结果，共'+total+' 条 (每页显示 '+max+' 条)'
    	}
    });
}
//点击行跳转到详情页
$("tbody",$('#drawerPayTable')).on("dblclick","tr",function(event) {
	var item = drawerPayTable.row($(this).closest('tr')).data();
	var url = BASE_PATH + '/admin/international/internationalDetail.html?orderid='+item.ordernumber;
	window.open(url);
});
//控制复选框
$(".checkall2").click(function () {
    var check = $(this).prop("checked");
    $(".checkchild2").prop("checked", check);
    //隐藏域的值
    var hiddenval = $('#checkedboxval2').val();
	if(check){
		var splits = hiddenval.split(',');
		$(".checkchild2:checked").each(function(){
			var thisvals = $(this).val();
			var flag = false;
			for(var i=0;i<splits.length;i++){
				if(splits[i] == thisvals){
					flag = true;
				}
			}
			//如果隐藏域值为空
			if(hiddenval){
				if(!flag){
					hiddenval += ',' + thisvals;
				}
			}else{
				hiddenval = thisvals;
			}
		});
	}else{
		$(".checkchild2").each(function(){
			var thisval = $(this).val();
			var flag = false;
			var splits = hiddenval.split(',');
			for(var i=0;i<splits.length;i++){
				if(splits[i] == thisval){
					flag = true;
				}
			}
			//如果隐藏域值为空
			if(flag){
				var ids = [];
				for(var i=0;i<splits.length;i++){
					if(splits[i] != thisval){
						ids.push(splits[i]);
					}
				}
				ids = ids.join(',');
				hiddenval = ids;
			}
		});
	}
	$('#checkedboxval2').val(hiddenval);
});
//点击之后给隐藏域赋值
$(document).on('click', '.checkchild2', function(e) {
	var hiddenval = $('#checkedboxval2').val();
	var thisval = $(this).val();
	var check = $(this).prop("checked");
	if(check){
		if(!hiddenval){
			$('#checkedboxval2').val(thisval);
		}else{
			$('#checkedboxval2').val(hiddenval+','+thisval);
		}
	}else{
		var splits = hiddenval.split(',');
		var flag = false;
		for(var i=0;i<splits.length;i++){
			if(splits[i] == thisval){
				flag = true;
			}
		}
		//如果存在则删掉当前值
		if(flag){
			var ids = [];
			for(var i=0;i<splits.length;i++){
				if(splits[i] != thisval){
					ids.push(splits[i]);
				}
			}
			ids = ids.join(',');
			$('#checkedboxval2').val(ids);
		}else{
			$('#checkedboxval2').val(hiddenval);
		}
	}
});
//点击出票加载出票表格
function loadTicking(status){
	if(!status){
		status = $('#status').val();
	}
	$('#checkedboxval2').val('');
	$(".checkall2").prop("checked", false);
	var param = {
			ordersstatus:status,
			ticketing:1
	};
	drawerPayTable.settings()[0].ajax.data = param;
	drawerPayTable.ajax.reload(function(json){
		autoHighLoad($('#drawerPayTable'));
	});
	$('#status').val(status);
	loadFukuanTable();
}
$('.fuKuanBtn').click(function(){
	var ids = $('#checkedboxval2').val();
	if(!ids){
		layer.msg("请至少选中一条记录",{time: 2000});
	}else{
		var status = $('#status').val();
		$.ajax({ 
			type: 'POST', 
			data: {ids:ids}, 
			url: BASE_PATH + '/admin/inland/checkIsCommonCompany.html',
           success: function (data) { 
        	   if(data){
        		   layer.open({
        				type: 2,
        				title:false,
        				skin: false, //加上边框
        				closeBtn:false,//默认 右上角关闭按钮 是否显示
        				shadeClose:true,
        				scrollbar: false,
        				area: ['850px', '568px'],
        				content: BASE_PATH + '/admin/international/openReceipt.html?ids='+ids+'&orderstatus='+status,
        				end:function(){
        					drawerPayTable.ajax.reload(function(json){
        						autoHighLoad($('#drawerPayTable'));
        					},false);
        					$('#checkedboxval2').val('');
        		  	    }
        			});
        	   }else{
        		   layer.msg("请选择同一个客户的订单","",3000);
        	   }
           },
           error: function (xhr) {
           	layer.msg("提交失败","",3000);
           } 
       });
		
	}
});

$('#ticketingSearch').click(function(){
	var div = $(this).parent().parent();
	var startdate = div.find('[name=startdate]').val();
	var enddate = div.find('[name=enddate]').val();
	var searchInfo = div.find('[name=searchInfo]').val();
	var status = $('#status').val();
	var param = {
			ordersstatus:status,
			startdate:startdate,
			enddate:enddate,
			searchInfo:searchInfo,
			ticketing:1
	};
	drawerPayTable.settings()[0].ajax.data = param;
	drawerPayTable.ajax.reload(function(json){
		autoHighLoad($('#drawerPayTable'));
	});
});
function onkeyTicketingEnter(){
	var e = window.event || arguments.callee.caller.arguments[0];
    if(e && e.keyCode == 13){
 		$('#ticketingSearch').click();
    }
}

