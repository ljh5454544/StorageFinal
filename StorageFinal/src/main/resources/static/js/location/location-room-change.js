$(function(){
    getRoomMessage();
});

function getRoomMessage(){
    var id = getcookie("roomId");
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
        url:"http://"+ getIp() +":8080/room/msg",
        async:true,
        dataType: "json",
        data: JSON.stringify(form),
        success :function(result){
            if (result.judge == 1){
                var room = result.room;
                // 开始插入数据
                $("#chid").val(room.id);
                $("#chname").val(room.name);
                $("#chremark").val(room.remark);
            }else{
                alert("修改错误!");
            }
        },
        error:function(){
            window.location.href="index.html"
        }
    });
}

function updateRoom(){
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
        url:"http://"+ getIp() +":8080/room/update",
        async:true,
        dataType: "json",
        data: JSON.stringify(form),
        success :function(result){
            if (result.judge == 1){
                window.location.href="location.html";
            }else{
                alert("修改错误!");
            }
        },
        error:function(){
            window.location.href="index.html"
        }
    });
}


