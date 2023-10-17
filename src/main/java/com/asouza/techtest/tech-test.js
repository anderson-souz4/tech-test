// This is a not well written javascript snippet.
// The main point of the exercise is to focus on improve
// any code independently of the programming language to
// validate your programming logic.
// How can you make it better?

function isDEV() {
    var host = window.location.host;
    return host.startsWith('qa-projectA.com')
        || host.startsWith('localhost')
        || host.startsWith('127.0.0.1')


}

function ajaxCall() {
    if (!isDEV()) {
        $.ajax('https://prod-url.com').done(function (result) {
            document.getElementById('output').value = result;
        });
    } else {
        $.ajax('https://dev-url.com').done(function (result) {
            document.getElementById('output').value = result;
        });
    }
}