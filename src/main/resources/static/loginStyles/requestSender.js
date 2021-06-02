const form = document.getElementById("auth-form");

function sendRequest(reguestUrl) {
    let allAreFilled = true;
    document.getElementById("auth-form").querySelectorAll("[required]").forEach(function (i) {
        if (!allAreFilled) return;
        if (!i.value) allAreFilled = false;
    })
    allAreFilled = document.getElementById("agree-term").checked;
    if (allAreFilled) {
        console.log("sending");
        const XHR = new XMLHttpRequest();
        const url = window.location.protocol + "//" + window.location.host + "/auth/" + reguestUrl;
        XHR.open('POST', url, true);
        XHR.setRequestHeader('Content-Type', 'application/json');
        const json = JSON.stringify(Object.fromEntries(new FormData(form)));
        console.log(json);
        XHR.send(json);

    }
}


