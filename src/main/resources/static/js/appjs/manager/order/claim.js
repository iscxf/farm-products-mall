
function saveClaimOrder() {
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