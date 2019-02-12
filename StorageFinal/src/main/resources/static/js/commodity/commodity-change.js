$(function(){
    getCommMessage();
});

function getCommMessage(){
    var id = getcookie("commId");

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
        url:"http://"+ getIp() +":8080/comm/msg",
        async:false,
        dataType: "json",
        data: JSON.stringify(form),
        success :function(result){
            if (result.judge == 1){
                var comm = result.commdity;
                // 开始插入数据
                $("#chid").val(comm.id);
                $("#chname").val(comm.name);
                $("#chlocation").val(comm.location);
                $("#chprice").val(comm.price);
            }else{
                alert("修改错误!");
            }
        },
        error:function(){
            window.location.href="index.html"
        }
    });
}

function updateComm(){
    var id = $("#chid").val();
    var name = $("#chname").val();
    var location = $("#chlocation").val();
    var price = $("#chprice").val();

    var form = {
        "id" : id, "name": name, "location": location, "price": price
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
        url:"http://"+ getIp() +":8080/record/update",
        async:true,
        dataType: "json",
        data: JSON.stringify(form),
        success :function(result){
            if (result.judge == 1){
                window.location.href="commodity.html";
            }else{
                alert("修改错误!");
            }
        },
        error:function(){
            window.location.href="index.html"
        }
    });
}


