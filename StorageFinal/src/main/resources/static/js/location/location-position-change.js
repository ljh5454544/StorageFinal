$(function(){
    getPositionMessage();
});

function getPositionMessage(){
    var id = getcookie("positionId");
    var form = {
        "id" : id
    }
    var token = getcookie("token");
    var username = getcookie("username");
    $.ajax({
        headers: {
            "token": token,
            "username": username
        },
        type:"post",
        contentType: "application/json;charset=UTF-8",
        url:"http://"+ getIp() +":8080/position/msg",
        async:true,
        dataType: "json",
        data: JSON.stringify(form),
        success :function(result){
            if (result.judge == 1){
                var position = result.position;
                // 开始插入数据
                $("#chid").val(position.id);
                $("#chname").val(position.name);
                $("#chremark").val(position.remark);
            }else{
                alert("修改错误!");
            }
        },
        error:function(){
            window.location.href="index.html"
        }
    });
}

function updatePosition(){
    var id = $("#chid").val();
    var name = $("#chname").val();
    var remark = $("#chremark").val();
    var form = {
        "id" : id, "name": name, "remark": remark
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
        url:"http://"+ getIp() +":8080/position/update",
        async:true,
        dataType: "json",
        data: JSON.stringify(form),
        success :function(result){
            if (result.judge == 1){
                window.location.href="location-position-list.html";
            }else{
                alert("修改错误!");
            }
        },
        error:function(){
            window.location.href="index.html"
        }
    });
}


