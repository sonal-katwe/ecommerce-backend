package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginRequest;
import com.example.demo.entity.User;
import com.example.demo.service.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/api/auth")
public class AuthController {
	 private final AuthService authService;

	    public AuthController(AuthService authService) {
	        this.authService = authService;
	    }

	    @PostMapping("/login")
	    @CrossOrigin
	    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
	        try {
	            // Authenticate user
	            User user = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());

	            // Generate token and set as cookie
	            String token = authService.generateToken(user);
	            Cookie cookie = new Cookie("authToken", token);
	            cookie.setHttpOnly(true);
	            cookie.setSecure(false); // Set to true if using HTTPS
	            cookie.setPath("/");
	            cookie.setMaxAge(3600); // 1 hour
	            cookie.setDomain("localhost");
	            response.addCookie(cookie);

	            // Optional: Add "Set-Cookie" header
	            response.addHeader("Set-Cookie", String.format("authToken=%s; HttpOnly; Path=/; Max-Age=3600; SameSite=None", token));

	            // Prepare response body
	            Map<String, Object> responseBody = new HashMap<>();
	            responseBody.put("message", "Login successful");
	            responseBody.put("role", user.getRole().name());
	            responseBody.put("username", user.getUsername());

	            return ResponseEntity.ok(responseBody);
	        } catch (RuntimeException e) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
	        }
	    }

	    @PostMapping("/logout")
	    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request, HttpServletResponse response) {
	        try {
	            // Retrieve the authenticated user
	            User user = (User) request.getAttribute("authenticatedUser");

	            // Perform logout
	            authService.logout(user);

	            // Clear authentication cookie
	            Cookie cookie = new Cookie("authToken", null);
	            cookie.setHttpOnly(true);
	            cookie.setMaxAge(0); // Immediately expire the cookie
	            cookie.setPath("/");
	            response.addCookie(cookie);

	            // Prepare response body
	            Map<String, String> responseBody = new HashMap<>();
	            responseBody.put("message", "Logout successful");

	            return ResponseEntity.ok(responseBody);
	        } catch (RuntimeException e) {
	            Map<String, String> errorResponse = new HashMap<>();
	            errorResponse.put("message", "Logout failed");
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	        }
	    }
	}


