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