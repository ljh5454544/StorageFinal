$(function(){
    getUndealList(1);
});

$(function(){
    $("#checkResult").click(function(){
        var x = $("#checkResult").is(':checked');
        $('input[name=idBox]').each(function(){
            $(this).attr('checked', x);
        });
    });
});

function removeAllUndeal(){
    var array = new Array()
    $('input[name=idBox]').each(function(){
        if ($(this).is(':checked')){
            var x = $(this).val();
            array.push(x)
        }
    });

    var token = getcookie("token");
    var username = getcookie("username");
    $.ajax({
        headers: {
            "token": token,
            "username": username
        },
        type:"post",
        contentType: "application/json;charset=UTF-8",
        url:"http://"+ getIp() +":8080/inventory/deletes",
        async:true,
        dataType: "json",
        traditional: true,
        data: JSON.stringify(array),
        success :function(result){
            if (result.judge == 1){
                location.reload();
            }else{
                alert("删除错误!");
            }
        },
        error:function(){
            window.location.href="index.html"
        }
    });
}

function getUndealList(number){
    var token = getcookie("token");
    var username = getcookie("username");
    $.ajax({
        headers: {
            "token": token,
            "username": username
        },
        type:"get",
        url:"http://"+ getIp() +":8080/inventory/page/"+number+"/"+1,
        async:true,
        dataType: "json",
        success :function(result){
            if (result.judge == 1){
                var str = "";
                var array = result.list;
                $.each(array, function(index, item) {
                    str +="<tr>" +
                        "<td><input name='idBox' type='checkbox' class='checkbox' value='"+array[index].id+"'/></td>" +
                        "<td>"+array[index].identity+"</td>" +
                        "<td>"+ array[index].userName +"("+array[index].undertaker+")</td>" +
                        "<td>"+array[index].agent+"</td>" +
                        "<td>"+array[index].kind+"</td>" +
                        "<td>"+array[index].price+"</td>" +
                        "<td>"+array[index].recordtime+"</td>" +
                        "<td>未入库</td>" +
                        "<td>" +
                        "<div role='presentation' class='dropdown'>" +
                        "<button class='btn btn-default dropdown-toggle' data-toggle='dropdown' href='#' role='button' aria-haspopup='true' aria-expanded='false'>"+
                        "操作<span class='caret'></span>" +
                        "</button><ul class='dropdown-menu'>" +
                        "<li><a href='#' onclick='watchInventory("+array[index].id+")'>查看</a></li>" +
                        "<li><a href='#' onclick='deleteInventory("+array[index].id+")'>删除</a></li></ul></div></td></tr>"
                });
                document.getElementById("tbodyInput").innerHTML=str;

                var page = result.page;
                var str2 = "";
                str2 += "<li>" +
                    "<a href='#' aria-label='Previous' onclick='getUndealList(1)'>" +
                    "<span aria-hidden='true'>&laquo;</span>" +
                    "</a>" +
                    "</li>";
                if (page.pageNum > 1){
                    var x = page.pageNum-1;
                    str2 += "<li ><a href='#' onclick='getUndealList("+x+")'>" + x + "</a></li>";
                }else{
                }
                str2 += "<li class='active'><a href='#'>" + page.pageNum + "</a></li>";
                if (page.pageNum < page.maxPage){
                    var xx = page.pageNum+1;
                    str2 += "<li><a href='#' onclick='getUndealList("+ xx +")'>" + xx + "</a></li>";
                }
                str2 += "<li><a href='#' aria-label='Next' onclick='getUndealList("+page.maxPage+")'><span aria-hidden='true'>&raquo;</span></a></li>";
                document.getElementById("pages").innerHTML=str2;
            }
        },
        error:function(){
            window.location.href="index.html"
        }
    });
}

function deleteInventory(number){
    var form = {
        "id": number
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
        url:"http://"+ getIp() +":8080/inventory/delete",
        async:true,
        dataType: "json",
        data: JSON.stringify(form),
        success :function(result){
            if (result.judge == 1){
                location.reload();
            }else{
                alert("删除信息错误!");
            }
        },
        error:function(){
            window.location.href="index.html"
        }
    });
}

function watchInventory(number){
    setcookie("inventoryId", number, 1);
    window.location.href="inventory-watch-undeal.html";
}