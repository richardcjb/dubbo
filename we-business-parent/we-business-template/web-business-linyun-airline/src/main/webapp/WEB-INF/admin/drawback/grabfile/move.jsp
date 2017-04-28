<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<title>移动到</title>
<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
<link rel="stylesheet" href="${base}/public/dist/css/dict.css">
<link rel="stylesheet" href="${base }/public/dist/css/bootstrapValidator.css"/>
<!-- zTree -->
<link rel="stylesheet" href="${base }/common/js/zTree/css/zTreeStyle/zTreeStyle.css">
<style type="text/css">
    .moveDiv{width: 100%;min-height: 300px;border: solid 1px #f1f1f1;border-radius: 3px;padding: 10px;}
    .moveCentent{background-color: #ececec;width: 100%;height: 100%;}
</style>
<!-- jQuery 2.2.3 -->
<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="${base}/public/bootstrap/js/bootstrap.js"></script>
<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
<!--zTree -->
<script src="${base}/common/js/zTree/jquery.ztree.core-3.5.js"></script>
<script src="${base}/common/js/zTree/jquery.ztree.excheck-3.5.js"></script>
<script src="${base}/common/js/zTree/jquery.ztree.exedit-3.5.js"></script>
<script src="${base}/common/js/layer/layer.js"></script>
</head>
<body>
	<div class="modal-top">
    <div class="modal-header boderButt">
            <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow();">取消</button>
            <input type="button" id="submit" class="btn btn-primary right btn-sm" value="保存"/>
            <h4>移动到</h4>
          </div>
          <div class="modal-body">
            <div class="moveDiv">
                <div id="fileName" class="moveCentent">
	                <div id="tree_fileName" class="ztree">
	                	<input type="hidden" id="ztreeids"/> 
	                	<input type="hidden" id="fileName" /> 
	                </div>
                </div>
            </div>
          </div>
	</div>
<script type="text/javascript">
//初始化
$(document).ready(function () {
	$.fn.zTree.init($("#tree_fileName"), setting, zNodes);
    var zTreeObj = $.fn.zTree.getZTreeObj("tree_fileName");
   	var parentId = "${obj.parentId}";
   	var toBeDeleteNode =  zTreeObj.getNodeByParam("id", parentId, null);
   	zTreeObj.removeChildNodes(toBeDeleteNode);//移出选中节点下面的子节点
   	zTreeObj.removeNode(toBeDeleteNode);//移出选出的本身节点
   	
});
//ztree配置
var setting = {
	check: {
		enable: false,//显示复选框
		chkStyle: "checkbox",
		chkboxType: { "Y": "ps", "N": "ps" }
	},
	data: {
		simpleData: {
			enable: true
		}
	},
    callback:{
    	onClick: zTreeOnClick
    }
};
//遍历得到的对象
var zNodes =[
	{id:"0", pId:"0", name:"全部文件", open:true},
	 <c:forEach var="p" items="${obj.list}">
		{ id:"${p.id }", pId:"${p.parentId }", name:"${p.fileName }", open:false,checked:false},
	 </c:forEach>
];
//获取选中的节点
function zTreeOnClick(event,treeId,treeNode){
    $("input#ztreeids").val(treeNode.id);
    $("input#fileName").val(treeNode.name);
  //保存
    $("#submit").click(function(){
    	var _parentId = "${obj.parentId}";//要被移动文件id
    	var _id = treeNode.id;//目标文件id
    	layer.confirm("确定要移动吗?", {
    	    btn: ["是","否"], //按钮
    	    shade: false //不显示遮罩
    	}, function(){
    		$.ajax({
    	           cache: false,
    	           type: "POST",
    	           url:'${base}/admin/drawback/grabfile/fileMove.html',
    	           data:{
    					id:_id,
    					parentId:_parentId
    			   },
    	           error: function(request) {
    	              layer.msg('添加失败!');
    	           },
    	            success: function(data) {
    				layer.load(1, {
    					 shade: [0.1,'#fff'] //0.1透明度的白色背景
    				});
    				  var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    				  parent.layer.close(index);
    				  window.parent.successCallback('4');
    	           }
    	       });
    	},function(){
    	    // 取消之后不用处理
    	});
    });
}

//点击取消
function closewindow(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}
</script>
</body>
</html>