var ws;
//test
function connect() {
   var username = document.getElementById("username").value;
   ws = new WebSocket("ws://localhost:8080/WebSocketDemoNoDisconnect-master/chat/" + username);
   //ws = new WebSocket("ws://" + location.hostname + ":" + location.port + "WebSocketDemoNoDisconnect-master/chat/"+ username);
   //alert("connecting");
    ws.onmessage = function(event) {
        var log = document.getElementById("log");
        var message = JSON.parse(event.data);
        log.innerHTML += message.from + " : " + message.content + "\n";
    };
    

    
    }
function disconnect(){
    ws.close();
}

function send() {
    //alert("send msg");
    var content = document.getElementById("msg").value;
    var json = JSON.stringify({
        "content":content
    });
    alert(json);
    ws.send(json);
}
window.onbeforeunload = function() {
    ws.onclose = function () {}; // disable onclose handler first
    ws.close();
};

