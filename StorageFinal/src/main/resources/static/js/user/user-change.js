$(function(){
    getUserMessage();
});

function getUserMessage(){
    var id = getcookie("userId");
    var form = {
        "id" : id
    };

    var token = getcookie("token");
    var username = getcookie("username");
    $.ajax({
        headers: {
            "token": token,
            "username": username
        },
        type:"post",
        contentType: "application/json;charset=UTF-8",
        url:"http://"+ getIp() +":8080/user/msg",
        async:true,
        dataType: "json",
        data: JSON.stringify(form),
        success :function(result){
            if (result.judge == 1){
                var user = result.user;
                // 开始插入数据
                $("#chtelephone").val(user.telephone);
                $("#chmail").val(user.email);
                $("#chremark").val(user.remark);
                $("#addid").val(user.id);
            }else{
                alert("修改错误!");
            }
        },
        error:function(){
            window.location.href="index.html"
        }
    });
}

function updateUser(){
    var roleId = getcookie("role");
    if (roleId === "3" || roleId === "2") {
        alert("对不起, 您的权限不足!");
        return
    }


    var id = $("#addid").val();
    var telephone = $("#chtelephone").val();
    var email = $("#chmail").val();
    var remark = $("#chremark").val();
    var roleId = $("#changeuser").val();

    var form = {
        "id" : id,
        "telephone": telephone, "email": email, "remark": remark, "roleId": roleId
    };

    var token = getcookie("token");
    var username = getcookie("username");
    $.ajax({
        headers: {
            "token": token,
            "username": username
        },
        type:"post",
        contentType: "application/json;charset=UTF-8",
        url:"http://"+ getIp() +":8080/user/update",
        async:true,
        dataType: "json",
        data: JSON.stringify(form),
        success :function(result){
            if (result.judge == 1){
                window.location.href="user-list.html";
            }else{
                alert("修改错误!");
            }
        },
        error:function(){
            window.location.href="index.html"
        }
    });
}

function updataPerson(){
    var addid = $("#addid").val();
    var telephone = $("#chtelephone").val();
    var password = $("#chpassword").val();
    var email = $("#chmail").val();
    var remark = $("#chremark").val();
    var roleId = $("#changeuser").val();

    var form = {
        "id": addid, "password": password, "telephone": telephone,
        "email": email, "remark": remark, "roleId": roleId
    };

    var token = getcookie("token");
    var username = getcookie("username");
    $.ajax({
        headers: {
            "token": token,
            "username": username
        },
        type:"post",
        contentType: "application/json;charset=UTF-8",
        url:"http://"+ getIp() +":8080/user/update",
        async:true,
        dataType: "json",
        data: JSON.stringify(form),
        success :function(result){
            if (result.judge == 1){
                window.location.href="user-list.html";
            }else{
                alert("修改错误!");
            }
        },
        error:function(){
            window.location.href="index.html"
        }
    });
}