var prefix = "/sys/user";

$(function(){
    load();
    validateRule();
});

function load() {
    $('#myOrderTable')
        .bootstrapTable(
            {
                method : 'get', // 服务器数据的请求方式 get or post
                url : "/manager/order/userOrder", // 服务器数据的加载地址
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
                        offset:params.offset
                        // name:$('#searchName').val(),
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
                    {
                        field : 'productName',
                        title : '商品名称',
                        formatter : function(value, row, index) {
                            var html = '<a  target="_blank" href="/manager/product/open/' + row.id + '">' + row.productName + '</a>'
                            return html;
                        }
                    },
                    {
                        field : 'productTitle',
                        title : '商品标题'
                    },
                    {
                        field : 'price',
                        title : '单价'
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
                        field : 'shipName',
                        title : '收货人'
                    },
                    {
                        field : 'shipPhone',
                        title : '收货人电话'
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
                        field : 'orderTime',
                        title : '下单时间'
                    },
                    {
                        field : 'status',
                        title : '状态',
                        formatter : function(value, row, index) {
                            if (value == '0') {
                                return '<span class="label label-primary">已付款待确认</span>';
                            } else if (value == '1') {
                                return '<span class="label label-warning">待收获</span>';
                            } else if (value == '2') {
                                return '<span class="label label-info">待收获确认</span>';
                            }else if (value == '3') {
                                return '<span class="label label-success">待收货</span>';
                            }else if (value == '4') {
                                return '<span class="label label-success">已完成</span>';
                            }
                        }
                    },
                    {
                        title : '操作',
                        field : 'id',
                        align : 'center',
                        formatter : function(value, row, index) {
                            var e = '<a class="btn btn-primary btn-sm" href="#" mce_href="#" title="编辑" onclick="getGrowth(\''
                                + row.id + '\')">查看订单产品状况</a> ';
                            var f = '<a class="btn btn-success btn-sm" href="#" title="收获"  mce_href="#" onclick="harvest(\''
                                + row.id
                                + '\')">收获</a> ';
                            var g = '<a class="btn btn-success btn-sm" href="#" title="确认收货"  mce_href="#" onclick="confirmReceipt(\''
                                + row.id
                                + '\')">确认收货</a> ';
                            if (row.status == "1") {
                                return e;
                            }else if(row.status == "2"){
                                return e + f;
                            }else if(row.status == "3"){
                                return e + g;
                            }else{
                                return "--";
                            }
                        }
                    } ]
            });
}
function reLoad() {
    $('#myOrderTable').bootstrapTable('refresh');
}

function change_title(data) {
    var text = $(data).text();
    $("#userCenterTitle").html(text);
    // if (data == "我的订单"){
    //     load();
    // }
}

function getGrowth(orderId){
    layer.open({
        type : 2,
        title : '生长情况信息',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '900px', '520px' ],
        content : '/manager/orderProductGrowth/' +  orderId
    });
}

function harvest(orderId){
    layer.confirm('确定要收获这个订单吗？', {
        btn : [ '确定', '取消' ]
    }, function() {
        $.ajax({
            url : "/manager/order/changeStatus",
            type : "post",
            data : {
                'orderId' : orderId,
                'status' : 3
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

function confirmReceipt(orderId){
    layer.confirm('确定收货吗？', {
        btn : [ '确定', '取消' ]
    }, function() {
        $.ajax({
            url : "/manager/order/changeStatus",
            type : "post",
            data : {
                'orderId' : orderId,
                'status' : 4
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

/**
 * 基本信息提交
 */
$("#base_save").click(function () {
            $.ajax({
                cache : true,
                type : "POST",
                url :"/sys/user/updatePersonal",
                data : $('#basicInfoForm').serialize(),
                async : false,
                error : function(request) {
                    laryer.alert("Connection error");
                },
                success : function(data) {
                    if (data.code == 0) {
                        parent.layer.msg("更新成功");
                    } else {
                        parent.layer.alert(data.msg)
                    }
                }
            });
        // }

});
$("#pwd_save").click(function () {
    if($("#modifyPwd").validate()){
        $.ajax({
            cache : true,
            type : "POST",
            url :"/sys/user/resetPwd",
            data : $('#modifyPwd').serialize(),
            async : false,
            error : function(request) {
                parent.laryer.alert("Connection error");
            },
            success : function(data) {
                if (data.code == 0) {
                    parent.layer.alert("更新密码成功");
                    $("#photo_info").click();
                } else {
                    parent.layer.alert(data.msg)
                }
            }
        });
    }
});

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#modifyPwd").validate({
        rules: {
            pwdOld: {
                required: true,
                minlength: 6
            },
            pwdNew: {
                required: true,
                minlength: 6,
            },
            confirm_password: {
                required: true,
                minlength: 6,
                equalTo: "#pwdNew"
            }
        },
        messages: {

            pwdOld: {
                required: icon + "请输入您的密码",
                minlength: icon + "密码必须6个字符以上"
            },
            pwdNew: {
                required: icon + "请输入新密码",
                minlength: icon + "密码必须6个字符以上",
            },
            confirm_password: {
                required: icon + "请再次输入新密码",
                minlength: icon + "密码必须6个字符以上",
                equalTo: icon + "两次输入的密码不一致"
            }
        }
    })
}
