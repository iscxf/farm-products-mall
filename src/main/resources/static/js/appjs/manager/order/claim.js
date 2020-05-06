function claimProduct(product) {

    var str = '?name=' + product.name + '&id=' + product.id + '&stock=' + product.stock;
    // console.log('str : ' + str);
    layer.open({
        type: 2,
        skin: 'layui-layer-demo', //样式类名
        // closeBtn: 1, //显示关闭按钮
        anim: 2,
        shadeClose: true, //开启遮罩关闭
        area: ['620px', '240px'],
        content: "/js/appjs/manager/order/claim.html" + str
    });
}

function saveClaimOrder() {
    layer.confirm('提交并付款', {
        btn : [ '付款', '取消' ]
    }, function() {
        var quantity = $("#quantityInput").val();
        $("#quantity").val(quantity);
        $.ajax({
            cache : true,
            type : "POST",
            url : "/manager/order/save",
            data : $('#orderForm').serialize(),
            async : false,
            error : function(request) {
                parent.layer.alert("Connection error");
            },
            success : function(data) {
                if (data.code == 0) {
                    parent.layer.msg("操作成功" + data);
                    console.log(data);
                    pay(data.uuid);
                } else {
                    parent.layer.alert(data.msg)
                }

            }
        });
    })
}

function pay(uuid) {
    layer.open({
        type : 2,
        title : '付款',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '400px', '420px' ],
        content :  '/manager/order/payCode/' + uuid // iframe的url
    });
}