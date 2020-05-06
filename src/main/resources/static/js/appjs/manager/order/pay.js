
function payForOrder() {
    layer.confirm('确定付款', {
        btn : [ '付款', '取消' ]
    }, function() {
        var uuid = $("#uuid").val();
        $.ajax({
            cache : true,
            type : "GET",
            url : "/manager/order/payForOrder/" + uuid,
            async : false,
            error : function(request) {
                parent.layer.alert("Connection error");
            },
            success : function(data) {
                if (data.code == 0) {
                    parent.layer.msg("付款成功");
                } else {
                    parent.layer.alert(data.msg)
                }

            }
        });
    })
}

