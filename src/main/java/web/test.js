const socket = new WebSocket("ws://localhost:8080/test_war_exploded/status");

socket.onmessage = onMessage;

async function formSubmit() {
    while (true) {
        await new Promise(r => setTimeout(r, 500));
        socket.send("status");
    }
}

function onMessage(event) {
    console.debug(event)
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
