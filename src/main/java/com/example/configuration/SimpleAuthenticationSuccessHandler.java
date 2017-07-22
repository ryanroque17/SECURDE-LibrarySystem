package com.example.configuration;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class SimpleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
		public void onAuthenticationSuccess(HttpServletRequest arg0, HttpServletResponse arg1, Authentication authentication)
				throws IOException, ServletException {
			
			Collection <GrantedAuthority> authorities = (Collection<GrantedAuthority>) authentication.getAuthorities();
			authorities.forEach(authority -> {
				if(authority.getAuthority().equals("STUDENT") || authority.getAuthority().equals("FACULTY")) {
					try {
						System.out.println("AUTH " + authority.getAuthority());
						redirectStrategy.sendRedirect(arg0, arg1, "/library/home");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(authority.getAuthority().equals("LIBRARY_STAFF")){
					try {
						redirectStrategy.sendRedirect(arg0, arg1, "/employee/staff/home");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(authority.getAuthority().equals("LIBRARY_MANAGER")){
					try {
						redirectStrategy.sendRedirect(arg0, arg1, "/employee/manager/home");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(authority.getAuthority().equals("ADMIN")) {
					try {
						redirectStrategy.sendRedirect(arg0, arg1, "/admin/home");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
		            throw new IllegalStateException();
		        }
			});
			
		}

}
