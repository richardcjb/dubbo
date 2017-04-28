/*function fileupload() {
	if ($("#fileID").val() == "") {
		layer.msg("上传文件不能为空", "", 2000);
		return false;
	}
	var actionUrl = BASE_PATH + "/admin/customer/upload.html";
	var dataurl = $("#fileContent").val();
	var blob = dataURLtoBlob(dataurl) ;
	var formData = new FormData();
	formData.append("fileId", blob,"filename");
	var loadLayer = layer.load();
	$.ajax({ 
        type: "POST",//提交类型  
        dataType: "json",//返回结果格式  
        url: actionUrl,//请求地址  
        async: true  ,
        processData: false, //当FormData在jquery中使用的时候需要设置此项
        contentType: false ,//如果不加，后台会报表单未封装的错误(enctype='multipart/form-data' )
      	//请求数据  
        data:formData ,
        success: function (obj) {//请求成功后的函数  
        	alert(obj);
        	layer.close(loadLayer) ;
        	layer.msg("设置成功", "", 2000);
            layer.close(index) ;//关闭层
        },  
        error: function (obj) {
        	layer.msg(obj.message);
        }
	});  // end of ajaxSubmit

	
}
//把dataUrl类型的数据转为blob
function dataURLtoBlob(dataurl) { 
    var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
    while(n--){
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new Blob([u8arr], {type:mime});
}*/