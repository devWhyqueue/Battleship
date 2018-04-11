var user;

function updateTable() {
  var url = '/lobby?updateTable';
  $("#playerTable").load(url);
}

function updateInvitations() {
  var url = '/lobby?checkInvitations';
  $("#invitations").load(url);
}

function updateAcceptedInvitations() {
  var url = '/lobby?checkAcceptedInvitations';
  $("#acceptedInvitations").load(url);
}

$('#gameInvite').on('show.bs.modal', function(e) {
  user = e.relatedTarget.dataset.user;
  document.getElementById("modalText").innerHTML = "Do you really want to send an invite to " + user.bold() + "?";
});

setInterval(function() {
  updateTable();
  updateInvitations();
  updateAcceptedInvitations(); // this will run after every 5 seconds
}, 5000);