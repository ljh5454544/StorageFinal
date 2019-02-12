var ALIYUN = "39.96.83.161"
var TENGXUNYUN = "139.199.69.64"
var LOCALHOST = "localhost"

function getIp(){
    return TENGXUNYUN;
}

function setcookie(name,value,iday){
    var odate=new Date();
    odate.setDate(odate.getDate()+iday);
    document.cookie=name+"="+value+";expires="+odate;

}

function getcookie(name) {
    var cookies = document.cookie;
    var arr1 = cookies.split("; ");
    for (i = 0; i < arr1.length; i++) {
        var arr2 = arr1[i].split("=")
        if (name == arr2[0]) {
            return arr2[1];
        }
    }
    return false;
}
function removecookie(name){
    setcookie(name,"","-1")  //通过建立cookie的时间设置，将时间设置为提前一天；
}

function saveExit(){
    var token = getcookie("token");
    var username = getcookie("username");

    var form = {
        "username" : username
    };

    $.ajax({
        headers: {
            "token": token,
            "username": username
        },
        type:"post",
        contentType: "application/json;charset=UTF-8",
        url:"http://"+ getIp() +":8080/user/exit",
        async:true,
        dataType: "json",
        data: JSON.stringify(form),
        success :function(result){
            if (result.judge === 1){
                alert("安全退出成功!");
                window.location.href = "index.html";
            }else{
                alert("安全退出失败!");
            }
        }
    });

    removecookie("username");
    removecookie("userId");
    removecookie("token");
}