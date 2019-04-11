/*var dir = "Data/";

var fileextension = ".png";
$.ajax({
    //This will retrieve the contents of the folder if the folder is configured as 'browsable'
    url: dir,
    success: function (data) {
        //List all .png file names in the page
        $(data).find("a:contains(" + fileextension + ")").each(function () {
            var filename = this.href.replace(window.location.host, "").replace("http://", "");
            $("body").append("<img src='" + dir + filename + "'>");
        });
    }
});*/

$.ajax({
    url: "ImagesData",
    type: "get",
    success: function (result) {
        console.log("length = " + result.length);
        for(var i=0;i<result.length;i++)
        {   
            //alert(result[i]);
            var source = 'images/' + String(result[i]) ;
            $("body").append("<img src='" + source + "' height = '250' width = '360' border = '5'n hspace = '20'>");
        }
    }
});

