package io.pivotal.demo;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SampleController {

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "open")
	public String open(Authentication auth, Principal principal) {
		System.out.println(auth.getName());
		System.out.println(principal);
		return "home"; 
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "close")
	public String close() {
		return "home"; 
	}

}
