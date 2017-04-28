//第一次初始化加载
initTeamSelect2();

function initTeamSelect2(){
	$('.setTeamMore').each(function(i){
		if($('.setTeamMore').length - 1 == i){
			//出发城市下拉列表
			$("#teamOutCity"+i).select2({
				ajax : {
					url : BASE_PATH  + "/admin/search/getCitySelect.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						var ids = $('#teamArriveCity'+i).val();
						if(ids){
							ids = ids.join(',');
						}
						return {
							cityname : params.term, 
							ids:ids,
							page : params.page
						};
					},
					processResults : function(data, params) {
						params.page = params.page || 1;
						var selectdata = $.map(data, function (obj) {
							obj.id = obj.dictCode; 
							obj.text = obj.dictCode +" - "+ obj.englishName +" - "+ obj.countryName; 
							return obj;
						});
						return {
							results : selectdata
						};
					},
					cache : false
				},
				escapeMarkup : function(markup) {
					return markup;
				}, // let our custom formatter work
				minimumInputLength : 1,
				maximumInputLength : 20,
				language : "zh-CN", 
				maximumSelectionLength : 1, 
				tags : false
			});

			//抵达城市查询
			$("#teamArriveCity"+i).select2({
				ajax : {
					url : BASE_PATH  + "/admin/search/getCitySelect.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						var ids = $('#teamOutCity'+i).val();
						if(ids){
							ids = ids.join(',');
						}
						return {
							cityname : params.term, 
							ids:ids,
							page : params.page
						};
					},
					processResults : function(data, params) {
						params.page = params.page || 1;
						var selectdata = $.map(data, function (obj) {
							obj.id = obj.dictCode; 
							obj.text = obj.dictCode +" - "+ obj.englishName +" - "+ obj.countryName; 
							return obj;
						});
						return {
							results : selectdata
						};
					},
					cache : true
				},
				escapeMarkup : function(markup) {
					return markup;
				}, // let our custom formatter work
				minimumInputLength : 1,
				maximumInputLength : 20,
				language : "zh-CN", 
				maximumSelectionLength : 1, 
				tags : false
			});
		}
	});
}

$(function(){
	//段数 样式切换
	/*$('.paragraphBtn li').click(function(){
		$(this).addClass('btnStyle').siblings().removeClass('btnStyle');
	});*/
	$(document).on("click",".paragraphBtn li",function(){
		$(this).addClass('btnStyle').siblings().removeClass('btnStyle');
	});

	//添加 setTeamMore
	$('.addTeamIconTd').click(function(){
		var clonediv = $('.setTeamMore').first();
		var divTest =  $('.setTeamMore').last(); 
		var newDiv = clonediv.clone(false,true);
		divTest.after(newDiv);
		$('.setTeamMore').each(function(i){
			if($('.setTeamMore').length - 1 == i){
				$(this).find('.addTeamIconTd').remove();
				$(this).append('<td class="removeIconTd"><i class="glyphicon glyphicon-minus removeMore"></i></td>');
				$(this).find('.removIconId').remove();
				$('.setTeamMore').first().find('.removIconId').hide();
				//设置新的出发城市下拉ID
				var teamOutCity = $(this).find('[name=origin1]');
				teamOutCity.attr("id","teamOutCity"+i);
				$('#teamOutCity'+i).next().remove();
				//设置新的返回城市下拉框ID
				var teamArriveCity = $(this).find('[name=destination1]');
				teamArriveCity.attr("id","teamArriveCity"+i);
				$('#teamArriveCity'+i).next().remove();
				//设置新的出发日期
				var teamDeparturedate = $(this).find('[name=departuredate1]');
				teamDeparturedate.attr("id","teamOutDatepicker"+i);
				$("#teamOutDatepicker"+i).val("");
				//设置新的到达日期
				var teamReturndate = $(this).find('[name=returndate1]');
				teamReturndate.attr("id","teamReturnDatepicker"+i);
				teamReturndate.attr("onFocus",'WdatePicker({dateFmt:"yyyy-MM-dd",minDate:"#F{$dp.$D(\'teamOutDatepicker'+ i +'\')}"})');
				$("#teamReturnDatepicker"+i).val("");
			}
		});
		initTeamSelect2();
	});
});
$(document).on('click', '.removeMore', function(e) {
	if($('.setTeamMore').length > 1){
		if($('.setTeamMore').length == 2){
			$('.setTeamMore').last().find('.removIconId').show();
		}
		$(this).parent().parent().remove();
	}
});


/*航空公司查询*/
$("#teamAirline").select2({
	ajax : {
		url : BASE_PATH  + '/admin/search/getAirLineSelect.html',
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				airlinename : params.term,
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				obj.id = obj.dictCode;
				obj.text = obj.dictCode +"-"+ obj.dictName; 
				return obj;
			});
			return {
				results : selectdata
			};
		},
		cache : true
	},

	escapeMarkup : function(markup) {
		return markup;
	}, 
	minimumInputLength : 1,
	maximumInputLength : 20,
	language : "zh-CN",
	maximumSelectionLength : 1, 
	tags : false
});


