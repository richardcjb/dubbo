<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/public/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>

<c:set var="url" value="${base}/admin/authority/companyfunction" />
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Main content -->
    <section class="content">
    <div class="row row-top">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header">
              <div class="form-group row form-right">
             
                
                <div class="col-md-3"><!--公司名称/负责人/电话 搜索框-->
                  <input type="text" name="companyName" id="companyName" class="form-control" placeholder="公司名称/负责人/电话" onkeypress="onkeyEnter();">
                </div>
                <div class="col-md-2 col-padding"><!--搜索 恢复默认 按钮-->
                  <button id="searchBtn" type="button" class="btn btn-primary btn-sm">搜索</button>
                </div>
              </div>
            </div>
            <h4 class="padLeft">全部公司：${obj.totalcompany }　上游公司：${obj.upconpany } 　代理商：${obj.agent }</h4>
            <!-- /.box-header -->
            <div class="box-body">
              <table id="datatableAuthority" class="table table-bordered table-hover" style="width: 100%;">
                <thead>
                <tr>
                  <th>公司名称</th>
                  <th>联系人</th>
                  <th>联系方式</th>
                  <th>员工人数</th>
                  <th>公司类型</th>
                  <th>操作</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
              </table>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->
        </div>
        <!-- /.col -->
      </div>
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->

  <%@include file="/WEB-INF/public/footer.jsp"%>
</div>
<!-- ./wrapper -->

<!--弹框 div-->
<div class="modal fade Mymodal-lg" role="dialog" tabindex="-1" aria-labelledby="myLargeModalLabel" aria-hidden="true" id="addTabs" style="width:auto;height:1000px;"> 
      <div class="modal-dialog modal-lg">
          <div class="modal-content">
              <div class="modal-header">
              </div>
                <div class="modal-body">
                </div>
            </div>
        </div>
    </div>

<!-- page script -->
<script>
var datatable;
function initDatatable() {
    datatable = $('#datatableAuthority').DataTable({
    	"searching":false,
    	"bLengthChange": false,
        "processing": true,
        "stripeClasses": [ 'strip1','strip2' ],//斑马线
        "serverSide": true,
        "language": {
            "url": "${base}/public/plugins/datatables/cn.json"
        },
        "ajax": {
            "url": "${base}/admin/authority/companyfunction/listData.html",
            "type": "post",
            "data": function (d) {
            	
            }
        },
        "columns": [
                    {"data": "comname", "bSortable": false},
                    {"data": "connect", "bSortable": false},
                    {"data": "mobile", "bSortable": false},
                    {"data": "renshu", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var s = '';
                    		if(row.renshu <= '0'){
                    			s = '0';
                    		}else{
                    			s = row.renshu;
                    		}
                            return s
                        }
                    },
                    {"data": "comtype", "bSortable": false}
            ],
        columnDefs: [{
            //   指定第一列，从0开始，0表示第一列，1表示第二列……
            targets: 5,
            render: function(data, type, row, meta) {
                return '<a style="cursor:pointer;" onclick="edit('+row.id+');">权限设置</a>'
            }
        }]
    });
}

	$("#searchBtn").on('click', function () {
		var companyName = $("#companyName").val();
	    var param = {
	        "companyName": companyName
	    };
	    datatable.settings()[0].ajax.data = param;
	    datatable.ajax.reload();
	});

$(function () {
    initDatatable();
});
   function add(){
      layer.open({
    	    type: 2,
    	    title: false,
    	    closeBtn:false,
    	    fix: false,
    	    maxmin: false,
    	    shadeClose: false,
    	    area: ['600px', '500px'],
    	    content: '${url}/add.html'
    	    
    	  });
  }
  
  function edit(id){
	  layer.open({
  	    type: 2,
  	    title: false,
  	  	closeBtn:false,
  	    fix: false,
  	    maxmin: false,
  	    shadeClose: false,
  	    area: ['800px', '500px'],
  	    content: '/admin/authority/companyfunction/companyUpdate.html?id='+id
  	  });
  }
  function userlist(id){
	  layer.open({
  	    type: 2,
  	    title: false,
  	  	closeBtn:false,
  	    fix: false,
  	    maxmin: false,
  	    shadeClose: false,
  	    area: ['900px', '600px'],
  	    content: '${url}/userList.html?id='+id
  	    
  	  });
  }
  function onkeyEnter(){
		 if(event.keyCode==13){
			 $("#searchBtn").click();
		 }
	}
  function successCallback(id){
	  datatable.ajax.reload(null,false);
	  if(id == '1'){
		  layer.msg("添加成功",{time:2000});
	  }else if(id == '2'){
		  layer.msg("权限分配成功",{time:2000});
	  }else if(id == '3'){
		  layer.msg("删除成功",{time:2000});
	  }
  }
</script>

