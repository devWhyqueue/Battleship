var gameFinished = false;
var hitMapDisabled = true;
var timer = new Timer(30000, document.getElementById('countdown'));

function attack(e) {
    if (!hitMapDisabled) {
        var ajaxdata = parseInt(e.dataset.point);
        var value = 'attack=' + ajaxdata;
        var token = $('#_csrf').attr('content');
        var header = $('#_csrf_header').attr('content');

        timer.reset();
        $.ajax({
            url: "/game",
            type: "post",
            data: value,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (data, textStatus, jqXHR) {
                var url = '/game?enemyFieldMap';

                $('.alert').hide();
                $('#resultMsg').text('');
                $("#enemyFieldMap").load(url);
                if (jqXHR.status == 213) { // SUNK
                    $('#resultMsg').text(jqXHR.responseText);
                    $(".alert").show();
                    startTimer();
                } else if (jqXHR.status == 214) { // LOST
                    gameFinished = true;
                    $('#gameResultModal').on('show.bs.modal', function (e) {
                        document.getElementById("modalText").innerHTML = jqXHR.responseText;
                    });
                    $("#gameResultModal").modal();
                } else if (jqXHR.status == 215) { // HIT
                    startTimer();
                }
            }
        });
    }
}

function updateTurnStatus() {
    var url = '/game?updateTurnStatus';
    $("#turnStatus").load(url, function (response, status, xhr) {
        if (xhr.status == 210) {
            gameFinished = true;
            $('#gameResultModal').on('show.bs.modal', function (e) {
                document.getElementById("modalText").innerHTML = 'You lost! Good luck next time!';
            });
            $("#gameResultModal").modal();
        } else {
            $("#ownFieldMap").load('/game?ownFieldMap');
            if (xhr.status == 211 && hitMapDisabled) {
                startTimer();
                hitMapDisabled = false;
            } else if (xhr.status == 212 && !hitMapDisabled) {
                timer.reset();
                hitMapDisabled = true;
            }
        }
    });
}

function startTimer() {
    timer.reset();
    timer.start();
}

var updateFunction = setInterval(function () {
    if (!gameFinished)
        updateTurnStatus();
    else
        clearInterval(updateFunction);
}, 1000);