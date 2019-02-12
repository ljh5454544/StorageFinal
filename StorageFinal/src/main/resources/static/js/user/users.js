$(document).ready(
    function(){
        var token = getcookie("token");
        var username = getcookie("username");
        $.ajax({
            headers: {
                "token": token,
                "username": username
            },
            type:"get",
            url:"http://"+ getIp() +":8080/role/all",
            async:true,
            dataType: "json",
            success :function(result){
                var str = "";
                $.each(result, function(index, item) {
                    str +="<option value="+result[index].id+">"+ result[index].name+"</option>";
                });
                document.getElementById("adduser").innerHTML=str;
                document.getElementById("changeuser").innerHTML=str;
            },
            error:function(){
                window.location.href="index.html"
            }
        });
    }
);

function addUser(){
    var roleId = getcookie("role");
    if (roleId === "3" || roleId === "2") {
        alert("对不起, 您的权限不足!");
        return
    }

    var username = $("#addusername").val();
    var password = $("#addpassword").val();
    var telephone = $("#addtelephone").val();
    var email = $("#addmail").val();
    var remark = $("#addremark").val();
    var roleId = $("#adduser").val();

    // 正则匹配 email
    var re = /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/;
    if ( !re.test(email)) {
        alert("请输入正确的邮箱!");
        return
    }

    var form = {
        "username": username, "password": password, "telephone": telephone,
        "remark": remark, "roleId": roleId, "email": email
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
        url:"http://"+ getIp() +":8080/user/add",
        async:true,
        dataType: "json",
        data: JSON.stringify(form),
        success :function(result){
            if (result.judge == 1){
                location.reload();
            }else{
                alert("添加错误!");
            }
        },
        error:function(){
            window.location.href="index.html"
        }
    });
}

function changeUser(number){
    if (number == -1){
        window.location.href="user-person.html";
    }else{
        setcookie("userId", number, 1);
        window.location.href="user-change.html";
    }
}