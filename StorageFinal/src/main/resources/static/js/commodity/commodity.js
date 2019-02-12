function addCommodity(){
    var name = $("#addconame").val();
    var location = $("#addcolocation").val();
    var price = $("#addcoprice").val();

    var form = {
        "name": name, "location": location, "price": price
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
        url:"http://" + getIp() +":8080/comm/add",
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