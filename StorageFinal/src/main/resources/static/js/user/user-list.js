$(function(){
    getList(1);
});

$(function(){
    $("#checkResult").click(function(){
        var x = $("#checkResult").is(':checked');
        $('input[name=idBox]').each(function(){
            $(this).attr('checked', x);
        });
    });
});

function getList(number){
    var token = getcookie("token");
    var username = getcookie("username");
    $.ajax({
        headers: {
            "token": token,
            "username": username
        },
        type:"get",
        url:"http://"+ getIp() +":8080/user/page/"+number,
        async:true,
        dataType: "json",
        success :function(result){
            if (result.judge === 1){
                var str = "";
                var array = result.list;
                $.each(array, function(index, item) {
                    str +="<tr>" +
                        "<td><input name='idBox' type='checkbox' class='checkbox' value='"+array[index].id+"'/></td>" +
                        "<td>"+array[index].id+"</td>" +
                        "<td>"+array[index].username+"</td>" +
                        "<td>"+array[index].roleName +"</td>" +
                        "<td>"+array[index].telephone+"</td>" +
                        "<td>"+array[index].email+"</td>" +
                        "<td>"+array[index].remark+"</td>" +
                        "<td>" +
                        "<div role='presentation' class='dropdown'>" +
                        "<button class='btn btn-default dropdown-toggle' data-toggle='dropdown' href='#' role='button' aria-haspopup='true' aria-expanded='false'>"+
                        "操作<span class='caret'></span>" +
                        "</button><ul class='dropdown-menu'>" +
                        "<li><a href='#' onclick='changeUser("+array[index].id+")'>修改</a></li>" +
                        "<li><a href='#' onclick='deleteUser("+array[index].id+")'>删除</a></li></ul></div></td></tr>"
                });
                document.getElementById("tbodyInput").innerHTML=str;

                var page = result.page;
                var str2 = "";
                str2 += "<li>" +
                "<a href='#' aria-label='Previous' onclick='getList(1)'>" +
                    "<span aria-hidden='true'>&laquo;</span>" +
                "</a>" +
                "</li>";
                if (page.pageNum > 1){
                    var x = page.pageNum-1;
                    str2 += "<li ><a href='#' onclick='getList("+x+")'>" + x + "</a></li>";
                }else{
                }
                str2 += "<li class='active'><a href='#'>" + page.pageNum + "</a></li>";
                if (page.pageNum < page.maxPage){
                    var xx = page.pageNum+1;
                    str2 += "<li><a href='#' onclick='getList("+ xx +")'>" + xx + "</a></li>";
                }
                str2 += "<li><a href='#' aria-label='Next' onclick='getList("+page.maxPage+")'><span aria-hidden='true'>&raquo;</span></a></li>";
                document.getElementById("pages").innerHTML=str2;
            }
        },
        error:function(){
                window.location.href="index.html"
            }
    });
}

function deleteUser(number){

    var roleId = getcookie("role");
    if (roleId === "3" || roleId === "2") {
        alert("对不起, 您的权限不足!");
        return
    }

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
        url:"http://"+ getIp() +":8080/user/delete",
        async:true,
        dataType: "json",
        data: JSON.stringify(form),
        success :function(result){
            if (result.judge === 1){
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


function removeAllUsers(){

    var roleId = getcookie("role");
    if (roleId === "3" || roleId === "2") {
        alert("对不起, 您的权限不足!");
        return
    }

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
        url:"http://"+ getIp() +":8080/user/deletes",
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


