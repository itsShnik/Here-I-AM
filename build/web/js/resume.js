$.ajax({
    url: "ResumeData",
    type: "get",
    success: function (result) {
        console.log("length = " + result.length);
        var str = "<object data='images/" + result;
        str = str + ".pdf' width='800px' height='1000px' />";
        $("body").append(str);
    }
});
