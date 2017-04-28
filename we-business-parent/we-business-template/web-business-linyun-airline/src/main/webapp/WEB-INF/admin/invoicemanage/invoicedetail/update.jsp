<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<div class="panel_box">
	<div class="panel_content nopadding">
		<form method="post" action="${base}/admin/invoicemanage/update.html" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
			
			<div class="form_item">
				<label class="form_label">发票信息id：</label>
			  	<div class="form_ctrl">
			    	<input name="invoiceinfoid" type="text" class="required"  maxlength="10" value="${obj.invoiceinfoid}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">发票号：</label>
			  	<div class="form_ctrl">
			    	<input name="invoicenum" type="text" class="required"  maxlength="10" value="${obj.invoicenum}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">发票金额：</label>
			  	<div class="form_ctrl">
			    	<input name="invoicebalance" type="text" class="required"  maxlength="10" value="${obj.invoicebalance}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">发票图片url：</label>
			  	<div class="form_ctrl">
			    	<input name="invoiceurl" type="text" class="required"  maxlength="10" value="${obj.invoiceurl}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">发票图片名称：</label>
			  	<div class="form_ctrl">
			    	<input name="imagename" type="text" class="required"  maxlength="10" value="${obj.imagename}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">操作人：</label>
			  	<div class="form_ctrl">
			    	<input name="opid" type="text" class="required"  maxlength="10" value="${obj.opid}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">操作时间：</label>
			  	<div class="form_ctrl">
			    	<input name="optime" type="text" class="required"  maxlength="10" value="${obj.optime}"/>
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