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
            url:"http://"+ getIp() +":8080/room/all",
            async:true,
            dataType: "json",
            success :function(result){
                var str = "<option value='#'>--请选择库区--</option>";
                $.each(result, function(index, item) {
                    str +="<option value="+result[index].id+">"+ result[index].name+"</option>";
                });
                document.getElementById("adroom").innerHTML=str;
            },
            error:function(){
                window.location.href="index.html"
            }
        });
    }
);

function addShelf(){
    var name = $("#adname").val();
    var roomId = $("#adroom").val();
    var remark = $("#adremark").val();

    var form = {
        "name" : name, "remark" : remark, "roomId" : roomId
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
        url:"http://"+ getIp() +":8080/shelf/add",
        async:true,
        dataType: "json",
        data: JSON.stringify(form),
        success :function(result){
            if (result.judge == 1){
                setcookie("roomId", roomId, 1);
                window.location.href="location-shelf-list.html";
            }else{
                alert("添加错误!");
            }
        },
        error:function(){
            window.location.href="index.html"
        }
    });
}