function check(input) {
  if (input.value != document.getElementById('password').value) {
    input.setCustomValidity('Passwörter müssen übereinstimmen!');
  } else {
    input.setCustomValidity('');
  }
}