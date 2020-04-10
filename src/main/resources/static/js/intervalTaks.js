window.setInterval(intervalTask,60000);
function intervalTask(){
    $.get("/viewer/intervalTask");
}
document.oncontextmenu = function(){
    event.returnValue = false;
}
// 或者直接返回整个事件
document.oncontextmenu = function(){
    return false;
}
