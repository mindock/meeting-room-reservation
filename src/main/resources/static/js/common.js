function $(selector) {
    return document.querySelector(selector);
}

function getData(url, callback) {
    fetchManger({
        url: url,
        method: "GET",
        headers: {"content-type" : "application/json"},
        callback: callback
    })
}

function fetchManger({url, method, body, headers, callback}) {
    fetch(url, {method, body, headers, credentials: "same-origin"})
        .then(response => {
            callback(response);
        })
}