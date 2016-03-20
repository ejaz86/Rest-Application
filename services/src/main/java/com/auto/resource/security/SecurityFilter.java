package com.auto.resource.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
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
import com.auto.dto.UserDTO;
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
		String uid = httpRequest.getHeader("uid");
		String email = httpRequest.getHeader("email");
		String userType = httpRequest.getHeader("userType");
		String token = httpRequest.getHeader("access-token");
		String path = ((HttpServletRequest) httpRequest).getRequestURI();
		if (uid == null || uid.isEmpty() || userType == null || userType.isEmpty() || email.isEmpty() || email == null
				|| token == null || token.isEmpty()) {
			throw new NotAuthorizedException("No header parameters found");
		}
		LOGGER.info("REQUEST URL" + path);
		AbstractAuthenticationToken userAuthenticationToken = null;
		if (Integer.parseInt(userType) == 1) {
			userAuthenticationToken = authUserByToken(token, uid, email);
		} else if (Integer.parseInt(userType) == 2) {
			userAuthenticationToken = authAdminByToken(token, uid, email);
		}
		if (userAuthenticationToken == null)
			throw new AuthenticationServiceException("Invalid headers");
		SecurityContextHolder.getContext().setAuthentication(userAuthenticationToken);
		super.doFilter(req, res, chain);
	}

	private AbstractAuthenticationToken authAdminByToken(String token, String uid, String email) {
		AbstractAuthenticationToken authToken = null;
		try {
			UserDTO userDto = userDAO.getUserbyId(new Integer(uid), email);
			if (userDto == null) {
				return authToken;
			}
			LOGGER.info("userProfile" + userDto.toString());
			String base = userDto.getfName() + userDto.getEmail() + userDto.getUid();
			String key = String.valueOf(userDto.getUid()) + userDto.getfName();
			if (!token.equals(computeSignature(base, key))) {
				LOGGER.error("Token mis-match");
				return authToken;
			}
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("ADMIN"));
			User principal = new User(String.valueOf(userDto.getUid()), "", authorities);
			authToken = new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities());
			LOGGER.info("user Id : " + uid + "validated successfully");
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			LOGGER.error("Error in authAdminByToken :", e);
			return authToken;
		}
		return authToken;
	}

	private AbstractAuthenticationToken authUserByToken(String token, String uid, String email) {
		AbstractAuthenticationToken authToken = null;
		try {
			UserDTO userDto = userDAO.getUserbyId(new Integer(uid), email);
			if (userDto == null) {
				return authToken;
			}
			LOGGER.info("userProfile" + userDto.toString());
			String base = userDto.getfName() + userDto.getEmail() + userDto.getUid();
			String key = String.valueOf(userDto.getUid()) + userDto.getfName();
			if (!token.equals(computeSignature(base, key))) {
				LOGGER.error("Token mis-match");
				return authToken;
			}
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("USER"));
			User principal = new User(String.valueOf(userDto.getUid()), "", authorities);
			authToken = new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities());
			LOGGER.info("user Id : " + uid + "validated successfully");
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			LOGGER.error("Error in authUserByToken :", e);
			return authToken;
		}
		return authToken;
	}

	private String computeSignature(String baseString, String keyString)
			throws GeneralSecurityException, UnsupportedEncodingException {
		SecretKey secretKey = null;
		byte[] keyBytes = keyString.getBytes();
		secretKey = new SecretKeySpec(keyBytes, "HmacMD5");
		Mac mac = Mac.getInstance("HmacMD5");
		mac.init(secretKey);
		byte[] text = baseString.getBytes();
		byte[] rawHmac = mac.doFinal(text);
		return Hex.encodeHexString(rawHmac);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		// TODO Auto-generated method stub
		return null;
	}
}