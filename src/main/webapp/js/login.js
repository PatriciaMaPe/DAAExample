function doLogin(login, password) {
    $.ajax({
	url: 'rest/users/' + login,
	type: 'GET',
	beforeSend: function (xhr) {
	    xhr.setRequestHeader('Authorization', 'Basic ' + btoa(login + ":" + password));
	}
    })
    .done(function() {
	localStorage.setItem('authorization-token', btoa(login + ":" + password));
	localStorage.setItem('userLogin', login);
	window.location = 'main.html';
    })
    .fail(function() {
	alert('Invalid login and/or password.');
    });
}

function doLogout() {
    localStorage.removeItem('authorization-token');
    window.location = 'index.html';
}
