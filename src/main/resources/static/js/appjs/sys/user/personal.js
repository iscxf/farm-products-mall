var prefix = "/sys/user";


function change_title(data) {
    var text = $(data).text();
    $("#userCenterTitle").html(text);
}

/**
 * 基本信息提交
 */
$("#base_save").click(function () {

    // var provicneSelectStr = $("#schoolprovince").find("option:selected").text();
    // // $("#schoolprovince").val(provicneSelectStr);
    // var provicneSelectStr2 = $("#schoolprovince").find("option[value="+provicneSelectStr+"]").attr("selected",true);
    // $("#schoolprovince option[value=provicneSelectStr2]").text(provicneSelectStr);
    //
    // var citySelectStr = $("#schoolcity").find("option:selected").text();
    // // $("#schoolcity").val(citySelectStr);
    // var citySelectStr2 = $("#schoolcity").val();
    // $("#schoolcity option[value = citySelectStr2 ]").text(citySelectStr);
    // $("#schoolcity").val(citySelectStr);
    //
    // var schoolUlStr = $("#school").find("option:selected").text();
    // $("#school").val(schoolUlStr);
    // alert($("#schoolprovince").val()+$("#schoolcity").val());
    // if($("#basicInfoForm").valid()){
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
