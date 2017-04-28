//计划制作表格初始化
var datatable2;
function initDatatable2() {
    datatable2 = $('#datatable2').DataTable({
    	"searching":false,
    	"bLengthChange": false,
        "processing": true,
        "serverSide": true,
        "stripeClasses": [ 'strip1','strip2' ],
        "language": {
            "url": BASE_PATH + "/public/plugins/datatables/cn.json"
        },
        "ajax": {
            "url": BASE_PATH + "/admin/customneeds/listEditPlanData.html",
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
        "columns": [
                	{"data": "id", "bSortable": false,
                    	"render": function (data, type, row, meta) {
                    		var result = '';
                    		var hiddenval = $('#checkedboxval').val();
                    		var splits = hiddenval.split(',');
                    		var flag = false;
                    		for(var i=0;i<splits.length;i++){
                    			if(splits[i] == row.id){
                    				flag = true;
                    			}
                    		}	
                    		if(flag){
                    			result = '<input type="checkbox"  class="checkchild" checked="true" value="' + row.id + '" />';
                    		}else{
                    			result = '<input type="checkbox"  class="checkchild" value="' + row.id + '" />';
                    		}
                            return result;
                        }
                    },
                    {"data": "xuhao", "bSortable": false},
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
                    {"data": "peoplecount", "bSortable": false},
                    {"data": "foc", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var result = '否';
                    		if(row.foc == 1){
                    			result = '16/1';
                    		}
                    		return result;
                    	}
                    },
                    {"data": "dayscount", "bSortable": false},
                    {"data": "travelname", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var result=' ';
                    		if(row.travelname){
                    			result = row.travelname;
                    		}
                    		return result;
                    	}
                    },
                    {"data": "unioncity", "bSortable": false}
            ],
        columnDefs: [{
    		targets: 0,
            render: function(data, type, row, meta) {
                return null
            }
    	},{
    		targets: 1,
            render: function(data, type, row, meta) {
                return null
            }
    	},{
            //   指定第一列，从0开始，0表示第一列，1表示第二列……
            targets: 11,
            render: function(data, type, row, meta) {
            	var s = '';
            	if(row.isclose == 0){
            		s += '<a style="cursor:pointer;" onclick="editplan('+row.id+');">编辑</a>';
            		s += '&nbsp;&nbsp;&nbsp;<a style="cursor:pointer;" onclick="closeEditPlan('+row.id+');">关闭</a>';
            	}else{
            		s += '&nbsp;&nbsp;&nbsp;<a style="cursor:pointer;" onclick="enableEditPlan('+row.id+');">启用</a>';
            	}
                return s
            }
    	}],
    	"infoCallback": function (settings, start, end, max, total, pre) {
    		var length = $(".checkchild:checked").length;
    		if(datatable2.page.len() == length){
    			$(".checkall").prop("checked", true);
    		}else{
    			$(".checkall").prop("checked", false);
    			
    		}
    		return '显示第 '+start+' 至 '+end+' 条结果，共'+total+' 条 (每页显示 '+max+' 条)'
    	}
    });
}
//控制复选框
$(".checkall").click(function () {
    var check = $(this).prop("checked");
    $(".checkchild").prop("checked", check);
    //隐藏域的值
    var hiddenval = $('#checkedboxval').val();
	if(check){
		var splits = hiddenval.split(',');
		$(".checkchild:checked").each(function(){
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
		$(".checkchild").each(function(){
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
	$('#checkedboxval').val(hiddenval);
});
//加载编辑计划列表数据
initDatatable2();
//获取检索条件数据
function getEditPlanParam(){
	var teamtype1 = $('#teamtype1').val();
	var idordernum = $('#idordernum').val();
	var travelname1 = $('#travelname1').val();
	var peoplecount1 = $('#peoplecount1').val();
	var dayscount1 = $('#dayscount1').val();
	var leaveairline1 = $('#leaveairline1').val();
	var backairline1 = $('#backairline1').val();
	var unioncity1 = $('#unioncity1').val();
	var startdate1 = $('#startdate1').val();
	var enddate1 = $('#enddate1').val();
	var leavescity1 = $('#leavescity1').val();
	var backscity1 = $('#backscity1').val();
	var param = {
			teamtype:teamtype1,
			idordernum:idordernum,
			travelname1:travelname1,
			peoplecount1:peoplecount1,
			dayscount1:dayscount1,
			leaveairline1:leaveairline1,
			backairline1:backairline1,
			unioncity1:unioncity1,
			startdate1:startdate1,
			enddate1:enddate1,
			leavescity1:leavescity1,
			backscity1:backscity1
	};
	return param;
}
//检索数据
$("#editPlanSearch").on('click', function () {
	document.getElementById('exportEditPlanId').href= BASE_PATH + "/admin/customneeds/exportEditPlanExcel.html?" + $("#editPlanForm").serialize();
	var param = getEditPlanParam();
    datatable2.settings()[0].ajax.data = param;
    datatable2.ajax.reload();
});
//检索
function editPlanListSearch(){
	$("#editPlanSearch").click();
}
//按回车键后检索
function onEnterSearch(){
	 var e = window.event || arguments.callee.caller.arguments[0];
     if(e && e.keyCode == 13){
    	 editPlanListSearch();
     }
}
//恢复默认
$('#resetPlanBtn').on('click',function(){
	$("#editPlanForm")[0].reset();
	$("#editPlanSearch").click();
});
//打开编辑计划页面
function editplan(id){
	layer.open({
  	    type: 2,
  	    title: false,
  	  	closeBtn:false,
  	    fix: false,
  	    maxmin: false,
  	    shadeClose: false,
  	    area: ['1100px', '500px'],
  	    content: BASE_PATH + '/admin/customneeds/editplanpage.html?id='+id,
  	    end:function(){
  	    	datatable2.ajax.reload(null,false);
  	    }
  	});
}
//关闭编辑计划
function closeEditPlan(id){
	layer.confirm('确定要关闭该计划吗?', {icon: 3, title:'提示'}, function(){
		$.ajax({ 
			type: 'POST', 
			data: {id:id}, 
			url: BASE_PATH + '/admin/customneeds/closeEditPlan.html',
            success: function (data) { 
            	layer.msg("关闭成功",{time: 2000});
            	datatable2.ajax.reload();
            },
            error: function (xhr) {
            	layer.msg("关闭失败",{time: 2000});
            } 
        });
	});
}
//启用计划
function enableEditPlan(id){
	layer.confirm('确定要启用该计划吗?', {icon: 3, title:'提示'}, function(){
		$.ajax({ 
			type: 'POST', 
			data: {id:id}, 
			url: BASE_PATH + '/admin/customneeds/enableEditPlan.html',
            success: function (data) { 
            	layer.msg("启用成功",{time: 2000});
            	datatable2.ajax.reload();
            },
            error: function (xhr) {
            	layer.msg("启用失败",{time: 2000});
            } 
        });
	});
}
//批量关闭计划
function batchClosePlan(){
	var ids = $('#checkedboxval').val();
	var length = $(".checkchild:checked").length;
	if(!ids){
		layer.alert("请至少选中一条记录",{time: 2000, icon:1});
	}else{
		layer.confirm('确定要关闭该计划吗?', {icon: 3, title:'提示'}, function(){
			/*var ids = [];
			$(".checkchild:checked").each(function(){
				ids.push($(this).val());
			});
			ids = ids.join(',');*/
			$.ajax({
				type: 'POST', 
				data: {ids:ids}, 
				url: BASE_PATH + '/admin/customneeds/betchClosePlan.html',
				success: function (data) { 
					layer.alert("关闭成功",{time: 2000, icon:1});
					$('#checkedboxval').val('');
					datatable2.ajax.reload(null,false);
					$('.checkall').attr('checked',false);
				},
				error: function (xhr) {
					layer.alert("关闭失败",{time: 2000, icon:1});
				} 
			});
		});
	}
}
//生成订单
function generateOrderNum(){
	var ids = $('#checkedboxval').val();
	var length = $(".checkchild:checked").length;
	if(!ids){
		layer.alert("请至少选中一条记录",{time: 2000, icon:1});
	}else{
		layer.confirm('确定要批量生成订单吗?', {icon: 3, title:'提示'}, function(){
			/*var ids = [];
			$(".checkchild:checked").each(function(){
				ids.push($(this).val());
			});
			ids = ids.join(',');*/
			$.ajax({
				type: 'POST', 
				data: {planids:ids}, 
				url: BASE_PATH + '/admin/customneeds/generateOrderNum.html',
				success: function (data) { 
					layer.alert("生成成功",{time: 2000, icon:1});
					$('#checkedboxval').val('');
					datatable2.ajax.reload();
					$('.checkall').attr('checked',false);
				},
				error: function (xhr) {
					layer.alert("生成失败",{time: 2000, icon:1});
				} 
			});
		});
	}
}
//点击之后给隐藏域赋值
$(document).on('click', '.checkchild', function(e) {
	var hiddenval = $('#checkedboxval').val();
	var thisval = $(this).val();
	var check = $(this).prop("checked");
	if(check){
		if(!hiddenval){
			$('#checkedboxval').val(thisval);
		}else{
			$('#checkedboxval').val(hiddenval+','+thisval);
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
			$('#checkedboxval').val(ids);
		}else{
			$('#checkedboxval').val(hiddenval);
		}
	}
	var length = $(".checkchild:checked").length;
	if(datatable2.page.len() == length){
		$(".checkall").prop("checked", true);
	}else{
		$(".checkall").prop("checked", false);
	}
});
