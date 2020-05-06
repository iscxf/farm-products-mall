
var prefix = "/manager/order"
$(function() {
	load();
});

function load() {
	$('#exampleTable')
			.bootstrapTable(
					{
						method : 'get', // 服务器数据的请求方式 get or post
						url : prefix + "/list", // 服务器数据的加载地址
					//	showRefresh : true,
					//	showToggle : true,
					//	showColumns : true,
						iconSize : 'outline',
						toolbar : '#exampleToolbar',
						striped : true, // 设置为true会有隔行变色效果
						dataType : "json", // 服务器返回的数据类型
						pagination : true, // 设置为true会在底部显示分页条
						// queryParamsType : "limit",
						// //设置为limit则会发送符合RESTFull格式的参数
						singleSelect : false, // 设置为true将禁止多选
						// contentType : "application/x-www-form-urlencoded",
						// //发送到服务器的数据编码类型
						pageSize : 10, // 如果设置了分页，每页数据条数
						pageNumber : 1, // 如果设置了分布，首页页码
						//search : true, // 是否显示搜索框
						showColumns : false, // 是否显示内容下拉框（选择显示的列）
						sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
						queryParams : function(params) {
							return {
								//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
								limit: params.limit,
								offset:params.offset,
					            accountName:$('#searchName').val()
					           // username:$('#searchName').val()
							};
						},
						// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
						// queryParamsType = 'limit' ,返回参数必须包含
						// limit, offset, search, sort, order 否则, 需要包含:
						// pageSize, pageNumber, searchText, sortName,
						// sortOrder.
						// 返回false将会终止请求
						columns : [
								// {
								// 	checkbox : true
								// },
								// 								{
								// 	field : 'id',
								// 	title : ''
								// },
																{
									field : 'accountName',
									title : '购买客户'
								},
																{
									field : 'productName',
									title : '商品名称',
									formatter : function(value, row, index) {
										var html = '<a  target="_blank" href="/manager/product/open/' + row.productId + '">' + row.productName + '</a>'
                                        return html;
									}
								},
                            {
                                field : 'productTitle',
                                title : '商品标题'
                            },
																{
									field : 'price', 
									title : '商品价格' 
								},
																{
									field : 'quantity', 
									title : '数量' 
								},
																{
									field : 'orderAmount', 
									title : '订单总金额' 
								},
																{
									field : 'orderTime', 
									title : '下单时间' 
								},
																{
									field : 'status', 
									title : '状态' ,
									formatter : function(value, row, index) {
                                        if (value == '-2') {
                                            return '<span class="label label-default">已取消</span>';
                                        } else if (value == '-1') {
                                            return '<span class="label label-primary">待付款</span>';
                                        } else if (value == '0') {
											return '<span class="label label-primary">已付款待确认</span>';
										} else if (value == '1') {
                                            return '<span class="label label-warning">待收获</span>';
                                        } else if (value == '2') {
                                            return '<span class="label label-info">待用户收获</span>';
                                        }else if (value == '3') {
                                            return '<span class="label label-success">待收货</span>';
                                        }else if (value == '4') {
                                            return '<span class="label label-success">已完成</span>';
                                        }
									}
								},
																{
									field : 'shipName', 
									title : '收货人' 
								},
																{
									field : 'shipPhone', 
									title : '收货人联系电话' 
								},
																{
									field : 'shipAddress', 
									title : '收货地址' 
								},
																{
									field : 'remark', 
									title : '备注' 
								},
																{
									field : 'createTime', 
									title : '创建时间' 
								},
																{
									field : 'updateTime',
									title : '修改时间'
								},
																{
									title : '操作',
									field : 'id',
									align : 'center',
									formatter : function(value, row, index) {
										var e = '<a class="btn btn-primary btn-sm" href="#" mce_href="#" onclick="changeStatus(\'' + row.id + '\',1)">通过审核</a> ';
										var d = '<a class="btn btn-warning btn-sm " href="#" mce_href="#" onclick="changeStatus(\'' + row.id + '\',2)">通知收获</a> ';
										var g = '<a class="btn btn-success btn-sm" href="#" title="添加生长情况"  mce_href="#" onclick="addGrowth(\''
												+ row.id
												+ '\')">添加生长情况</a> ';
                                        var h = '<a class="btn btn-warning btn-sm " href="#" mce_href="#" onclick="setVideo(\'' + row.id + '\')">直播地址</a> ';
                                        if (row.status == "0") {
											return e;
										}else if (row.status == "1") {
                                            return g + d + h;
                                        }else {
                                            return "--";
										}
									}
								} ]
					});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}
function add() {
	layer.open({
		type : 2,
		title : '增加',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/add' // iframe的url
	});
}
function edit(id) {
	layer.open({
		type : 2,
		title : '编辑',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/edit/' + id // iframe的url
	});
}
function remove(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix+"/remove",
			type : "post",
			data : {
				'id' : id
			},
			success : function(r) {
				if (r.code==0) {
					layer.msg(r.msg);
					reLoad();
				}else{
					layer.msg(r.msg);
				}
			}
		});
	})
}

function changeStatus(orderId, status){
	var msg = '';
	if (status == 1){
        msg = '确定通过审核?';
	} else if (status == 2){
        msg = '确定通知收获?';
	}
    layer.confirm(msg, {
        btn : [ '确定', '取消' ]
    }, function() {
        $.ajax({
            url : prefix+"/changeStatus",
            type : "post",
            data : {
                'orderId' : orderId,
				'status' : status
            },
            success : function(r) {
                if (r.code==0) {
                    layer.msg(r.msg);
                    reLoad();
                }else{
                    layer.msg(r.msg);
                }
            }
        });
    })
}

function addGrowth(orderId){
    layer.open({
        type : 2,
        title : '编辑',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '800px', '520px' ],
        content : ' /manager/orderProductGrowth/add/' + orderId
    });

}

function setVideo(orderId){
    layer.open({
        type : 2,
        title : '视频',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '800px', '520px' ],
        content : ' /manager/order/video/add/' + orderId
    });
}


function batchRemove() {
	var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要删除的数据");
		return;
	}
	layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		var ids = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			ids[i] = row['id'];
		});
		$.ajax({
			type : 'POST',
			data : {
				"ids" : ids
			},
			url : prefix + '/batchRemove',
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	}, function() {

	});
}