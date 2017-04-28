<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>详情</title>
	<link rel="stylesheet" href="${base }/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base }/public/dist/css/AdminLTE.css">
  <link rel="stylesheet" href="${base }/public/dist/css/bookingOrderDetail.css"><!--本页面styleFlie-->
</head>
<body>
	<div class="modal-top">
    <div class="modal-header boderButt">
            <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow();">取消</button>
            <button type="button" class="btn btn-primary right btn-sm btnEdit">编辑</button>
            <h4>详情</h4>
          </div>
          <div class="modal-body">
            <div class="tab-content backcard">
                <table class="PNRtable">
                  <tr>
                    <td>PNR：</td>
                    <td><input type="text" disabled="disabled" class="form-control input-sm" value="${obj.pnrinfo.PNR }"></td>
                    <td>登录帐号：</td>
                    <td>
                    	<select disabled="disabled" class="form-control input-sm">
                    		<option value="">请选择</option>
                    		<c:forEach items="${obj.loginselect }" var="one">
	                    		<c:choose>
	                        		<c:when test="${obj.pnrinfo.loginid eq one.id}">
	                        			<option value="${one.id }" selected="selected">${one.loginNumName }</option>
	                        		</c:when>
	                        		<c:otherwise>
			                    		<option value="${one.id }">${one.loginNumName }</option>
	                        		</c:otherwise>
	                        	</c:choose>
	                    	</c:forEach>
                    	</select>
                    </td>
                    <td>人数：</td>
                    <td><input type="text" disabled="disabled" class="form-control input-sm" disabled="disabled" value="${obj.pnrinfo.peoplecount }"></td></td>
                    <td>币种：</td>
                    <td>
                      <select disabled="disabled" class="form-control input-sm" >
                      	<option value="">请选择</option>
                        <c:forEach items="${obj.bzcode }" var="one"> 
                        	<c:choose>
                        		<c:when test="${obj.pnrinfo.currency eq one.dictCode }">
				                     <option value="${one.dictCode }" selected="selected">${one.dictCode }</option>
                        		</c:when>
                        		<c:otherwise>
				                     <option value="${one.dictCode }">${one.dictCode }</option>
                        		</c:otherwise>
                        	</c:choose>
                     	</c:forEach>
                      </select>
                      </select>
                    </td>
                  </tr>
                  <tr>
                    <td>成本单价：</td>
                    <td><input type="text" disabled="disabled" class="form-control input-sm" value="<fmt:formatNumber type="number" value="${obj.pnrinfo.costprice }" pattern="0.00" maxFractionDigits="2"/>"></td>
                    <td>成本总价：</td>
                    <td><input type="text" disabled="disabled" class="form-control input-sm" value="<fmt:formatNumber type="number" value="${obj.pnrinfo.costpricesum }" pattern="0.00" maxFractionDigits="2"/>"></td></td>
                    <td>销售单价：</td>
                    <td><input type="text" disabled="disabled" class="form-control input-sm" value="<fmt:formatNumber type="number" value="${obj.pnrinfo.salesprice }" pattern="0.00" maxFractionDigits="2"/>"></td></td>
                    <td>销售总价：</td>
                    <td><input type="text" disabled="disabled" class="form-control input-sm" value="<fmt:formatNumber type="number" value="${obj.pnrinfo.salespricesum }" pattern="0.00" maxFractionDigits="2"/>"></td></td>
                  </tr>
                </table>
                <div class="touristsInfo">
                  <ul>
                  	<c:forEach items="${obj.visitors }" var="one"> 
                       <li onclick="viewVisitorInfo(${one.id});">${one.visitorname }</li>
                   	</c:forEach>
                    <!-- <li class="actionInfo">布鲁斯·李</li> -->
                  </ul>
                  <div class="infoCentext">
                    <div id="visitorinfo" style="line-height:50px;"></div>    
                  </div>
                </div>

            </div>
          </div>
	</div>
   <!--JS 文件-->
	<script src="${base }/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base }/public/bootstrap/js/bootstrap.min.js"></script>

	<script src="${base }/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
	<script src="${base }/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
	<script src="${base }/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
    <script src="${base}/common/js/layer/layer.js"></script>
  <script type="text/javascript">

//关闭窗口
  function closewindow(){
		var index = parent.parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
     $(function(){
        /* $('.touristsInfo ul li').click(function(){
            $(this).addClass('actionInfo').siblings().removeClass('actionInfo');
            var index = $('.touristsInfo ul li').index(this);
            $('.infoCentext > div').eq(index).show().siblings().hide();
        }); */

        //点击 编辑 弹框
        $('.btnEdit').click(function(){
        	location.href = '${base}/admin/inland/editPnr.html?id=${obj.pnrinfo.id }';
            /* layer.open({
                type: 2,
                title:false,
                skin: false, //加上边框
                closeBtn:false,//默认 右上角关闭按钮 是否显示
                shadeClose:true,
                area: ['830px', '440px'],
                content: '${base}/admin/inland/editPnr.html?id=${obj.pnrinfo.id }'
              }); */
        });
     });
     //显示游客的详细信息
     function viewVisitorInfo(id){
    	 $.ajax({ 
 			type: 'POST', 
 			data: {id:id}, 
 			dataType:'json',
 			url: '${base}/admin/inland/showVisitorInfo.html',
             success: function (data) { 
             	var result = '';
             	result += '姓名：'+data.visitorname+'&nbsp;&nbsp;<br>性别：'
             				+ data.gender + '<br>出生日期：' 
             				+ data.birthday + '<br>护照号：'
             				+data.cardnum + '<br>有效期至：'+ data.validuntil;
             	$('#visitorinfo').html(result);
             },
             error: function (xhr) {
           	
             } 
          });
     }
  </script>
</body>
</html>	