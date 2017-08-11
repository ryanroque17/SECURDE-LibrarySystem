package com.example.configuration;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

@Component
public class SimpleAuthenticationFailureHandler implements AuthenticationFailureHandler{
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
//	public SimpleAuthenticationFailureHandler (String defaultFailureUrl) {
//        super();
//    }

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		String message = "";

	    if(exception instanceof UsernameNotFoundException) {
	        message = "UsernameNotFoundException";
	    } else if(exception instanceof AuthenticationCredentialsNotFoundException) {
	        message = "AuthenticationCredentialsNotFoundException";
	    }else if(exception instanceof InsufficientAuthenticationException) {
	        message = "InsufficientAuthenticationException";
	    }else if(exception instanceof AccountExpiredException) {
	        message = "AccountExpiredException";
	    }else if(exception instanceof CredentialsExpiredException) {
	        message = "CredentialsExpiredException";
	    }else if(exception instanceof DisabledException) {
	        message = "DisabledException";
	    }else if(exception instanceof LockedException) {
	        message = "LockedException";
	    }else if(exception instanceof BadCredentialsException) {
	        message = "BadCredentialsException";
	    }else{
	        message = exception.getMessage();
	    }
	    System.out.println(message);
	    final HttpSession session = request.getSession();
	    session.setAttribute("errorMessage", message);
	    redirectStrategy.sendRedirect(request, response, "/login?error="+message);
	}
}
