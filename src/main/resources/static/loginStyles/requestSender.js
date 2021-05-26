const form = document.getElementById("auth-form");

function sendRequest(reguestUrl) {
    var XHR = new XMLHttpRequest();
    // Define what happens on successful data submission
    XHR.addEventListener("load", function (event) {
        alert(event.target.responseText);
    });
    // Define what happens in case of error
    XHR.addEventListener("error", function (event) {
        alert('Oops! Something went wrong.');
    });

    var url = "http://localhost:8080/auth/" + reguestUrl;
    console.log(url);
    XHR.open('POST', url, true);
    XHR.setRequestHeader('Content-Type', 'application/json');

    var json = JSON.stringify(Object.fromEntries(new FormData(form)));
    console.log(json);
    XHR.send(json);
}
