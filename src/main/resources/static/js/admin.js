$(document).ready(function () {
    value = $('#idea').attr("data-value");
    judgePage();
    getSearchResult(1);
    $('#search').click(function () {
        getSearchResult(1);
    })

    $('#pre_page').click(function () {
        var currentPage = $('#current_page').text();
        currentPage = parseInt(currentPage);
        getSearchResult(currentPage-1);
    });
    $('#next_page').click(function () {
        var currentPage = $('#current_page').text();
        currentPage = parseInt(currentPage);
        getSearchResult(currentPage+1);

    });
    $('#confirm').click(function () {
        var oldPassword = $('#old_password').val();
        var newPassword=$('#new_password').val();
        var reNewPassword=$('#repeat_new_password').val();
        var employeeId = $('#employeeId').text();
        if(oldPassword == ''|| oldPassword == null){
            alert("请输入原密码");
            return false;
        }
        if(newPassword == ''|| newPassword == null){
            alert("请输入新密码");
            return false;
        }
        if(reNewPassword == ''||reNewPassword==null){
            alert("请重复输入新密码");
            return false;
        }

        if(newPassword!=reNewPassword){
            alert('两次密码输入不同');
            $('#new_password').val("");
            $('#repeat_new_password').val("");
            return false;
        }
        if(oldPassword == newPassword){
            alert("新旧密码不能相同");
            return false;
        }
        $.post('update',{
            employeeId:employeeId,
            oldPassword:oldPassword,
            newPassword:newPassword,
            repeatNewPassword:reNewPassword
        },function (data) {
            if(data=='3001'){
                alert("两次新密码输入不同");
            }else if(data =='3002'){
                alert("旧密码不匹配");
            }else if(data == '1'){
                alert("修改成功,下次登录请使用新密码");
                window.location.reload();
            }else{
                alert("修改失败");
            }
        })
    })
    
});

function getSearchResult(page) {
    var operationDate = $('#operation_date').val();
    var operationType = $('#operation_type').val();
    var user = $('#user').val();
    $.post("/viewer/admin/search",{
        operationDate:operationDate,
        operationType:operationType,
        user:user,
        pageNum:page
    },function (data,status){
        if(status != "success"){
            tips("未知的错误发生了");
        }else if(data == '0'){
            tips("请求错误");
        }else if(data == '301'){
            tips('页码输入有误');
        }else if(data == "302"){
            tips("日期参数有误");
        }else if(data == '303'){
            tips("操作类型参数有误");
        }else{
            $('.wxj_file_info_search').remove();
            result = JSON.parse(data);
            $('#current_page').text(result.currentPage);
            $('#total_page').text(result.totalPage);
            for(var i=0;i<result.recordList.length;i++){
                addResult(result.recordList[i]);
            }
            judgePage();
            $(".wxj_content").animate({ scrollTop: "0px" }, 500);

        }
    })
}


function judgePage() {
    var currentPage = $('#current_page').text();
    currentPage = parseInt(currentPage);
    //var keyword = $('#keyword').val();
    //keyword = parseInt(keyword);
    var totalPage = $("#total_page").text();
    totalPage = parseInt(totalPage);
    if(currentPage<=1){
        $('#pre_page').attr("style","pointer-events: none; ");
    }else{
        $('#pre_page').attr("style","");
    }
    if(currentPage>=totalPage){
        $('#next_page').attr("style","pointer-events: none; ");
    }else{
        $('#next_page').attr("style","");
    }

}
$(function () {
    $('[data-toggle="popover"]').popover()
})

function addResult(record) {
    $('#result_area').append("<div class=\"wxj_file_info_search\">\n" +
        "                        <div class=\"row\">\n" +
        "                            <div class=\"col-xs-6 col-md-2\">\n" +
        "                                <span class=\"col-xs-12 wxj_search_title\">操作用户</span>\n" +
        "                                <span class=\"col-xs-12 lines_2\">"+record.userId+"</span>\n" +
        "                            </div>\n" +
        "                            <div class=\"col-xs-6 col-md-2\">\n" +
        "                                <span class=\"col-xs-12 wxj_search_title\">操作类型</span>\n" +
        "                                <span class=\"col-xs-12 lines_2\">"+record.type+"</span>\n" +
        "                            </div>\n" +
        "                            <div class=\"col-xs-6 col-md-2\">\n" +
        "                                <span class=\"col-xs-12 wxj_search_title\">操作时间</span>\n" +
        "                                <span class=\"col-xs-12 lines_2\">"+record.operationTimeStr+"</span>\n" +
        "                            </div>\n" +
        "                            <div class=\"col-xs-6 col-md-2\">\n" +
        "                                <span class=\"col-xs-12 wxj_search_title\">用户地址</span>\n" +
        "                                <span class=\"col-xs-12 lines_2\">"+record.ip+"</span>\n" +
        "                            </div>\n" +
        "                            <div class=\"col-xs-12 col-md-2\">\n" +
        "                                <span class=\"col-xs-12 wxj_search_title\">操作详情</span>\n" +
        "                                <span class=\"col-xs-12 lines_2\">"+record.description+"</span>\n" +
        "                            </div>\n" +
        "                            <div class=\"col-xs-12 col-md-2\" id='record_"+record.id+"'>\n" +
        "                                <span class=\"col-xs-12 wxj_search_title\">操作</span>\n" +
        "<button type=\"button\" class=\"btn btn-default\" title='申请单号："+record.applyNumber+"' data-container=\"body\" data-toggle=\"popover\" data-placement=\"left\" data-content='借阅原因说明："+record.situation+"'>\n" +
        "    查看详情" +
        "</button>"+
        "                            </div>\n" +
        "                        </div>\n" +
        "                    </div>");
    if(record.type!="提出申请"){
        $('#record_'+record.id).hide();
    }
    $('[data-toggle="popover"]').popover()
}

