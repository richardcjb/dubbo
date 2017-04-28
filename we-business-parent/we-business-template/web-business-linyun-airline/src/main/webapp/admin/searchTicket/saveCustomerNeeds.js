//保存订单
function saveOrderInfo(){

	var linkName = $("#linkNameId").select2("val");
	var phoneNum = $("#phoneNumId").select2("val");
	//国际不需要验证 客户名称
	if(!(linkName || phoneNum)){
		layer.msg("客户姓名不能为空", "", 2000);
		return;
	}

	var customdata = {};
	var customerId = $('#linkManId').val();
	customdata.customerId = customerId;
	var generateOrder = $('#generateOrder').val();
	customdata.generateOrder = $("#generateOrder").is(':checked');
	//订单类型
	var orderType = $("#orderType").val();
	customdata.orderType = orderType;
	//订单状态
	var orderStatus = $('#orderStatus').val();
	customdata.orderStatus = orderStatus;
	//提醒方式
	var remindType = $("#remindType").val();
	customdata.remindType = remindType;
	//提醒日期
	var remindDate = $("#datepicker").val();
	customdata.remindDate = remindDate;
	//客户信息id
	var customerInfoId = $('#linkManId').val();
	customdata.customerInfoId = customerInfoId;
   
	//备注
	var cRemark = $('#cRemark').val();
	customdata.cRemark = cRemark;
	
	var row = [];
	$('.DemandDiv').each(function(i){
		var lenthcustom = '';
		var row1 = {};
		var leavecity = $(this).find('[name=cOutcity]').val();
		var arrivecity = $(this).find('[name=cArrivalcity]').val();
		var cOutDate = $(this).find('[name=cOutDate]').val();
		var cPersonAmount = $(this).find('[name=cPersonAmount]').val();
		var tickettype = $(this).find('[name=tickettype]').val();
		var cRemark = $(this).find('[name=cRemark]').val();
		//出发城市
		if (leavecity) {
			leavecity = leavecity.join(',');
			lenthcustom += leavecity;
		}else{
			lenthcustom += '';
		}
		row1.leavecity = leavecity;
		//抵达城市
		if (arrivecity) {
			arrivecity = arrivecity.join(',');
			lenthcustom += arrivecity;
		}else{
			lenthcustom += '';
		}
		row1.arrivecity = arrivecity;
		row1.leavedate = cOutDate;
		row1.peoplecount = cPersonAmount;
		row1.tickettype = tickettype;
		row1.cRemark = cRemark;
		console.log(tickettype);
		lenthcustom += $(this).find('[name=cOutDate]').val();
		lenthcustom += $(this).find('[name=cPersonAmount]').val();
		lenthcustom += $(this).find('[name=cRemark]').val();

		var airrows = [];
		$(this).find('[name=airLineInfo]').each(function(i){
			var airrow = {};
			var lengthAir = '';
			var aircom = $(this).find('[name=cAirlineCompany]').val();
			var ailinenum = $(this).find('[name=cAirlineNum]').val();
			var cAirOutDate = $(this).find('[name=cAirOutDate]').val();
			var cAirArrivalDate = $(this).find('[name=cAirArrivalDate]').val();
			var cAirCost = $(this).find('[name=cAirCost]').val();
			var cAirPretium = $(this).find('[name=cAirPretium]').val();
			if (aircom) {
				aircom = aircom.join(',');
				lengthAir += aircom;
			}else{
				lengthAir += '';
			}
			airrow.aircom = aircom;
			if (ailinenum) {
				ailinenum = ailinenum.join(',');
				lengthAir += ailinenum;
			}else{
				lengthAir += '';
			}
			airrow.ailinenum = ailinenum;
			airrow.leavetime = cAirOutDate;
			airrow.arrivetime = cAirArrivalDate;
			airrow.formprice = cAirCost;
			airrow.price = cAirPretium;

			lengthAir += $(this).find('[name=cAirOutDate]').val();
			lengthAir += $(this).find('[name=cAirArrivalDate]').val();
			lengthAir += $(this).find('[name=cAirCost]').val();
			lengthAir += $(this).find('[name=cAirPretium]').val();

			if(lengthAir.length > 0){
				airrows.push(airrow);
			}
			lenthcustom += lengthAir;
		});
		row1.airinfo = airrows;
		if(lenthcustom.length > 0){
			row.push(row1);
		}

	});
	customdata.customdata=row;
	console.log(JSON.stringify(customdata));
	//保存中
	var layerIndex =  layer.load(1, {shade: "#000"});
	//新窗口对象
	var tempwindow = window.open();
	$.ajax({
		dataType : 'json',
		type: 'POST', 
		delay : 250,
		async:false,
		data: {
			"data":JSON.stringify(customdata)
		}, 
		url : BASE_PATH  + "/admin/search/saveCustomerNeeds.html",
		success: function (result) { 
			if(200==result.status){
				//关闭加载层
				layer.close(layerIndex);
				layer.msg('添加成功', {time:1500}, function(){
					//跳转新的标签页
					var url = BASE_PATH;
					var data = result.data;
					if(data.orderStatus == 1){
						url += '/admin/inland/queryDetail.html?id='+data.orderId;
					}else{
						url += '/admin/inland/bookingDetail.html?id='+data.orderId;
					}
					tempwindow.location = url;
					//重新刷新页面
					window.location.reload();
				}); 

			}else{
				layer.msg("添加失败","",4000);
			}

		},
		error: function (xhr) {
			layer.msg("添加失败","",4000);
		} 
	});
}