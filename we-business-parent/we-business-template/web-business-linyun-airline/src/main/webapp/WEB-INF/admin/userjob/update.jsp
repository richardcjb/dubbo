<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<div class="panel_box">
	<div class="panel_content nopadding">
		<form method="post" action="${base}/admin/userjob/update.html" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
			
			<div class="form_item">
				<label class="form_label">用户id：</label>
			  	<div class="form_ctrl">
			    	<input name="userid" type="text" class="required"  maxlength="10" value="${obj.userid}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">公司职位id：</label>
			  	<div class="form_ctrl">
			    	<input name="companyJobId" type="text" class="required"  maxlength="10" value="${obj.companyJobId}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">状态：</label>
			  	<div class="form_ctrl">
			    	<input name="status" type="text" class="required"  maxlength="10" value="${obj.status}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">入职时间：</label>
			  	<div class="form_ctrl">
			    	<input name="hireDate" type="text" class="required"  maxlength="10" value="${obj.hireDate}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">离职时间：</label>
			  	<div class="form_ctrl">
			    	<input name="leaveDate" type="text" class="required"  maxlength="10" value="${obj.leaveDate}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">备注：</label>
			  	<div class="form_ctrl">
			    	<input name="remark" type="text" class="required"  maxlength="10" value="${obj.remark}"/>
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