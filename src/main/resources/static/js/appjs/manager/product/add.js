// $().ready(function() {
// 	validateRule();
// });
//
// $.validator.setDefaults({
// 	submitHandler : function() {
// 		save();
// 	}
// });

$().ready(function() {
    $('.summernote').summernote({
        height : '220px',
        lang : 'zh-CN',
        callbacks: {
            onImageUpload: function(files, editor, $editable) {
                sendFile(files);
            }
        }
    });
    validateRule();
});

$.validator.setDefaults({
    submitHandler : function() {
        save();
    }
});
function editpro(){
    $('.summernote').summernote({
        height : '220px',
        lang : 'zh-CN',
        callbacks: {
            onImageUpload: function(files, editor, $editable) {
                sendFile(files);
            }
        }
    });
}


function save() {
    var content_sn = $("#content_sn").summernote('code');
    if (content_sn == "<p><br></p>")
        $("#description").val();
    else
        $("#description").val(content_sn);
	$.ajax({
		cache : true,
		type : "POST",
		url : "/manager/product/save",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			name : {
				required : true
			}
		},
		messages : {
			name : {
				required : icon + "请输入姓名"
			}
		}
	})
}