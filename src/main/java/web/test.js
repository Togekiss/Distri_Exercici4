const socket = new WebSocket("ws://localhost:8080/test_war_exploded/status");

function onMessage() {
    console.log("onMessage")
}

socket.onmessage = onMessage;
const wsUrl = 'ws://' + window.location.host
console.log(wsUrl)

const aux = 'ws://' + document.location.host
console.log(aux)

function printRand(message){
    console.log("in printRand")
    const randField = document.getElementById("randField");
    randField.innerText = message;

}
