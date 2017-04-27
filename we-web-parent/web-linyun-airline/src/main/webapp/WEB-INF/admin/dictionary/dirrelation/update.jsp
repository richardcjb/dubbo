<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<div class="panel_box">
	<div class="panel_content nopadding">
		<form method="post" action="${base}/admin/dictionary/dirrelation/update.html" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
			<div class="form_item">
				<label class="form_label">字典id：</label>
				<div class="form_ctrl">
					<input name="sourceId" type="text" class="required" maxlength="32" value="${obj.dirtype.sourceId}"/>
				</div>
			</div>
			<div class="form_item">
				<label class="form_label">关联字典id：</label>
				<div class="form_ctrl">
					<input name="targetId" type="text" class="required" maxlength="32" value="${obj.dirtype.targetId}"/>
				</div>
			</div>
            <div class="form_actions">
              	<button type="submit" class="btn btn_add" onclick="setFunc()">保存</button>
				<button type="button" class="btn btn_del closeDialog">取消</button>
            </div>
          </form>
	</div>
</div>
<script>
seajs.use('${static_path}/xiaoka/dlws-xiaoka-game/dictionary/dirrelation/update');

var setting = {
		check: {
			enable: true,
			chkStyle: "checkbox",
			chkboxType: { "Y": "ps", "N": "ps" }
		},
		data: {
			simpleData: {
				enable: true
			}
		}
	};
		
	function initTree(){
		if(!we.utils.isEmpty($("#treeDemo").html())){
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			treeObj.expandAll(false);
			return ;
		}
		
		//获取zNodes
		var zNodes = new Array();
		$('#ztreeDiv li').each(function(){
			var $this = $(this);
			var node =  { id:$this.attr('id'),name:$this.attr('targetId'),open:"true"};
			//每个节点
			we.utils.pushOnly(node,zNodes);
		});
		
		//调用初始方法
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	}

	function setFunc(){
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes =  treeObj.getCheckedNodes(true);
		
		var funcIds = "" ;
		for(var i = 0 ; i < nodes.length; i ++){
			funcIds += (nodes[i].id + ",") ;
		}
		$("input[name='functionIds']").val(funcIds) ;
	}
</script>