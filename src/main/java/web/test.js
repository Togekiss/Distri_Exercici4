var socket = new WebSocket("ws://localhost:8080/test_war_exploded/status");
socket.onmessage = onMessage;


var wsUrl = 'ws://' + window.location.host
console.log(wsUrl)

var aux = 'ws://' + document.location.host
console.log(aux)

/*
function onMessage(event) {
    var btnSubmit = document.getElementById("btnSubmit");
    btnSubmit.disabled = true;

    var progress = document.getElementById("progress");
    var data = JSON.parse(event.data);
    progress.value = data.value;

    var lblProgress = document.getElementById("lblProgress");
    if(data.value < 100){
        lblProgress.innerHTML = 'Progress: ' + data.value + '%';
    }else{
        btnSubmit.disabled = false;
        lblProgress.innerHTML = "Finish";
    }
}

 */

socket.onopen = function test(){
    socket.send("C1")
}

socket2.onopen = function test(){
    socket.send("C2")
}

function onMessage(event) {
    console.log(event)
    var randField = document.getElementById("randField")
    var randField2 = document.getElementById("randField2")
    randField.innerText = event;
    randField2.innerHTML = event;
}


function formSubmit() {
    socket.send("{\"start\":\"true\"}");
}

