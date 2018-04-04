package com.angelorobson.alternativescene.security.services;

import com.angelorobson.alternativescene.entities.UserApp;
import com.angelorobson.alternativescene.security.JwtUserFactory;
import com.angelorobson.alternativescene.services.UserAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserAppService userAppService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserApp> user = userAppService.findByEmail(username);

		if (user.isPresent()) {
			return JwtUserFactory.create(user.get());
		}

		throw new UsernameNotFoundException("Email not found.");
	}

}
