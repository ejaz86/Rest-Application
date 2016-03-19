package com.auto.resource.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.auto.dao.UserDAO;
import com.auto.exceptions.NotAuthorizedException;

public class SecurityFilter extends AbstractAuthenticationProcessingFilter {
	private static final Log LOGGER = LogFactory.getLog(SecurityFilter.class);
	UserDAO userDAO;

	protected SecurityFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
		setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler());
		setAuthenticationManager(new CustomAuthenticationManager());
		userDAO = new UserDAO();
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		final HttpServletRequest httpRequest = (HttpServletRequest) req;
		String userName = httpRequest.getHeader("name");
		String email = httpRequest.getHeader("email");
		String userType = httpRequest.getHeader("userType");
		String path = ((HttpServletRequest) httpRequest).getRequestURI();
		if (userName == null || userName.isEmpty() || userType == null || userType.isEmpty() || email.isEmpty()
				|| email == null) {
			throw new NotAuthorizedException("No header parameters found");
		}
		if (!userDAO.isValidUser(userName, email, userType)) {
			throw new NotAuthorizedException("Invalid user");
		}
		LOGGER.info("REQUEST URL" + path);
		AbstractAuthenticationToken userAuthenticationToken = null;

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if (userType.equalsIgnoreCase("1")) {
			authorities.add(new SimpleGrantedAuthority("USER"));
		} else if (userType.equalsIgnoreCase("2")) {
			authorities.add(new SimpleGrantedAuthority("ADMIN"));
		}
		User principal = new User(userType, "", authorities);
		userAuthenticationToken = new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(userAuthenticationToken);
		super.doFilter(req, res, chain);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		return null;
	}
}