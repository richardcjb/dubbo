<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<div class="panel_box">
	<div class="panel_content nopadding">
		<form method="post" action="${base}/admin/invoice/update.html" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
			
			<div class="form_item">
				<label class="form_label">发票项：</label>
			  	<div class="form_ctrl">
			    	<input name="item" type="text" class="required"  maxlength="10" value="${obj.item}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">描述：</label>
			  	<div class="form_ctrl">
			    	<input name="description" type="text" class="required"  maxlength="10" value="${obj.description}"/>
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