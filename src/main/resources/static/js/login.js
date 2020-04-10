$(document).ready(function () {

    $('#password').focus(function () {
        if($('#code_area').length== 0){
            $('#code_area_parent').append(" <div class=\"form-group col-xs-12\" id=\"code_area\">\n" +
                "                        <label for=\"code\" class=\"col-sm-12 control-label\">输入验证码</label>\n" +
                "\n" +
                "                        <div class=\"col-sm-6\">\n" +
                "                            <input type=\"text\" class=\"form-control\" id ='code' name=\"code\">\n" +
                "                        </div>\n" +
                "                        <div class=\"col-sm-6\">\n" +
                "                            <td> <img class='login_code' src=\"/viewer/getCode\" alt=\"\" id=\"captcha1\" onclick='imgOnclick()'>\n" +
                "                        </div>\n" +
                "                    </div>")
        }
    })


});

function imgOnclick() {
    var ran = Math.floor(Math.random() * 100)
    $('#captcha1').attr('src','/viewer/getCode?' + ran);
}
function check() {
    var username  = $('#username').val();
    var password = $('#password').val();
    var code = $("#code").val();
    if(username.length<1){
        $("#errorMessage").text("请填写用户名");
        $("#errorModal").modal("show");
        return false
    }
    if(password.length<1){
        $("#errorMessage").text("请填写密码");
        $("#errorModal").modal("show");
        return false
    }
    if(code.length<1){
        $("#errorMessage").text("请填写验证码");
        $("#errorModal").modal("show");
        return false;
    }
    var pass=false;
    $.ajax({
        url:"/viewer/admin/prelogin",
        type: 'POST',
        async: false,
        timeout: 1000,
        data:{
            username:username,
            password:password,
            code:code
        },
        success:function (data) {
            if(data == "1"){
                pass = true;
            }else{
                pass = false;
                if(data=="验证码错误")
                {
                    var ran = Math.floor(Math.random() * 100)
                    $('#captcha1').attr('src','/viewer/getCode?' + ran);
                }
                $("#errorMessage").text(data);
                $('#errorModal').modal('show');
            }
        },
        error:function (message) {
            console.log(message)
        }

    })
    return pass;
}