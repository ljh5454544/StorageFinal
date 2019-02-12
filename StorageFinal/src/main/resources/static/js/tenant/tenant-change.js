$(function(){
    getTenantMessage();
});

function getTenantMessage(){
    var id = getcookie("tenantId");

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
        url:"http://"+ getIp() +":8080/tenant/msg",
        async:true,
        dataType: "json",
        data: JSON.stringify(form),
        success :function(result){
            if (result.judge == 1){
                var tenant = result.tenant;
                // 开始插入数据
                $("#chid").val(tenant.id);
                $("#chname").val(tenant.name);
                $("#chagent").val(tenant.agent);
                $("#chlocation").val(tenant.location);
                $("#chtelphone").val(tenant.telphone);
                $("#chremark").val(tenant.remark);

            }else{
                alert("修改错误!");

            }
            return false;
        },
        error:function(){
            window.location.href="index.html"
        }
    });
    return false;
}

function updateTenant(){
    var id = $("#chid").val();
    var name = $("#chname").val();
    var agent = $("#chagent").val();
    var location = $("#chlocation").val();
    var telphone = $("#chtelphone").val();
    var remark = $("#chremark").val();

    var form = {
        "id" : id, "name": name, "location": location, "telphone": telphone, "remark": remark, "agent" : agent
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
        url:"http://"+ getIp() +":8080/tenant/update/",
        async:true,
        dataType: "json",
        data: JSON.stringify(form),
        success :function(result){
            if (result.judge == 1){
                window.event.returnValue=false;
                setTimeout(function(){window.location.href="tenant.html"},100);
            }else{
                alert("修改错误!");
            }
        },
        error:function(){
            window.location.href="index.html"
        }
    });
}


