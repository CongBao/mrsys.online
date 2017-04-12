/*!
 * index.js
 * Author: Cong Bao
 * Date: 11 April, 2017
 */

if (typeof jQuery === 'undefined') {
    throw new Error('jQuery required');
}

(function (win, doc, $, undefined) {

    $(function () {
        $(win).scroll(function () {
            if ($(win).scrollTop() >= 100) {
                $('#navbar > div').css('width', $(win).innerWidth() * 0.9);
            } else {
                $('#navbar > div').css('width', '');
            }
        });
    });
    
    // switch login form and register form
    $(function () {
    	$('#changeBtn').click(function () {
    		$('#loginModal').modal('hide');
    		$('#registerModal').modal('show');
    	});
    	$('#loginModal').on('hidden.bs.modal', function (e) {
    		$('body').removeAttr('style class');
    	});
    	$('#registerModal').on('hidden.bs.modal', function (e) {
    		$('body').removeAttr('style class');
    	});
    });
    
    // register form check
    $(function () {
    	var account = false;
    	var email = false;
    	var pass = false;
    	var confirm = false;
    	
    	// check if this account is exist
    	var checkAccount = function () {
    		$.ajax({
    			cache: false,
    			type: 'post',
    			url: 'ajax/accountExist',
    			data: { account: $('#registerAccount').val() },
    			success: function (data, statusText) {
    				if (data.exist == true) {
    					account = false;
    					$('#registerAccountGroup').attr('class', 'form-group has-error has-feedback');
    					$('#registerAccount').siblings('span').remove();
    					$('#registerAccount').after('<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>');
    					$('#registerAccount').after('<span class="help-block">This account has been registered</span>');
    				} else {
    					account = true;
    					$('#registerAccountGroup').attr('class', 'form-group has-success has-feedback');
    					$('#registerAccount').siblings('span').remove();
    					$('#registerAccount').after('<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>');
    				}
    			}
    		});
    		checkStatus();
    	}
    	
    	// check if email is valid
    	var checkEmail = function () {
    		var $val = $('#registerEmail').val();
    		var regex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    		if (!regex.test($val)) {
    			email = false;
    			$('#registerEmailGroup').attr('class', 'form-group has-error has-feedback');
				$('#registerEmail').siblings('span').remove();
				$('#registerEmail').after('<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>');
				$('#registerEmail').after('<span class="help-block">Invalid email format</span>');
    		} else {
    			$.ajax({
    				cache: false,
    				type: 'post',
    				url: 'ajax/emailExist',
    				data: { email: $val },
    				success: function (data, statusText) {
    					if (data.exist == true) {
    						email = false;
    						$('#registerEmailGroup').attr('class', 'form-group has-error has-feedback');
    						$('#registerEmail').siblings('span').remove();
    						$('#registerEmail').after('<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>');
    						$('#registerEmail').after('<span class="help-block">This email address has been registered</span>');
    					} else {
    						email = true;
    						$('#registerEmailGroup').attr('class', 'form-group has-success has-feedback');
        					$('#registerEmail').siblings('span').remove();
        					$('#registerEmail').after('<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>');
    					}
    				}
    			});
    		}
    		checkStatus();
    	}
    	
    	// check the complexity of password
    	var passComplexity = function (pass) {
    		var com = 0;
    		if (pass.length < 6) return 0;
    		if (pass.match(/[a-z]+/)) com++;
    		if (pass.match(/[A-Z]+/)) com++;
    		if (pass.match(/[0-9]+/)) com++;
    		if (pass.match(/[^a-zA-Z0-9]+/)) com++;
    		return com;
    	}
    	
    	// check if password is valid
    	var checkPass = function () {
    		var $val = $('#registerPassword').val();
    		var $lv = passComplexity($val);
    		if ($lv == 0) {
    			pass = false;
    			$('#registerPassGroup').attr('class', 'form-group has-error has-feedback');
    			$('#registerPassword').siblings('span').remove();
    			$('#registerPassword').after('<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>');
    			$('#registerPassword').after('<span class="help-block">Your password is too simple</span>');
    		} else if ($lv > 0 && $lv < 3) {
    			pass = true;
    			$('#registerPassGroup').attr('class', 'form-group has-warning has-feedback');
    			$('#registerPassword').siblings('span').remove();
    			$('#registerPassword').after('<span class="glyphicon glyphicon-warning-sign form-control-feedback" aria-hidden="true"></span>');
    			$('#registerPassword').after('<span class="help-block">Seems good, but can be better</span>');
    		} else {
    			pass = true;
    			$('#registerPassGroup').attr('class', 'form-group has-success has-feedback');
    			$('#registerPassword').siblings('span').remove();
    			$('#registerPassword').after('<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>');
    		}
    		if ($('#confirmPassword').val().length > 0) {
    			checkConfirm();
    		}
    		checkStatus();
    	}
    	
    	// check the confirmation of password
    	var checkConfirm = function () {
    		var $val = $('#confirmPassword').val();
    		var $pass = $('#registerPassword').val();
    		if ($val.length == 0 || $val != $pass || passComplexity($pass) == 0) {
    			confirm = false;
    			$('#passConfirmGroup').attr('class', 'form-group has-error has-feedback');
    			$('#confirmPassword').siblings('span').remove();
    			$('#confirmPassword').after('<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>');
    			$('#confirmPassword').after('<span class="help-block">This confirmation is not equal to your password</span>');
    		} else {
    			confirm = true;
    			$('#passConfirmGroup').attr('class', 'form-group has-success has-feedback');
    			$('#confirmPassword').siblings('span').remove();
    			$('#confirmPassword').after('<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>');
    		}
    		checkStatus();
    	}
    	
    	var checkStatus = function () {
    		if (account && email && pass && confirm) {
    			$('#registerSubmitBtn').removeAttr('disabled');
    		} else {
    			$('#registerSubmitBtn').attr('disabled', 'disabled');
    		}
    	}
    	
    	$('#registerAccount').blur(checkAccount);
    	$('#registerEmail').blur(checkEmail);
    	$('#registerPassword').blur(checkPass);
    	$('#confirmPassword').blur(checkConfirm);
    	
    	$('#registerResetBtn').click(function () {
    		$('.form-group.has-error.has-feedback').attr('class', 'form-group');
    		$('.form-group.has-warning.has-feedback').attr('class', 'form-group');
    		$('.form-group.has-success.has-feedback').attr('class', 'form-group');
    		$('.col-sm-8').children('span').remove();
    		$('#registerSubmitBtn').attr('disabled', 'disabled');
    		account = false;
        	email = false;
        	pass = false;
        	confirm = false;
    	});
    });

})(window, document, jQuery);