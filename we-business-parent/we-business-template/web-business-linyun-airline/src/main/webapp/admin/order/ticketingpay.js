var payTable;
//初始化表格
initpayTable();
function initpayTable() {
	payTable = $('#payTable').DataTable({
    	"searching":false,
    	"bLengthChange": false,
        "processing": true,
        "serverSide": true,
        "stripeClasses": [ 'strip1','strip2' ],
        "language": {
            "url": BASE_PATH + "/public/plugins/datatables/cn.json"
        },
        "ajax": {
            "url": BASE_PATH + "/admin/inland/listPayData.html",
            "type": "post",
            "data": function (d) {
            	
            }
        },
        "columns": [{"data": "id", "bSortable": false,
			        	"render": function (data, type, row, meta) {
			        		var result = '';
			        		var hiddenval = $('#checkedboxval1').val();
			        		var splits = hiddenval.split(',');
			        		var flag = false;
			        		for(var i=0;i<splits.length;i++){
			        			if(splits[i] == row.id){
			        				flag = true;
			        			}
			        		}	
			        		if(flag){
			        			result = '<input type="checkbox"  class="checkchild1" checked="true" value="' + row.id + '" />';
			        		}else{
			        			result = '<input type="checkbox"  class="checkchild1" value="' + row.id + '" />';
			        		}
			                return result;
			            }
			        },
                    {"data": "ordersnum", "bSortable": false},
                    {"data": "pnr", "bSortable": false},
                    {"data": "leavetdate", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '';
                    		if(row.leavetdate && row.leavetdate != undefined){
                    			result = row.leavetdate;
                    		}
                    		return result;
                    	}
                    },
                    {"data": "airnum", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '';
                    		if(row.ailinenum && row.ailinenum != undefined){
                    			result = row.ailinenum;
                    		}
                    		return result;
                    	}
                    },
                    {"data": "airsag", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '';
            				if(row.leavecity != undefined){
            					result += row.leavecity;
            				}
            				result += '/';
            				if(row.arrivecity != undefined){
            					result += row.arrivecity;
            				}
                    		return result;
                    	}
                    },
                    {"data": "airtime", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		//var result = row.leavetime + '/' + row.arrivetime;
            				var result = '';
            				if(row.leavetime != undefined){
            					result += row.leavetime;
            				}
            				result += '/';
            				if(row.arrivetime != undefined){
            					result += row.arrivetime;
            				}
                    		return result;
                    	}
                    },
                    {"data": "costprice", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '';
                    		if(row.costprice && row.costprice != undefined){
                    			result = row.costprice;
                    		}
                    		return result; 
                    	}
                    },
                    {"data": "costpricesum", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '';
                    		if(row.costpricesum && row.costpricesum != undefined){
                    			result = row.costpricesum;
                    		}
                    		return result; 
                    	}
                    },
                    {"data": "peoplecount", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '';
                    		if(row.peoplecount){
                    			result = row.peoplecount;
                    		}
                    		return result; 
                    	}
                    },
                    {"data": "ordersstatus", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '已出票';
                    		if(row.orderpnrstatus === 4){
                    			result = '已拒绝';
                    		}
                    		return result; 
                    	}
                    },
                    {"data": "linkman", "bSortable": false},
                    {"data": "telephone", "bSortable": false}
            ],
        columnDefs: [{
            //   指定第一列，从0开始，0表示第一列，1表示第二列……
        }],
        "infoCallback": function (settings, start, end, max, total, pre) {
    		var length = $(".checkchild1:checked").length;
    		if(payTable.page.len() == length){
    			$(".checkall1").prop("checked", true);
    		}else{
    			$(".checkall1").prop("checked", false);
    			
    		}
    		return '显示第 '+start+' 至 '+end+' 条结果，共'+total+' 条 (每页显示 '+max+' 条)'
    	}
    });
}
//控制复选框
$(".checkall1").click(function () {
    var check = $(this).prop("checked");
    $(".checkchild1").prop("checked", check);
    //隐藏域的值
    var hiddenval = $('#checkedboxval1').val();
	if(check){
		var splits = hiddenval.split(',');
		$(".checkchild1:checked").each(function(){
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
		$(".checkchild1").each(function(){
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
	$('#checkedboxval1').val(hiddenval);
});
//点击之后给隐藏域赋值
$(document).on('click', '.checkchild1', function(e) {
	var hiddenval = $('#checkedboxval1').val();
	var thisval = $(this).val();
	var check = $(this).prop("checked");
	if(check){
		if(!hiddenval){
			$('#checkedboxval1').val(thisval);
		}else{
			$('#checkedboxval1').val(hiddenval+','+thisval);
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
			$('#checkedboxval1').val(ids);
		}else{
			$('#checkedboxval1').val(hiddenval);
		}
	}
});
function loadFukuanTable(){
	payTable.ajax.reload();
}
$('.fuKuanBtn1').click(function(){
	var ids = $('#checkedboxval1').val();
	if(!ids){
		layer.msg("请至少选中一条记录",{time: 2000});
	}else{
		$.ajax({ 
			type: 'POST', 
			data: {ids:ids}, 
			url: BASE_PATH + '/admin/inland/checkPayIsCommonCompany.html',
           success: function (data) { 
        	   if(data){
        		   layer.open({
        				type: 2,
        				title:false,
        				skin: false, //加上边框
        				closeBtn:false,//默认 右上角关闭按钮 是否显示
        				shadeClose:true,
        				scrollbar: false,
        				area: ['850px', '320px'],
        				content:  BASE_PATH + '/admin/inland/seaPayApply.html?ids='+ids,
        				end:function(){
        					payTable.ajax.reload(null,false);
        					$('#checkedboxval1').val('');
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
$('.ticketpaysearch').click(function(){
	var div = $(this).parent().parent();
	var startdate = div.find('[name=startdate]').val();
	var enddate = div.find('[name=enddate]').val();
	var searchInfo = div.find('[name=searchInfo]').val();
	var param = {
			startdate:startdate,
			enddate:enddate,
			searchInfo:searchInfo
	};
	payTable.settings()[0].ajax.data = param;
	payTable.ajax.reload();
});