package com.example.configuration;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.model.User;
import com.example.service.UserService;

@Component
public class SimpleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	@Autowired
	private UserService userService;
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
		public void onAuthenticationSuccess(HttpServletRequest arg0, HttpServletResponse arg1, Authentication authentication)
				throws IOException, ServletException {
		System.out.println(arg0.getParameter("email"));
		User user = userService.findUserByEmail(arg0.getParameter("email"));
		Date today = new Date();
		Collection <GrantedAuthority> authorities = (Collection<GrantedAuthority>) authentication.getAuthorities();
			
			if(user.getlockout_time() != null && user.getlockout_time().after(new Date()))
			{
				String message = "Account has been lockout";
				System.out.println(message);
				redirectStrategy.sendRedirect(arg0 , arg1, "/login?error1=true");
			}
			else
			{
				userService.recordLoginSuccess(user);
				
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

}
