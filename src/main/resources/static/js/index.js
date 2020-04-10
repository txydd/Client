/**
 * Created by chenweixuan on 2018/8/30.
 */
$(function () {
    // 移动端顶部菜单 2019/01/01
    // var wi=$(window).width();
    // // alert(wi);
    // if(wi<=768){
    //     $(".sub_menu").parent().click(function () {
    //         $(".sub_menu").slideToggle();
    //     })
    // }
    // ======================= application.html < START > =======================
    $('.time').each(function () {
        var time = $(this).text();
        $(this).text(dateParse(time));

        time = $(this).val();
        $(this).val(dateParse(time));
    });
    $('#logout').click(function () {
        window.location.href='/viewer/exit';
    });

    //日期选择插件 初始化
    $("[data-datepicker]").datepicker({
        autoclose: true,
        language: 'zh-CN',
        format: "yyyy-mm-dd",
        startDate: new Date()
    });
    //日期选择插件 初始化
    $("[data-datepicker_2]").datepicker({
        autoclose: true,
        language: 'zh-CN',
        format: "yyyy-mm-dd",
    });

    //申请时间
    //获取当天时间，格式YYYY-MM-DD
    function getNowFormatDate() {
        var date = new Date();
        var seperator1 = "-";
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = year + seperator1 + month + seperator1 + strDate;
        $("[data-today]").val(currentdate)
    }

    getNowFormatDate();

    //搜索关键词
    /* $(".wxj_search_but").click(function () {
         var file_number = "xxx.file_number";
         var file_name = "xxx.file_name";
         var revision = "xxx.revision";
         var creator_id = "xxx.creator_id";
         var create_time = "xxx.create_time";
         $(".wxj_result").append("<tr><td>1</td><td>" + file_number + "</td><td>" + file_name + "</td><td>" + revision + "</td><td>" + creator_id + "</td><td>" + create_time + "</td><td class='wxj_res_application' data-fileNumber=" + file_number + ">添加</td></tr>");


     });*/


// ======================= application.html < END > =======================


// =========================== applicationHistory.html < START > ====================================


   /* $(".wxj_file_info_edit_read").click(function () {
        var fileLink = $(this).attr("data-link");
        if($(this).children().last().text()!="点击查看文件"){
            return;
        }
        var truthBeTold = window.confirm("您当前正在行使查阅权限，附件一旦打开即视为权限已使用，您确定要打开附件进行查阅吗？");
        if(truthBeTold){
            window.location.href = "/viewer/read/?fileName="+fileLink
        }
    });*/
    $('.wxj_file_info_read').click(function () {
        var applyNumber = getQueryString("applyNumber");
        var fileLink = $(this).children().last().attr("data-link");
        var uid = $(this).children().last().attr("data-uid");

        if($(this).children().last().last().text() != "点击查看文件"){
            return;
        }
        var fileName = $(this).children().last().attr("data-fileName");
        if(fileName.toLocaleString().toLocaleLowerCase().indexOf("png")!=-1||fileName.toLocaleString().toLocaleLowerCase().indexOf("jpg")!=-1){
            $("#ImgShow").attr("src","/viewer/complete/"+fileName);
            $('#ImgShow').click();
        }else{
            $.post("/viewer/read/checkStatus",{
                fileName:fileLink,
                applyNumber:applyNumber,
                guid:uid
            },function (data) {
                if(data == "success"){
                    var truthBeTold = window.confirm("您当前正在行使查阅权限，附件一旦打开即视为权限已使用，您确定要打开附件进行查阅吗？");
                    if(truthBeTold){
                        window.location.href = "/viewer/read/?fileName="+fileLink+"&applyNumber="+applyNumber+"&guid="+uid;
                    }
                }else{
                    tips(data);
                }
            })
        }




    })


// ============================ applicationHistory.html < END > =====================================


    //list.html 点击 "查看"
    $(".wxj_res_read").click(function () {
        var applyNumber = $(this).attr('data-apply-number');
        window.location.href = "../applicationHistory/?applyNumber=" + applyNumber;
    });
    //点击"再次申请"
    $(".wxj_res_read_again").click(function () {
        var applyNumber = $(this).attr('data-apply-number');
        var classification = $(this).attr("data-classification");
        window.location.href = "../application/?applyNumber=" + applyNumber+"&classification="+classification;
    });
    //点击"审批管理"
    $(".wxj_res_exam").click(function () {
        window.location.href = "/viewer/feedback/"
    });


    //页面跳转
    $("[data-menuctrl]").click(function () {
        var i = $(this).text();
        var u = $(this).find("span").text();

        if (u == "新建申请"||i=="新建申请") {
            window.location.href = "/viewer/index"
        }else if (u == "目录结构" ||i == "目录结构") {
            $('#_load').fadeIn();
            window.location.href = "/viewer/directory/index"
        }else if (i == "审批申请单"||u == "审批申请单") {
            window.location.href = "/viewer/admin/checkApplication"
        }else if (i == "用户管理"||u == "用户管理") {
            window.location.href = "/viewer/admin/organization"
        } else if (i == "权限管理"||u == "权限管理") {
            window.location.href = "/viewer/admin/role"
        }else if (u == "模糊搜索" || i == "模糊搜索") {
            window.location.href = "/viewer/search/index"
        }

        else if (u == "文件清单" || i == "文件清单") {
            window.location.href = "/viewer/referUpload/index"
        }

        else if (u == "上传文件" || i == "上传文件") {
            window.location.href = "/viewer/admin/uploadFile"
        }
        else if (u == "上传文件清单" || i == "上传文件清单") {
            window.location.href = "/viewer/admin/uploadFileList"
        }


        else if (u == "审批管理"||i == "审批管理") {
            window.location.href = "examinationsList.html"
        } else if (i == "待审核"|| u== "待审核") {
            window.location.href = "/viewer/list/uncheckedList"
        } else if (i == "已审核"|| u== "已审核") {
            window.location.href = "/viewer/list/checkedList"
        } else if (i == "已过期"|| u== "已过期") {
            window.location.href = "/viewer/list/expiredList"
        } else if (i == "已失效"|| u== "已失效") {
            window.location.href = "/viewer/list/invalidList"
        }else if (i == "操作记录"|| u== "操作记录") {
            window.location.href = "/viewer/admin/"
        }else if(i=='下载文件'|| u== "下载文件"){
            window.location.href = "/viewer/admin/getFile"
        }else if(i=='借阅时限设置'|| u== "借阅时限设置"){
            window.location.href = "/viewer/admin/setting"
        }else {console.log(u)}
    });
    
});

