var serializedData;

function rotate(el) {
  var grid = $('.grid-stack').data('gridstack');
  var width = el.getAttribute("data-gs-width") - 0;
  var height = el.getAttribute("data-gs-height") - 0;
  grid.resize(el, height, width);
}

function saveGrid() {
  this.serializedData = _.map($('.grid-stack > .grid-stack-item:visible'), function(el) {
    el = $(el);
    var node = el.data('_gridstack_node');
    return {
      x: node.x,
      y: node.y,
      width: node.width,
      height: node.height
    };
  }, this);

  var ajaxdata = btoa(JSON.stringify(this.serializedData, null, '    '));
  var value = 'shipPlacement=' + ajaxdata;
  var token = $('#_csrf').attr('content');
  var header = $('#_csrf_header').attr('content');
  $.ajax({
    url: "/placeShips",
    type: "post",
    data: value,
    beforeSend: function(xhr) {
      xhr.setRequestHeader(header, token);
    },
    success: function(msg) {
      $('#errorMsg').text('');
      var grid = $('.grid-stack').data('gridstack');
      grid.setStatic(true);
      var but = document.getElementById("lock-in");
      but.firstChild.data = "Waiting for opponent...";
      but.disabled = true;
    },
    error: function(XMLHttpRequest, textStatus, errorThrown) {
      $('#errorMsg').text(XMLHttpRequest.responseText);
    }
  });
  return false;
}

function updateGameStatus() {
  var url = '/placeShips?isGameReady';
  $("#gameReady").load(url, function(response, status, xhr) {
    if (xhr.status == 308) {
      window.location.href = "/index.html";
    }
  });
}

setInterval(function() {
  updateGameStatus(); // this will run after every 5 seconds
}, 5000);

$(function() {
  var options = {
    float: true,
    disableResize: true,
    width: 10,
    height: 10,
    staticHeight: 10
  };
  $('.grid-stack').gridstack(options);
});