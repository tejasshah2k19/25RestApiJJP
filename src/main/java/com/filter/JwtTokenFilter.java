package com.filter;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.util.JwtUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//controller repository service component  
@Component
public class JwtTokenFilter implements Filter {

	@Autowired
	JwtUtil jwt;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		System.out.println("JwtTokenFilter");

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String url = req.getRequestURL().toString();
		System.out.println("URL => " + url);

		if (url.contains("/public/")) {
			chain.doFilter(request, response);
		} else {
			// private
			// token
			String token = req.getHeader("token");
			if (jwt.isTokenValid(token) == false) {
				// unauthorized

				PrintWriter out = res.getWriter();
				out.print("Invalid Token");

			} else {
				chain.doFilter(request, response);// go ahead
			}
		}

	}
}
