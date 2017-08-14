package com.example.configuration;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.example.model.User;
import com.example.service.UserService;

@Component
public class SimpleAuthenticationFailureHandler implements AuthenticationFailureHandler{
	@Autowired
	private UserService userService;
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
//	public SimpleAuthenticationFailureHandler (String defaultFailureUrl) {
//        super();
//    }

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		String message = "";
		Date today = new Date();
		User user = userService.findUserByEmail(request.getParameter("email"));

		if(user == null)
		{
			System.out.println("USER NOT EXIST");
			redirectStrategy.sendRedirect(request, response, "/login?error=true");
		}
		else if(user.getlockout_time() != null && user.getlockout_time().after(today)) {
			message = "Account has been lockout";
			redirectStrategy.sendRedirect(request, response, "/login?error1=true");
		}
		else if(exception instanceof BadCredentialsException) {
	        message = "BadCredentialsException";
	        userService.recordLoginFailure(user);
	        redirectStrategy.sendRedirect(request, response, "/login?error=true");
	    }
		else{
	        message = exception.getMessage();
	    }
	    
	    System.out.println(message);
//	    final HttpSession session = request.getSession();
//	    session.setAttribute("errorMessage", message);
	}
}
