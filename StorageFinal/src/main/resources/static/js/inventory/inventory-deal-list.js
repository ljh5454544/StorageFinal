$(function(){
    getDealList(1);
});

$(function(){
    $("#checkResult").click(function(){
        var x = $("#checkResult").is(':checked');
        $('input[name=idBox]').each(function(){
            $(this).attr('checked', x);
        });
    });
});

function getDealList(number){
    var token = getcookie("token");
    var username = getcookie("username");
    $.ajax({
        headers: {
            "token": token,
            "username": username
        },
        type:"get",
        url:"http://"+ getIp() +":8080/inventory/page/"+number+"/"+2,
        async:true,
        dataType: "json",
        success :function(result){
            if (result.judge == 1){
                var str = "";
                var array = result.list;
                $.each(array, function(index, item) {
                    str +="<tr>" +
                        "<td>"+array[index].identity+"</td>" +
                        "<td>"+array[index].userName+"("+array[index].undertaker+")</td>" +
                        "<td>"+array[index].agent+"</td>" +
                        "<td>"+array[index].kind+"</td>" +
                        "<td>"+array[index].price+"</td>" +
                        "<td>"+array[index].recordtime+"</td>" +
                        "<td>"+array[index].dealtime+"</td>" +
                        "<td>"+array[index].positions+"</td>" +
                        "<td>已入库</td>" +
                        "<td>" +
                        "<div role='presentation' class='dropdown'>" +
                        "<button class='btn btn-default dropdown-toggle' data-toggle='dropdown' href='#' role='button' aria-haspopup='true' aria-expanded='false'>"+
                        "操作<span class='caret'></span>" +
                        "</button><ul class='dropdown-menu'>" +
                        "<li><a href='#' onclick='watchDealInventory("+array[index].id+")'>查看</a></li>" +
                        "</ul></div></td></tr>"
                });
                document.getElementById("tbodyInput").innerHTML=str;

                var page = result.page;
                var str2 = "";
                str2 += "<li>" +
                    "<a href='#' aria-label='Previous' onclick='getDealList(1)'>" +
                    "<span aria-hidden='true'>&laquo;</span>" +
                    "</a>" +
                    "</li>";
                if (page.pageNum > 1){
                    var x = page.pageNum-1;
                    str2 += "<li ><a href='#' onclick='getDealList("+x+")'>" + x + "</a></li>";
                }else{
                }
                str2 += "<li class='active'><a href='#'>" + page.pageNum + "</a></li>";
                if (page.pageNum < page.maxPage){
                    var xx = page.pageNum+1;
                    str2 += "<li><a href='#' onclick='getDealList("+ xx +")'>" + xx + "</a></li>";
                }
                str2 += "<li><a href='#' aria-label='Next' onclick='getDealList("+page.maxPage+")'><span aria-hidden='true'>&raquo;</span></a></li>";
                document.getElementById("pages").innerHTML=str2;
            }
        },
        error:function(){
            window.location.href="index.html"
        }
    });
}

function watchDealInventory(number){
    setcookie("inventoryId", number, 1);
    window.location.href="inventory-watch-deal.html";
}

function searchInventory() {
    var searchMsg = $("#search").val();
    if (searchMsg == "") {
        window.location.reload()
        return
    }

    var form = {
        "identity": searchMsg
    };

    var token = getcookie("token");
    var username = getcookie("username");
    $.ajax({
        headers: {
            "token": token,
            "username": username
        },
        type:"post",
        url:"http://"+ getIp() +":8080/inventory/find",
        async:true,
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(form),
        success :function(result){
            if (result.judge == 1){
                var str = "";
                var inventory = result.inventory;
                str +="<tr>" +
                    "<td>"+inventory.identity+"</td>" +
                    "<td>"+inventory.userName+"("+inventory.undertaker+")</td>" +
                    "<td>"+inventory.agent+"</td>" +
                    "<td>"+inventory.kind+"</td>" +
                    "<td>"+inventory.price+"</td>" +
                    "<td>"+inventory.recordtime+"</td>" +
                    "<td>"+inventory.dealtime+"</td>" +
                    "<td>"+inventory.positions+"</td>" +
                    "<td>已入库</td>" +
                    "<td>" +
                    "<div role='presentation' class='dropdown'>" +
                    "<button class='btn btn-default dropdown-toggle' data-toggle='dropdown' href='#' role='button' aria-haspopup='true' aria-expanded='false'>"+
                    "操作<span class='caret'></span>" +
                    "</button><ul class='dropdown-menu'>" +
                    "<li><a href='#' onclick='watchDealInventory("+inventory.id+")'>查看</a></li>" +
                    "</ul></div></td></tr>"
                    document.getElementById("tbodyInput").innerHTML=str;
            }
        },
        error:function(){
            window.location.href="index.html"
        }
    });
}