function fileUpload(){
    var formData = new FormData();
    formData.append('file', $('#excelFile')[0].files[0]);
    var token = getcookie("token");
    var username = getcookie("username");
    $.ajax({
        headers: {
            "token": token,
            "username": username
        },
        url: "http://"+ getIp() +":8080/comm/upload",
        type: 'POST',
        cache: true,
        data: formData,
        processData: false,
        contentType: false
    }).done(function(res) {
    }).fail(function(res) {
    });
    window.location.href="commodity.html";
}

function CommodityUploadFile(){
    //创建表单
    var formData = new FormData();
    var file = $("#excelFile").val();
    formData.append("file",$("#excelFile")[0].files[0]);

    if ($('#excelFile').val() == "") {
        alert("请选择所要上传的文件");
    }else{
        var index = file.lastIndexOf(".");
        if(index < 0 ){
            alert("上传的文件格式不正确，请选择Excel文件(*.xls)！");
        }else{
            var ext = file.substring(index + 1, file.length);
            if(ext == "xls" || ext == "xlsx"){
                fileUpload();
            }else{
                alert("上传的文件格式不正确，请选择Excel文件(*.xls)！");
            }
        }
    }
}


