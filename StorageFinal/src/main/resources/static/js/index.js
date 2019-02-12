function submitForm(){
    var username = $("#username").val();
    var password = $("#password").val();
    var form = {
        "username": username, "password": password
    };

    $.ajax({
        headers: {
            "token": "1234"
        },
        type:"post",
        contentType: "application/json;charset=UTF-8",
        url:"http://"+ getIp() +":8080/user/login",
        async:false,
        dataType: "json",
        data: JSON.stringify(form),
        success :function(result){
            if (result.judge == 1){
                setcookie("username", result.username, 3);
                setcookie("userId", result.id, 3);
                setcookie("token", result.token, 1);
                setcookie("role", result.role, 3);
                setTimeout(function(){window.location.href="user-list.html"},100);
                window.event.returnValue=false
            }else{
                alert("账户或密码错误!");
                window.location.reload();
                window.event.returnValue=false
            }
        }
    });
    return false;
}

$(function(){
    var username = getcookie("username");
    var password = getcookie("password");

    if (username != null && password != null){
        $("#username").val(username);
        $("#password").val(password);
    }
})

function save(){
    if ($('#checkbox').attr('checked')) {
        var username = $("#username").val();
        var password = $("#password").val();
        setcookie("username", username, 3);
        setcookie("password", password, 3);
     }else{
        removecookie("username");
        removecookie("password");
    }
}