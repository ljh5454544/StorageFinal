$(function(){
    var inventoryId = getcookie("inventoryId");
    $("#inventoryId").val(inventoryId);

    var form = {
        "id" : inventoryId
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
        url:"http://"+ getIp() +":8080/inventory/msg",
        async:true,
        dataType: "json",
        traditional: true,
        data: JSON.stringify(form),
        success :function(result){
            var inventory = result.inventory;
            var records = result.records;
            if (result.judge === 1){
                $("#identity").val(inventory.identity);
                var str1 = "<tr>" +
                    "<td>" + inventory.identity + "</td>" +
                    "<td>"+ inventory.userName +"("+ inventory.undertaker +")</td>" +
                    "<td>" + inventory.agent + "</td>" +
                    "<td>" + inventory.kind + "</td>" +
                    "<td>" + inventory.price + "</td>" +
                    "<td>" + inventory.recordtime + "</td>" +
                    "<td>" + inventory.dealtime + "</td>" +
                    "<td>" + inventory.positions + "</td>" +
                    "<td>已入库</td>" +
                    "</tr>";
                $("#tbodyInput").html(str1);

                var str2 = "";
                $.each(records,function(index){
                    str2 += "<tr>" +
                        "<td>"+ records[index].commodityName + "</td>" +
                        "<td>"+ records[index].commodityPrice + "</td>" +
                        "<td>"+ records[index].num +"</td>" +
                        "<td>"+ records[index].price +"</td>" +
                        "</tr>";
                });
                $("#record").html(str2);
            }else{
                alert("加载错误!");
            }
        },
        error:function(){
            window.location.href="index.html"
        }
    });
});



function outInventory(){
    var id = $("#inventoryId").val();
    var identity = $("#identity").val();

    var form = {
        "id" : id,
        "identity" : identity
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
        url:"http://39.96.83.161:8080/inventory/out",
        async:true,
        dataType: "json",
        data: JSON.stringify(form),
        success :function(result){
            if (result.judge === 1){
                alert("出库成功!");
                window.location.href="inventory-out.html";
            }
        },
        error:function(){
            window.location.href="index.html"
        }
    });
}