/*-----------------------拼接多段  start------------------------*/
function makePart(){
	//段数
	var airTeamType = $("input[name='voyageType1']:checked").val();
	var html = "";
	if(airTeamType == 1){
		html = '<li id="teamNum1" class="btnStyle">第1段</li>';
		document.getElementById('travelTeamTypeNum').innerHTML=html;
	}
	if(airTeamType == 2){
		/*往返不需要显示第二段*/
		/*html = '<li id="teamNum1" class="btnStyle dClass">第1段</li><li id="teamNum2"  class="dClass">第2段</li>';*/
		html = '<li id="teamNum1" class="btnStyle dClass">第1段</li>';
		document.getElementById('travelTeamTypeNum').innerHTML=html;
	}
	/* 多程 显示多段 */
	if(airTeamType == 3){
		/*
		//方案一  显示往返段
		for(var i=1; i<$('.setTeamMore').length*2; i=i+2){
			var j = i+1;
			html +='<li id="teamNum'+i+'">第'+i+'段</li><li id="teamNum'+j+'">第'+j+'段</li>';
		}*/
		//方案二  显示去程段
		html ='<li id="teamNumMore1" class="btnStyle">第1段</li>';
		for(var i=2; i<=$('.setTeamMore').length; i++){
			html +='<li id="teamNumMore'+i+'">第'+i+'段</li>';
		}
		document.getElementById('travelTeamTypeNum').innerHTML=html;
	}
}
/*-----------------------拼接多段  end--------------------------*/


/*-----------------------select2隐藏域赋值  start------------------------*/
/* 航空公司 */
airlineNameOpt = function(){
	var airName = $('#teamAirline').find("option:selected").text();
	$("#teamAirlineName").val(airName);
	var selectedAirId = $("#teamAirline").select2("val");
	$("#teamAirlineCode").val(selectedAirId);
}
/*-----------------------select2隐藏域赋值  end----------------------------*/
$(document).on('click','#teamNum1',function(){
	var index=0;
	$("#teamorigin").val($("#teamOutCity"+index).select2("val"));
	$("#teamdestination").val($("#teamArriveCity"+index).select2("val"));
	$("#teamdeparturedate").val($("#teamOutDatepicker"+index).val());
	$("#teamreturndate").val($("#teamReturnDatepicker"+index).val());
	//$("#searchTeamTicketsBtn").click();
	searchInternetOrders();
	$("#travelTeamTypeNum li").attr("class","");
	$("#teamNum1").attr("class","btnStyle");
});
$(document).on('click','#teamNum2',function(){
	var index=0;
	$("#teamorigin").val($("#teamArriveCity"+index).select2("val"));
	$("#teamdestination").val($("#teamOutCity"+index).select2("val"));
	$("#teamdeparturedate").val($("#teamReturnDatepicker"+index).val());
	//$("#teamreturndate").val($("#teamOutDatepicker"+index).val());
	//$("#searchTeamTicketsBtn").click();
	searchInternetOrders();
	$("#travelTeamTypeNum li").attr("class","");
	$("#teamNum2").attr("class","btnStyle");
});


//机票库表格初始化
var datatable2;
function initDatatable2() {
	datatable2 = $('#datatable2').DataTable({
		"searching":false,
		"bLengthChange": false,
		"processing": true,
		"serverSide": true,
		"destroy": true,
		"stripeClasses": [ 'strip1','strip2' ],
		"language": {
			"url": BASE_PATH + "/public/plugins/datatables/cn.json"
		},
		"ajax": {
			"url": BASE_PATH + '/admin/search/searchTeamTickets.html',
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
                    			result += '<li style="list-style:none;">'+(value.leavetime+'/'+value.arrivetime)+'</li>';
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
		            		var travelname = row.travelname;
		            		if(null == travelname || ""== travelname){
		            			return "";
		            		}
		            		return travelname;
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
		            }]
	});
}

//控制复选框
$(".checkall").click(function () {
	var check = $(this).prop("checked");
	$(".checkchild").prop("checked", check);
});


//获取检索条件数据
function getEditPlanParam(){
	var origin = $('#teamorigin').val();
	var destination = $('#teamdestination').val();
	var departuredate = $('#teamdeparturedate').val();
	var returndate = $('#teamreturndate').val();
	var includedcarriers = $('#teamAirlineCode').val();
	var param = {
			origin:origin,
			destination:destination,
			departuredate:departuredate,
			returndate:returndate,
			includedcarriers:includedcarriers
	};
	return param;
}

/* 机票库多程查询 */
$("#searchTeamTicketsBtn").click(function() {
	/*var linkName = $("#linkNameId").select2("val");
	var phoneNum = $("#phoneNumId").select2("val");
	if(!(linkName || phoneNum)){
		layer.msg("客户姓名不能为空", "", 2000);
		return;
	}*/
	$("#teamTrId").attr("style", "");

	//默认第一段
	var index = 0;
	$("#teamorigin").val($("#teamOutCity"+index).select2("val"));
	$("#teamdestination").val($("#teamArriveCity"+index).select2("val"));
	$("#teamdeparturedate").val($("#teamOutDatepicker"+index).val());
	$("#teamreturndate").val($("#teamReturnDatepicker"+index).val());
	//加载列表数据
	initDatatable2();

	makePart();

	var param = getEditPlanParam();
	datatable2.settings()[0].ajax.data = param;
	datatable2.ajax.url(BASE_PATH + '/admin/search/searchTeamTickets.html').load();
	
});


function selectteam(){
	$("#searchTeamTicketsBtn").click();
}

function onkeyTeamEnter(){
	var e = window.event || arguments.callee.caller.arguments[0];
	if(e && e.keyCode == 13){
		selectteam();
	}
}
