//隐藏错误提示
$('.icons').hide();
$('.modal_icons').hide();

// 登录表单
var username = $("#username");
var username_null = $("#username_null");
var username_error = $("#username_error");

//登录表单提交
var loginFormSubmit = $("#loginFormSubmit");
var password = $("#password");
var password_null = $("#password_null");

// 输入框失去焦点
username.blur(function() {
	var username_len = username.val().length;
	if (username_len === null) {
		username_null.show();
	}
	if (username_len !== null) {
		var pattren = /^[a-zA-Z0-9-]{6,19}$/;
		var username_value = username.val();
		if (!pattren.test(username_value)) {
			username_error.show();
		}
	}
});
// 输入框获得焦点
var login_error = $('.login_error');
username.focus(function() {
	username_null.hide();
	username_error.hide();
	login_error.css("visibility", "hidden");
});

loginFormSubmit.click(function() {
	var password_len = password.val().length;
	var username_len = username.val().length;
	var username_value = username.val();
	var pattren1 = /^[a-zA-Z0-9-]{6,19}$/;
	var isusername = checkUsername(username_value);

	console.log(isusername);
	if (password_len !== 0 && username_len !== 0
			&& pattren1.test(username_value)) {
		return true;
	} else {
		if (username_len === 0) {
			username_null.show();
		}
		if (!pattren1.test(username_value) && username_len !== 0) {
			username_error.show();
		}
		if (password_len === 0) {
			password_null.show();
		}
		return false;
	}
});
password.focus(function() {
	password_null.hide();

	login_error.css("visibility", "hidden");
});

function checkUsername(username) {
	if (!(/^[a-zA-Z0-9-]{6,19}$/.test(username))) {


		return false;
	}

	return true;
}
