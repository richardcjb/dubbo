<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<title>部门职位-添加</title>
<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
<link rel="stylesheet" href="${base}/public/dist/css/authoritymanage.css">
<link rel="stylesheet" href="${base }/public/dist/css/bootstrapValidator.css" />
<!-- zTree -->
<link rel="stylesheet" href="${base }/common/js/zTree/css/zTreeStyle/zTreeStyle.css">
<%-- 解决部门验证样式 --%>
<style type="text/css">
	small.help-block {display: inline-block;position: relative;left: 151px;top: -5px;}
	.form-control-feedback {position: absolute;top: 0px;right: 330px;}
</style>
</head>
<body class="bodyOne" onload="document.getElementById('deptName').focus()">
	<div class="divContent">
		<form id="addDeptForm" method="post">
			<div class="headerClass">
				<button type="button" class="btn btn-primary right btn-sm" onclick="closewindow();">取消</button>
				<button type="button" id="submit" class="btn btn-primary right btn-sm">保存</button>
				<h4>添加职位部门</h4>
			</div>
			<div class="modal-body" style="height:435px;overflow-y: auto;">
	          <div class="departmentName form-group"><!--部门权限 设置-->
                 <ul class="addDepartment">
                 	<input id="jobJson" name="jobJson" type="hidden" value=""/>
                   <li><label class=" text-right">部门名称：</label></li>
                   <li class="li-input">
                   		<div>
                   			<input id="deptName" name="deptName"  type="text" class="form-control input-sm inputText" placeholder="请输入部门名称">
	                    	<span class="prompt">*</span>
                   		</div>
                   </li>
                   <li><button type="button" class="btn btn-primary btn-sm btnPadding" id="addJob">添加职位</button></li>
                 </ul>
	          </div><!--end 部门权限 设置-->
	
	          <div class="jobName cf"><!--职位权限 设置-->
	              
	          </div><!--end 职位权限 设置-->
	        </div>
        </form>
	</div>
	<!--JS 文件-->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
	<script	src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script>
	<!-- SlimScroll -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script>
	<!-- FastClick -->
	<script src="${base}/public/dist/js/app.min.js"></script>
	<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
	<!--zTree -->
	<script src="${base}/common/js/zTree/jquery.ztree.core-3.5.js"></script>
	<script src="${base}/common/js/zTree/jquery.ztree.excheck-3.5.js"></script>
	<script src="${base}/common/js/zTree/jquery.ztree.exedit-3.5.js"></script>
	<script src="${base}/common/js/layer/layer.js"></script>
	<!-- AdminLTE App -->

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
	//默认选中个人信息和操作台
	function zTreeBeforeCheck(treeId, treeNode) {
		if((treeNode.id == 43 || treeNode.id == 44) && treeNode.checked){
			return false ;
		}else{
			return true;
		}
	};
	//遍历得到的对象
	var zNodes =[
		 {id:"0", pId:"0", name:"职位权限设置", open:true},
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
 	
   var treeIndex = 0 ;
   $(function () {
		//部门职位 添加职位
	    $('#addJob').click(function(){
	       $(".job_container .ztree").hide();
	       $('.jobName').append('<div class="job_container form-group"><ul class="addDepartment marHei"><li><label class="text-right">职位名称：</label></li><li class="li-input inpPadd"><input id="jobName" name="jobName'+(treeIndex)+'" type="text" class="form-control input-sm inputText" placeholder="请输入职位名称"></li><li><button type="button" class="btn btn-primary btn-sm btnPadding" id="settingsPermis">设置权限</button><button type="button" class="btn btn-primary btn-sm btnPadding" id="deleteBtn" >删除</button></li></ul>'
	       +'<div class="ztree"><ul id="tree_'+treeIndex+'"></ul></div></div>');
	       treeIndex++;
		   var ztree_container = $(".job_container:last").find("div.ztree").find("ul:first");
	       var treeId = ztree_container.attr("id") ;
	       var treeObj = $.fn.zTree.getZTreeObj(treeId);
	       if(null == treeObj || undefined == treeObj){
		      	//初始化ztree
			    $.fn.zTree.init(ztree_container, setting, zNodes);
	      	}
	    });
	    //删除按钮
	    $('.jobName').on("click","#deleteBtn",function() {
	      $(this).parent().parent().next().remove();
	      $(this).closest('.job_container').remove();
	
	    });
	    //设置权限 按钮
	    $('.jobName').on("click","#settingsPermis",function() {
	    	$(this).parents('.marHei').next().toggle('500');
	        $(this).parents(".job_container").siblings().children('.ztree').hide();
	      	var ztree_container = $(this).parents(".marHei").next("div.ztree").find("ul:first");
	      	var treeId = ztree_container.attr("id") ;
	      	
	      	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	      	if(null == treeObj || undefined == treeObj){
	      	//初始化ztree
		    	$.fn.zTree.init(ztree_container, setting, zNodes);
	      	}
	    });
    });
   //设置功能
	function setFunc(){
	   var jobInfos = [];
	   //取所有树
	   $(".job_container").each(function(index,container){
		   var jobName = $(container).find("input[id='jobName']").val();
		   var treeObj = $.fn.zTree.getZTreeObj("tree_" + index);
		   var nodes =  treeObj.getCheckedNodes(true);
		   var funcIds = "" ;
			$(nodes).each(function(i,node){
				funcIds += node.id + ",";
			});
		   var job = new Object();
		   job.jobName=jobName;
		   job.functionIds=funcIds;
		   jobInfos.push(job);
	   });
	   var jobJson = JSON.stringify(jobInfos) ;
	   $("#jobJson").val(jobJson) ;
	}
</script>
<script type="text/javascript">
//验证
$(document).ready(function(){
	$('#addDeptForm').bootstrapValidator({
		message: '验证不通过!',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	deptName: {
                validators: {
                    notEmpty: {
                        message: '部门名称不能为空!'
                    },
                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
                         url: '${base}/admin/authority/authoritymanage/checkDeptNameExist.html',//验证地址
                         message: '部门名称已存在，请重新输入!',//提示消息
                         delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                         type: 'POST',//请求方式
                         //自定义提交数据，默认值提交当前input value
                         data: function(validator) {
                            return {
                            	deptName:$('#deptName').val()
                            };
                         }
                     }
                }
            },
            jobName: {
                validators: {
                    notEmpty: {
                        message: '职位名称不能为空!'
                    },
                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
                         url: '${base}/admin/authority/authoritymanage/checkJobNameExist.html',//验证地址
                         message: '此职位已存在，请重新输入!',//提示消息
                         delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                         type: 'POST',//请求方式
                         //自定义提交数据，默认值提交当前input value
                         data: function(validator) {
                            return {
                            	jobName:$('#jobName').val()
                            };
                         }
                     }
                }
            }
        }
	});
});
	//保存
	$("#submit").click(function(){
		setFunc();
		$('#addDeptForm').bootstrapValidator('validate');
		var bootstrapValidator = $("#addDeptForm").data('bootstrapValidator');
		var _deptName = $("input#deptName").val();
		var _jobJson = $("input#jobJson").val();
		
		try{
			$("input[id='jobName']").each(function(index,element){
				var eachJobName = $(element).val();
				if(null == eachJobName || undefined == eachJobName || "" == eachJobName || "" == $.trim(eachJobName)){
					throw "职位名称不能为空";
				}
			}) ;
		}catch(e){
			layer.msg(e) ;
			return false ;
		}
		
		if(bootstrapValidator.isValid()){
			$.ajax({
	           cache: false,
	           type: "POST",
	           url:'${base}/admin/authority/authoritymanage/add.html',
	           data:{
					deptName:_deptName,
					jobJson:_jobJson
			   },
	           error: function(request) {
	              layer.msg('添加失败!',{time:2000});
	           },
	            success: function(data) {
				layer.load(1, {
					 shade: [0.1,'#fff'] //0.1透明度的白色背景
				});
				  var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
				  parent.layer.close(index);
				  window.parent.successCallback('1');
	           }
	       });
		}
	}); 
	//提交时开始验证
	$('#submit').click(function() {
	    $('#addDeptForm').bootstrapValidator('validate');
	});
	//点击返回关闭窗口
	function closewindow(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
</script>
</body>
</html>