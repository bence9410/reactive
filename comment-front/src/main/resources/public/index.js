function addComment() {
     $.ajax({
        url: "/comments",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify({content: $("#commentContent").val()}),
        success: function() {
            $("#commentContent").val("")
        },
      });
}

setTimeout(() => $.SSE("/comments/subscribe", {
    onMessage: function(e) {
        $("#comments").append("<li>" + JSON.parse(e.data).content + "</li>")
    },
    headers: { "Accept": "text/event-stream",
    "Cache-Control": "no-cache",
    "Connection": "keep-alive"}
}).start(), 0)

$.ajax({
    url: "/comments",
    success: function(data) {
        let element = $("#comments")
        for (let i = 0; i < data.length; i++) {
            element.append("<li>" + data[i].content + "</li>")
        }
    },
});