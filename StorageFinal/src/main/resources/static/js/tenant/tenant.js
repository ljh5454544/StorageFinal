function addTenant(){
    var name = $("#addname").val();
    var agent = $("#addagent").val();
    var location = $("#addlocation").val();
    var telphone = $("#addtelphone").val();
    var remark = $("#addremark").val();

    var form = {
        "name": name, "agent": agent, "location": location, "telphone": telphone, "remark": remark
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
        url:"http://"+ getIp() +":8080/tenant/add",
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