$(function() {

    $('#login-form-link').click(function(e) {
		$("#login-form").delay(100).fadeIn(100);
 		$("#register-form").fadeOut(100);
		$('#register-form-link').removeClass('active');
		$(this).addClass('active');
		e.preventDefault();
	});
	$('#register-form-link').click(function(e) {
		$("#register-form").delay(100).fadeIn(100);
 		$("#login-form").fadeOut(100);
		$('#login-form-link').removeClass('active');
		$(this).addClass('active');
		e.preventDefault();
	});
	
	var strength = {
	        0: "Worst ☹",
	        1: "Bad ☹",
	        2: "Weak ☹",
	        3: "Good ☺",
	        4: "Strong ☻"
	}

	var password = document.getElementById('password');
	var meter = document.getElementById('password-strength-meter');
	var text = document.getElementById('password-strength-text');

	password.addEventListener('input', function()
	{
	    var val = password.value;
	    var result = zxcvbn(val);
	    
	    // Update the password strength meter
	    meter.value = result.score;
	   
	    // Update the text indicator "Strength: " + "<strong>" + strength[result.score] + "</strong>" + 
	    if(val !== "") {
	        text.innerHTML ="<span class='feedback'>" + strength[result.score] + " : " + result.feedback.warning + " " + result.feedback.suggestions + "</span"; 
	    }
	    else {
	        text.innerHTML = "";
	    }
	});

});
