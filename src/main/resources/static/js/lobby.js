var user;

function reloadTable() {
  var url = '/lobby?updateTable';
  $("#playerTable").load(url);
}

function reloadInvitations() {
  var url = '/lobby?checkInvitations';
  $("#invitations").load(url);
}

function reloadAcceptedInvitations() {
  var url = '/lobby?checkAcceptedInvitations';
  $("#acceptedInvitations").load(url);
}

$('#gameInvite').on('show.bs.modal', function(e) {
  user = e.relatedTarget.dataset.user;
  document.getElementById("modalText").innerHTML = "Do you really want to send an invite to " + user.bold() + "?";
});

setInterval(function() {
  reloadTable();
  reloadInvitations();
  reloadAcceptedInvitations(); // this will run after every 5 seconds
}, 5000);