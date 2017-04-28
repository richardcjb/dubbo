<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<meta http-equiv="Access-Control-Allow-Origin" content="*" />
<meta name="alexaVerifyID" content="" />
<title>公司权限配置</title>
<link rel="stylesheet"
	href="${base }/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${base }/public/dist/css/AdminLTE.css">
<link rel="stylesheet" href="${base }/public/dist/css/dict.css">
<link rel="stylesheet"
	href="${base }/public/dist/css/font-awesome.min.css">
<link rel="stylesheet" href="${base }/public/dist/css/ionicons.min.css">
<link rel="stylesheet"
	href="${base }/public/dist/css/skins/_all-skins.min.css">
<link rel="stylesheet"
	href="${base }/public/dist/css/bootstrapValidator.css" />
<!-- zTree -->
<link rel="stylesheet"
	href="${base }/common/js/zTree/css/zTreeStyle/zTreeStyle.css">
<!-- style -->
<link rel="stylesheet" href="${base}/public/css/style.css">
<style type="text/css">
	.wu-example .statusBar .btns .uploadBtn {background: #3c8dbc !important;color: #fff;border-color: transparent;position: relative;top: -122px;height: 40px;border-radius: 5px;}
</style>
</head>
<body onresize=hero();>
	<div class="modal-top">
		<form id="companyUpdateForm">
			<div class="modal-header boderButt">
				<button type="button" class="btn btn-primary right btn-sm"
					onclick="closewindow();">返回</button>
				<input type="button" id="submitButton"
					class="btn btn-primary right btn-sm" onclick="submitCompany();"
					value="保存" />
				<h4>公司权限配置</h4>
			</div>
			<div class="modal-body" style="height:430px;overflow-y: auto;">
				<div class="tab-content">
					<input id="companyId" name="companyId" type="hidden" value="${obj.company.id }">
					<input id="functionIds" name="functionIds" type="hidden" value=""/>
					<div class="form-group row comForSty">
						<label class="col-sm-3 text-right padding"><h5>公司名称：</h5></label>
						<div class="col-sm-9 padding">
							<h5 class="text-red">${obj.company.comName }</h5>
						</div>
					</div>
					<div class="form-group row comForSty">
                        <label class="col-sm-3 text-right padding"><h5>功能：</h5></label>
                        <div class="col-sm-9 text-right padding comSty">
                          <ul id="treeDemo" class="ztree"></ul>
                        </div>
                    </div>
				</div>
			</div>
		</form>
	</div>
	<!-- jQuery 2.2.3 -->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
	<!--zTree -->
	<script src="${base}/common/js/zTree/jquery.ztree.core-3.5.js"></script>
	<script src="${base}/common/js/zTree/jquery.ztree.excheck-3.5.js"></script>
	<script src="${base}/common/js/zTree/jquery.ztree.exedit-3.5.js"></script>
	<!--layer -->
	<script src="${base}/common/js/layer/layer.js"></script>
	<jsp:include page="/WEB-INF/common/webupload_resource.jsp"></jsp:include>
	<script type="text/javascript">
		// Validate the form manually
		$('#submitButton').click(function() {
			$('#companyUpdateForm').bootstrapValidator('validate');
		});
		//关闭当前弹层
		function closewindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
		//保存
		function submitCompany() {
			setFunc();
			var _companyId = $("input#companyId").val() ;
			var _functionIds = $("input#functionIds").val() ;
			$.ajax({
				type : 'POST',
				data : {
					companyId : _companyId,
					functionIds:_functionIds
				},
				url : '${base}/admin/authority/companyfunction/update.html',
				success : function(data) {
					var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
					window.parent.successCallback('2');
					parent.layer.close(index);

				},
				error : function(xhr) {
					layer.msg("保存失败",{time:2000});
				}
			});
		}
		//删除
		function deleteCompany() {
			layer.confirm('确认你的操作?', {
				icon : 3,
				title : '提示'
			}, function() {
				$.ajax({
					type : 'POST',
					data : {
						id : '${obj.company.id}'
					},
					url : '${base}/admin/Company/updateDeleteStatus.html',
					success : function(data) {
						var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						window.parent.successCallback('3');
						parent.layer.close(index);
					},
					error : function(xhr) {
						layer.msg("删除失败",{time:2000});
					}
				});
			});
		}
	</script>
	<!-- 功能树 -->
	<script type="text/javascript">
		var setting = {
				check: {
					enable: true,//显示复选框
					chkStyle: "checkbox",
					chkboxType: { "Y": "ps", "N": "ps" }
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					beforeCheck: zTreeBeforeCheck
				}
			};
		//初始化功能树
		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		});
		function setFunc(){
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			var nodes =  treeObj.getCheckedNodes(true);
			var funcIds = "" ;
			for(var i = 0 ; i < nodes.length; i ++){
				funcIds += (nodes[i].id + ",") ;
			}
			$("input[name='functionIds']").val(funcIds) ;
		}
		//默认选中个人信息和操作台
		function zTreeBeforeCheck(treeId, treeNode) {
			/* if((treeNode.id == 43 || treeNode.id == 44) && treeNode.checked){
				return false ;
			}else{
				return true;
			} */
			return true;
		};
		//遍历得到的对象
		var zNodes =[
			{id:"0", pId:"0", name:"功能菜单", open:true},
			<c:forEach var="p" items="${obj.list}">
				<c:choose>
					<c:when test="${p.id eq 43 || p.id eq 44}">
						{ id:"${p.id }", pId:"${p.parentId }", name:"${p.name }", open:true,checked:true},
					</c:when>
					<c:otherwise>
						{ id:"${p.id }", pId:"${p.parentId }", name:"${p.name }", open:true,checked:"${p.checked}"},
					</c:otherwise>
				</c:choose>
			</c:forEach>
		];
	</script>
	
</body>
</html>