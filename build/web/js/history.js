$.ajax({
    url: "HistoryData",
    type: "get",
    success: function (result) {
        console.log("length = " + result.length);
        var str = "<p>";
        for(var i=0;i<result.length;i++)
        {   
            //alert(result[i]);
            //var source = 'images/' + String(result[i]) ;
            str = str + String(result[i]);
            str = str + "</p>";
            $("body").append(str);
        }
    }
});