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
            url:"http://"+ getIp() +":8080/inventory/uuid",
            async:true,
            dataType: "json",
            success :function(result){
                var uuid = result.uuid;
                $("#inventoryid").val(uuid);
            },
            error:function(){
                window.location.href="index.html"
            }
        });

        var userId = getcookie("userId");
        var username = getcookie("username");
        $("#user").val(username);
        $("#userId").val(userId);

        var token = getcookie("token");
        var username = getcookie("username");
        $.ajax({
            headers: {
                "token": token,
                "username": username
            },
            type:"get",
            url:"http://"+ getIp() +":8080/tenant/all",
            async:true,
            dataType: "json",
            success :function(result){
                str = "<option value='无'>--请选择负责人--</option>";
                $.each(result, function(index, item) {
                    str += "<option value='"+result[index].name+"'>"+result[index].name+"</option>"
                });
                document.getElementById("agent").innerHTML=str;
            },
            error:function(){
                window.location.href="index.html"
            }
        });
    }
);

function addInventory(){
    var undertaker = $("#userId").val();
    var identity = $("#inventoryid").val();
    var agent = $("#agent").val();
    var kind = $("#kind").val();
    var price = $("#price").val();

    var form = {
        "undertaker" : undertaker, "identity" : identity, "agent" : agent, "kind" : kind, "price" : price
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
        url:"http://"+ getIp() +":8080/inventory/add",
        async:true,
        dataType: "json",
        data: JSON.stringify(form),
        success :function(result){
            if (result.judge == 1){
                setcookie("kind", kind, 1);
                setcookie("identity", identity, 1);
                window.location.href="inventory-add.html";
            }else{
                alert("添加错误!");
            }
        },
        error:function(){
            window.location.href="index.html"
        }
    });
}
