$(function(){
    getShelfMessage();
});

function getShelfMessage(){
    var id = getcookie("shelfId");
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
        url:"http://"+ getIp() +":8080/shelf/msg",
        async:true,
        dataType: "json",
        data: JSON.stringify(form),
        success :function(result){
            if (result.judge == 1){
                var shelf = result.shelf;
                // 开始插入数据
                $("#chid").val(shelf.id);
                $("#chname").val(shelf.name);
                $("#chremark").val(shelf.remark);
            }else{
                alert("修改错误!");
            }
        },
        error:function(){
            window.location.href="index.html"
        }
    });
}

function updateShelf(){
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
        url:"http://"+ getIp() +":8080/shelf/update",
        async:true,
        dataType: "json",
        data: JSON.stringify(form),
        success :function(result){
            if (result.judge == 1){
                window.location.href="location-shelf-list.html";
            }else{
                alert("修改错误!");
            }
        },
        error:function(){
            window.location.href="index.html"
        }
    });
}


