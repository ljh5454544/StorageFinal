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
                var str1 = "<tr>" +
                    "<td>" + inventory.identity + "</td>" +
                    "<td>"+ inventory.userName +"("+inventory.undertaker+")</td>" +
                    "<td>" + inventory.agent + "</td>" +
                    "<td>" + inventory.kind + "</td>" +
                    "<td>" + inventory.price + "</td>" +
                    "<td>" + inventory.recordtime + "</td>" +
                    "<td>" + inventory.dealtime + "</td>" +
                    "<td>未入库</td>" +
                    "</tr>";
                $("#tbodyInput").html(str1);

                var str2 = "";
                $.each(records,function(index){
                    str2 += "<tr>" +
                    "<td>"+ records[index].commodityName +"</td>" +
                    "<td>"+ records[index].commodityPrice +"</td>" +
                    "<td>"+ records[index].num +"</td>" +
                    "<td>"+ records[index].price +"</td>" +
                    "</tr>"
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



function getShelf(){
        var room = $("#room").val();
        var token = getcookie("token");
        var username = getcookie("username");
        $.ajax({
            headers: {
                "token": token,
                "username": username
            },
            type:"get",
            url:"http://"+ getIp() +":8080/shelf/rshlef/"+room,
            async:true,
            dataType: "json",
            success :function(result){
                var str3 = "";
                $.each(result, function(index, item) {
                    str3 +="<option value="+result[index].id +">"+ result[index].name+"</option>";
                });
                $("#shelf").html(str3);
            },
            error:function(){
                window.location.href="index.html"
            }
    });
}

function getPostion(){
    var shelf = $("#shelf").val();

    var token = getcookie("token");
    var username = getcookie("username");
    $.ajax({
        headers: {
            "token": token,
            "username": username
        },
        type:"get",
        url:"http://"+ getIp() +":8080/position/sposition/"+shelf,
        async:true,
        dataType: "json",
        success :function(result){
            var str3 = "";
            $.each(result, function(index, item) {
                str3 +="<option value="+result[index].id +">"+ result[index].name+"</option>";
            });
            $("#position").html(str3);
        },
        error:function(){
            window.location.href="index.html"
        }
    });
}

function saveStorage(){

    var id =  $("#inventoryId").val();
    var room = $("#room").val();

    var sid = $("#shelf").val();
    var pid = $("#position").val();
    var locat = room + "_"+ sid + "_"+ pid;

    if (sid == null || sid == "" || pid == null || pid == ""){
        alert("请选择库区位置!");
        return;
    }


    alert(id);
    var form = {
        "id" : id,
        "location" : locat,
        "sid" : sid,
        "pid" : pid
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
        url:"http://"+ getIp() +":8080/inventory/save",
        async:true,
        dataType: "json",
        data: JSON.stringify(form),
        success :function(result){
            if (result.judge === 1){
                alert("入库成功!");
                window.location.href="inventory-deal.html";
            }
        },
        error:function(){
            window.location.href="index.html"
        }
    });

}