/*
Date->String "yyy-MM-dd"
 */
function dateParse(time) {
    var date;
    if (!isNaN(time)) {
        date = new Date(time);
    } else {
        var dateStr = time.trim().split(" ");
        var strGMT = dateStr[0] + " " + dateStr[1] + " " + dateStr[2] + " " + dateStr[5] + " " + dateStr[3] + " GMT+0800";
        date = new Date(Date.parse(strGMT));
    }
    var year = date.getFullYear();  //获取年
    var month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1);    //获取月
    var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate(); //获取日
    var hours = date.getHours();
    var minutes = date.getMinutes();
    var sec = date.getSeconds();
    return year + "-" + month + "-" + day
}

/*
获取url中参数值
name:参数名
 */
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

function tips(mes) {
    $(".wxj_mes_word").html(mes);
    $(".wxj_mes_bg").fadeIn();
    $(".closeBut").click(function () {
        $(".wxj_mes_bg").fadeOut("fast");
    })
}


/*
5/30 chenweixuan add
 */
$(function () {
    // 导航栏收缩
    $(".parents_menu").click(function(){
        $(this).siblings(".sub_menu").slideToggle("fast");
    });
    // 图片浏览插件 写在 directoryIndex.html 中
    // 配置项说明地址：https://blog.csdn.net/qq_29132907/article/details/80136023
    // 3D 插件写在 fileIndex2.html 中
});
