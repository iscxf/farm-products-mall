
function enableSubmit(bool){
    if(bool)$("#mysubmit").removeAttr("disabled");
    else $("#mysubmit").attr("disabled","disabled");
}

function v_submitbutton(){
    for(var f in flags) if(!flags[f]) {
        enableSubmit(false);
        return;
    }
    enableSubmit(true);
}


var flags = [false,false,false];

function lineState(name,state,msg){
    if(state=="none"){$("#line_"+name+" .input div").attr("class","none"); return;}
    if(state=="corect"){$("#line_"+name+" .input div").attr("class","corect");return;}
    $("#line_"+name+" .input span").text(msg);
    $("#line_"+name+" .input div").attr("class","error");
}

function saveRegister() {
        $.ajax({
            type: "POST",
            url: "/sys/user/register/merchant",
            data: $('#registerForm').serialize(),
            success: function (r) {
                parent.location.href = '/index';
                // if (r == "success") {
                //     var index = layer.load(1, {
                //         shade: [0.1,'#fff'] //0.1透明度的白色背景
                //     });
                //     parent.location.href = '/question/index';
                // } else {
                //     layer.msg(r);
                // }
            },
        });

}

$('#name').blur(function () {
    var userName = $("#name").val();
    $.ajax({
        url: '/sys/user/register/check',
        type: 'post',
        dataType: 'text',
        data: {userName:userName},
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            //这个error函数调试时非常有用，如果解析不正确，将会弹出错误框
            alert(XMLHttpRequest.responseText);
            alert(XMLHttpRequest.status);
            alert(XMLHttpRequest.readyState);
            alert(textStatus); // parser error;
        },
        success:name_ajax
    });

    function name_ajax(text){
        if(text=='no'){
            lineState("name","error","此用户名已存在！");
            flags[0]=false;
        }
    }
});

// $('#storeName').blur(function () {
//     var userName = $("#name").val();
//     $.ajax({
//         url: '/sys/user/register/storeName/check',
//         type: 'post',
//         dataType: 'text',
//         data: {userName:userName},
//         error : function(XMLHttpRequest, textStatus, errorThrown) {
//             //这个error函数调试时非常有用，如果解析不正确，将会弹出错误框
//             alert(XMLHttpRequest.responseText);
//             alert(XMLHttpRequest.status);
//             alert(XMLHttpRequest.readyState);
//             alert(textStatus); // parser error;
//         },
//         success:name_ajax
//     });
//
//     function name_ajax(text){
//         if(text=='no'){
//             lineState("name","error","此用户名已存在！");
//             flags[0]=false;
//         }
//     }
// });

function v_name(){
    var name = $("#name").val();
    if(name.length==0) {
        lineState("name","error","不得为空");
        flags[0]=false;
    }else{
        if(name.length>16) {
            lineState("name","error","必须少于16个字符");
            flags[0]=false;
        }else{
            lineState("name","corect","");
            flags[0] = true;
        }
    }
    v_submitbutton();
}

function v_password(){
    var password = $("#password").val();
    if(password.length<6) {
        lineState("password","error","必须多于或等于6个字符");
        flags[1]=false;
    }else{
        if(password.length>16){
            lineState("password","error","必须少于或等于16个字符");
            flags[1]=false;
        }else{
            lineState("password","corect","");
            flags[1] = true;
        }
    }
    v_repeat();
    v_submitbutton();
}

function v_repeat(){
    if(!flags[1]) {
        lineState("repeat","none","");
        return;
    }
    if($("#password").val()!=$("#repeat").val()) {
        lineState("repeat","error","密码不一致");
        flags[2]=false;
    }else{
        lineState("repeat","corect","");
        flags[2] = true;
    }
    v_submitbutton();
}

