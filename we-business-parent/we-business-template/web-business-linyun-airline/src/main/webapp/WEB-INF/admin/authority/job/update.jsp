<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<div class="panel_box">
	<div class="panel_content nopadding">
		<form method="post" action="${base}/admin/authority/job/update.html" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
			<div class="form_item">
				<label class="form_label">职位名称：</label>
			  	<div class="form_ctrl">
			  		<%-- 职位id --%>
			  		<input name="id" type="hidden" value="${obj.job.id}"/>
			  		<%-- 职位功能id --%>
			  		<input name="functionIds" type="hidden" value=""/>
			    	<input name="name" type="text" class="required"  maxlength="10" value="${obj.job.name}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">备注：</label>
			  	<div class="form_ctrl">
			    	<input name="remark" type="text" class="required"  maxlength="10" value="${obj.job.remark}"/>
			  	</div>
			</div>
			
			<div class="form_item">
				<label class="form_label"><button type="button" class="btn btn_modify" onclick="initTree()">功能</button></label>
				<div class="form_ctrl">
						<div id="ztreeDiv">
							<c:forEach var="p" items="${obj.list}">
								<li type="hidden" id="${p.id }" pId="${p.parentId }" name="${p.name }" check="${p.checked}"/>
							</c:forEach>
						</div>
						<ul id="treeDemo" class="ztree"></ul>
				</div>
			</div>
			
            <div class="form_actions">
            	<input name="id" type="hidden" value="${obj.id}"/>
              	<button type="submit" class="btn btn_add">保存</button>
				<button type="button" class="btn btn_del closeDialog">取消</button>
            </div>
          </form>
	</div>
</div>