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
    			url: 'AccountExist',
    			data: { account: $('#registerAccount').val() },
    			success: function (data, statusText) {
    				if (data.exist == true) {
    					$('#registerAccountGroup').attr('class', 'form-group has-error has-feedback');
    					$('#registerAccount').siblings('span').remove();
    					$('#registerAccount').after('<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>');
    				} else {
    					$('#registerAccountGroup').attr('class', 'form-group has-success has-feedback');
    					$('#registerAccount').siblings('span').remove();
    					$('#registerAccount').after('<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>');
    				}
    			}
    		});
    	}
    	
    	var checkEmail = function () {
    		var $val = $('#registerEmail').val();
    		var regex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    		if (!regex.test($val)) {
    			$('#registerEmailGroup').attr('class', 'form-group has-error has-feedback');
				$('#registerEmail').siblings('span').remove();
				$('#registerEmail').after('<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>');
    		} else {/*
    			$.ajax({
    				cache: false,
    				type: 'post',
    				url: 'EmailExist',
    				data: { email: $val },
    				success: function (data, statusText) {
    					if (data.exist == true) {
    						$('#registerEmailGroup').attr('class', 'form-group has-error has-feedback');
    						$('#registerEmail').siblings('span').remove();
    						$('#registerEmail').after('<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>');
    					} else {
    						$('#registerEmailGroup').attr('class', 'form-group has-success has-feedback');
        					$('#registerEmail').siblings('span').remove();
        					$('#registerEmail').after('<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>');
    					}
    				}
    			});*/
    		}
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
    	
    	// check if password is valid
    	var checkPass = function () {
    		var $val = $('#registerPassword').val();
    		var $lv = passComplexity($val);
    		if ($lv == 0) {
    			$('#registerPassGroup').attr('class', 'form-group has-error has-feedback');
    			$('#registerPassword').siblings('span').remove();
    			$('#registerPassword').after('<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>');
    		} else if ($lv > 0 && $lv < 3) {
    			$('#registerPassGroup').attr('class', 'form-group has-warning has-feedback');
    			$('#registerPassword').siblings('span').remove();
    			$('#registerPassword').after('<span class="glyphicon glyphicon-warning-sign form-control-feedback" aria-hidden="true"></span>');
    		} else {
    			$('#registerPassGroup').attr('class', 'form-group has-success has-feedback');
    			$('#registerPassword').siblings('span').remove();
    			$('#registerPassword').after('<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>');
    		}
    	}
    	
    	// check the confirmation of password
    	var checkConfirm = function () {
    		var $val = $('#confirmPassword').val();
    		var $pass = $('#registerPassword').val();
    		if ($val.length == 0 || $val != $pass || passComplexity($pass) == 0) {
    			$('#passConfirmGroup').attr('class', 'form-group has-error has-feedback');
    			$('#confirmPassword').siblings('span').remove();
    			$('#confirmPassword').after('<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>');
    		} else {
    			$('#passConfirmGroup').attr('class', 'form-group has-success has-feedback');
    			$('#confirmPassword').siblings('span').remove();
    			$('#confirmPassword').after('<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>');
    		}
    	}
    	
    	$('#registerAccount').blur(checkAccount);
    	$('#registerEmail').blur(checkEmail);
    	$('#registerPassword').blur(checkPass);
    	$('#confirmPassword').blur(checkConfirm);
    });

})(window, document, jQuery);