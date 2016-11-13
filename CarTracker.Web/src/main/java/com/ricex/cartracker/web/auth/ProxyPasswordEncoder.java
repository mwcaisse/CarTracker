package com.ricex.cartracker.web.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ricex.cartracker.data.util.PasswordHasher;

/** Proxy class that Converts a PasswordEncoder into a PasswordHasher, but still
 * 		servers as a Password Encoder
 * 
 * @author Mitchell
 *
 */

public class ProxyPasswordEncoder implements PasswordHasher, PasswordEncoder {
	
	private final PasswordEncoder passwordEncoder;

	public ProxyPasswordEncoder() {
		this(new BCryptPasswordEncoder());
	}
	
	public ProxyPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public String hashPassword(String password) {
		return passwordEncoder.encode(password);
	}

	@Override
	public String encode(CharSequence rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
	
}
