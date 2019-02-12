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

function getShelf(){
    var token = getcookie("token");
    var username = getcookie("username");
    $.ajax({
        headers: {
            "token": token,
            "username": username
        },
        type:"get",
        url:"http://"+ getIp() +":8080/shelf/all",
        async:true,
        dataType: "json",
        success :function(result){
            var str = "<option value='#'>--请选择货架--</option>";
            $.each(result, function(index, item) {
                str +="<option value="+result[index].id+">"+ result[index].name+"</option>";
            });
            document.getElementById("adshelf").innerHTML=str;
        },
        error:function(){
            window.location.href="index.html"
        }
    });

}


function addPosition(){
    var name = $("#adname").val();
    var shelfId = $("#adshelf").val();
    var remark = $("#adremark").val();

    var form = {
        "name" : name, "remark" : remark, "shelfId" : shelfId
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
        url:"http://"+ getIp() +":8080/position/add",
        async:true,
        dataType: "json",
        data: JSON.stringify(form),
        success :function(result){
            if (result.judge == 1){
                setcookie("shelfId", shelfId, 1);
                window.location.href="location-position-list.html";
            }else{
                alert("添加错误!");
            }
        },
        error:function(){
            window.location.href="index.html"
        }
    });
}