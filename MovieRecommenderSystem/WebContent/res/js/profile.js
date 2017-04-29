/*!
 * profile.js v1.1
 * Author: Cong Bao
 * Date: 22 April, 2017
 */

if (typeof jQuery === 'undefined') {
    throw new Error('jQuery required');
}

(function (win, doc, $, undefined) {
	
	$(function () {
		$('#navbar').attr('class', 'navbar navbar-static-top');
		$('footer').css('background', '#fff');
		$('#tabs a[href="' + $(location).attr('hash') + '"]').tab('show');
	});
	
	// favourites
	$(function () {
		$('#favorite > div').each(function () {
			var $imdb = $(this).attr('id').substr(3);
			$.ajax({
				cache: false,
				type: 'get',
				url: 'https://www.omdbapi.com',
				data: { 'i': $imdb },
				success: function (data, statusText) {
					$('#favtitle' + $imdb).text(data.Title);
					$('#favimg' + $imdb).attr('src', data.Poster.replace(/https?/, 'https'));
					$('#favimg' + $imdb).attr('alt', data.Title);
					$('#favimg' + $imdb).css('width', '100px');
					$('#favinfo' + $imdb).text('Released on: ' + data.Released);
					$('#favplot' + $imdb).text(data.Plot);
				}
			});
		});
	});
	
	// recommendations
	$(function () {
		$('#recommend > div').each(function () {
			var $imdb = $(this).attr('id').substr(3);
			$.ajax({
				cache: false,
				type: 'get',
				url: 'https://www.omdbapi.com',
				data: { 'i': $imdb },
				success: function (data, statusText) {
					var $neighbour = $('#rectitle' + $imdb).text();
					$('#rectitle' + $imdb).html(data.Title + '<i class="pull-right">according to <b>' + $neighbour  + '</b> similar users\' ratings</i>');
					$('#recimg' + $imdb).attr('src', data.Poster.replace(/https?/, 'https'));
					$('#recimg' + $imdb).attr('alt', data.Title);
					$('#recimg' + $imdb).css('width', '100px');
					$('#recinfo' + $imdb).text('Released on: ' + data.Released);
					$('#recplot' + $imdb).text(data.Plot);
				}
			});
		});
	});
	
	$(function () {
		var email = false;
		$('#newEmail').blur(function () {
    		var $val = $('#newEmail').val();
    		var regex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    		if (!regex.test($val)) {
    			$('#editEmailGroup').attr('class', 'form-group has-error has-feedback');
				$('#newEmail').siblings('span').remove();
				$('#newEmail').after('<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>');
				$('#newEmail').after('<span class="help-block">Invalid email format</span>');
				$('#submitEmailBtn').attr('disabled', 'disabled');
    		} else {
    			$.ajax({
    				cache: false,
    				type: 'post',
    				url: '/ajax/emailExist',
    				data: { email: $val },
    				success: function (data, statusText) {
    					if (data.exist == true) {
    						$('#editEmailGroup').attr('class', 'form-group has-error has-feedback');
    						$('#newEmail').siblings('span').remove();
    						$('#newEmail').after('<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>');
    						$('#newEmail').after('<span class="help-block">This email address has been registered</span>');
    						$('#submitEmailBtn').attr('disabled', 'disabled');
    					} else {
    						$('#editEmailGroup').attr('class', 'form-group has-success has-feedback');
        					$('#newEmail').siblings('span').remove();
        					$('#newEmail').after('<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>');
        					$('#submitEmailBtn').removeAttr('disabled');
    					}
    				}
    			});
    		}
    	});
		$('#resetEmailBtn').click(function () {
			$("#editEmailGroup").attr("class", "form-group");
			$("#newEmail").siblings("span").remove();
			$("#submitEmailBtn").attr("disabled", "disabled");
		});
	});
	
	$(function () {
		var oldPass = false;
		var newPass = false;
		var confirm = false;
		
		var checkOld = function () {
			$.ajax({
				cache: false,
				type: 'post',
				url: '/ajax/checkPass',
				data: {
					id: $('#userId').val(),
					password: $.md5($('#oldPassword').val())
				},
				success: function (data, statusText) {
					if (data.valid == false) {
						oldPass = false;
						$('#oldPassGroup').attr('class', 'form-group has-error has-feedback');
						$('#oldPassword').siblings('span').remove();
						$('#oldPassword').after('<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>');
						$('#oldPassword').after('<span class="help-block">Wrong old password</span>');
					} else {
						oldPass = true;
						$('#oldPassGroup').attr('class', 'form-group has-success has-feedback');
    					$('#oldPassword').siblings('span').remove();
    					$('#oldPassword').after('<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>');
					}
				}
			});
			checkStatus();
		}
		
		var passComplexity = function (pass) {
    		var com = 0;
    		if (pass.length < 6) return 0;
    		if (pass.match(/[a-z]+/)) com++;
    		if (pass.match(/[A-Z]+/)) com++;
    		if (pass.match(/[0-9]+/)) com++;
    		if (pass.match(/[^a-zA-Z0-9]+/)) com++;
    		return com;
    	}
		
		var checkNew = function () {
			var $val = $('#newPassword').val();
    		var $lv = passComplexity($val);
    		if ($lv == 0) {
    			newPass = false;
    			$('#newPassGroup').attr('class', 'form-group has-error has-feedback');
    			$('#newPassword').siblings('span').remove();
    			$('#newPassword').after('<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>');
    			$('#newPassword').after('<span class="help-block">Your password is too simple</span>');
    		} else if ($lv > 0 && $lv < 3) {
    			newPass = true;
    			$('#newPassGroup').attr('class', 'form-group has-warning has-feedback');
    			$('#newPassword').siblings('span').remove();
    			$('#newPassword').after('<span class="glyphicon glyphicon-warning-sign form-control-feedback" aria-hidden="true"></span>');
    			$('#newPassword').after('<span class="help-block">Seems good, but can be better</span>');
    		} else {
    			newPass = true;
    			$('#newPassGroup').attr('class', 'form-group has-success has-feedback');
    			$('#newPassword').siblings('span').remove();
    			$('#newPassword').after('<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>');
    		}
    		if ($('#confirmPass').val().length > 0) {
    			checkConfirm();
    		}
    		checkStatus();
		}
		
		var checkConfirm = function () {
			var $val = $('#confirmPass').val();
    		var $pass = $('#newPassword').val();
    		if ($val.length == 0 || $val != $pass || passComplexity($pass) == 0) {
    			confirm = false;
    			$('#confirmGroup').attr('class', 'form-group has-error has-feedback');
    			$('#confirmPass').siblings('span').remove();
    			$('#confirmPass').after('<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>');
    			$('#confirmPass').after('<span class="help-block">This confirmation is not equal to your password</span>');
    		} else {
    			confirm = true;
    			$('#confirmGroup').attr('class', 'form-group has-success has-feedback');
    			$('#confirmPass').siblings('span').remove();
    			$('#confirmPass').after('<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>');
    		}
    		checkStatus();
		}
		
		var checkStatus = function () {
			if (oldPass && newPass && confirm) {
    			$('#submitPasswordBtn').removeAttr('disabled');
    		} else {
    			$('#submitPasswordBtn').attr('disabled', 'disabled');
    		}
		}
		
		$('#oldPassword').blur(checkOld);
		$('#newPassword').blur(checkNew);
		$('#confirmPass').blur(checkConfirm);
		
		$("#resetPasswordBtn").click(function() {
			$("#oldPassGroup").attr("class", "form-group");
			$("#newPassGroup").attr("class", "form-group");
			$("#confirmGroup").attr("class", "form-group");
			$("#oldPassword").siblings("span").remove();
			$("#newPassword").siblings("span").remove();
			$("#confirmPass").siblings("span").remove();
			$("#submitPasswordBtn").attr("disabled", "disabled");
			oldPass = false;
			newPass = false;
			confirm = false;
		});
	});
	
	$(function () {
		$("#agreement").click(function() {
			if ($("#agreement").is(":checked")) {
				$("#deleteAccount").removeAttr("disabled");
			} else {
				$("#deleteAccount").attr("disabled", "disabled");
			}
		});
	});
	
	$(function () {
    	$('#editPassForm').submit(function () {
    		$('#oldPassword').val($.md5($('#oldPassword').val()));
    		$('#newPassword').val($.md5($('#newPassword').val()));
    		$('#confirmPass').val($.md5($('#confirmPass').val()));
    	});
    });
	
})(window, document, jQuery);