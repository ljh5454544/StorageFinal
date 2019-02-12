$(document).ready(
    function(){
        var kind = getcookie("kind");

        var str = "";
        for (var x=0; x < kind; x++){
            str += "<tr>" +
                "<td>" +
                "<select id='name' name='role' class='form-control' onchange='searchComm(this.options[this.options.selectedIndex].value, this)'>" +
                "</select>" +
                "</td>" +
                "<td><input id='sinprice' type='text' name='price' readonly='readonly'/></td>" +
                "<td><input id='num' type='text' name='num' onblur='calPrice(this)'/></td>" +
                "<td><input id='price' type='text' name='price' readonly='readonly'/></td>" +
                "</tr>"
        }
        $("#body").html(str);

        var token = getcookie("token");
        var username = getcookie("username");
        $.ajax({
            headers: {
                "token": token,
                "username": username
            },
            type:"get",
            url:"http://"+ getIp() +":8080/comm/all",
            async:true,
            dataType: "json",
            success :function(result){
                var list = result.list;
                var str2 = "";
                $.each(list, function(index, item) {
                    str2 +="<option value="+list[index].id+">"+ list[index].name+"</option>";
                });
                $("div [id=name]").each(function(){
                    $(this).html(str2);
                });
                $("div [id=sinprice]").each(function(){
                    $(this).val(list[0].price);
                });
                $("div [id=num]").each(function(){
                    $(this).val(1);
                });
                $("div [id=price]").each(function(){
                    $(this).val(list[0].price);
                });
            },
            error:function(){
                window.location.href="index.html"
            }
        });
    }
);

function calPrice(obj){
    var num = $(obj).val();
    var sinprice = $(obj).parent().prev().children().val();
    $(obj).parent().next().children().val(num * sinprice);
}

function searchComm(id,obj){
    var par = $(obj).parent().next().children();
    var form = {
        "id" : id
    }
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
        async:true,
        dataType: "json",
        data: JSON.stringify(form),
        success :function(result){
            if (result.judge == 1){
                var price = result.commdity.price;
                $(par).val(price);
                var number = $(obj).parent().next().next().children().val();
                $(obj).parent().next().next().next().children().val(number * price);
            }else{
                alert("修改错误!");
            }
        },
        error:function(){
            window.location.href="index.html"
        }
    });
}

function addRecords(){
    var commArray = new Array();
    var numArray = new Array();
    var priceArray = new Array();
    var identity = getcookie("identity");

    $("div [id=name]").each(function(){
        var x = $(this).val();
        commArray.push(x);
    });

    $("div [id=num]").each(function(){
        var n = $(this).val();
        numArray.push(n);
    });

    $("div [id=price]").each(function(){
        var p = $(this).val();
        priceArray.push(p);
    });

    var form = {
        "identity" : identity,
        "commArray" : commArray,
        "numArray" : numArray,
        "priceArray" : priceArray
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
        url:"http://"+ getIp() +":8080/record/add",
        async:true,
        dataType: "json",
        data: JSON.stringify(form),
        success :function(result){
            if (result.judge === 1){
                window.location.href="inventory-undeal.html";
            }else{
                alert("添加错误!");
            }
        },
        error:function(){
            window.location.href="index.html"
        }
    });
}