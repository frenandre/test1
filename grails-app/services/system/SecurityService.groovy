package system

import org.springframework.security.core.Authentication
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator

import grails.plugin.springsecurity.SpringSecurityService

class SecurityService {

	static transactional = false

	SpringSecurityService springSecurityService
	WebInvocationPrivilegeEvaluator webInvocationPrivilegeEvaluator

	String getUserId() {
		if (springSecurityService.loggedIn)
			return springSecurityService.principal.id
		else
			return null
	}

	Authentication getAuthentication() {
		if (springSecurityService.loggedIn)
			return springSecurityService.authentication
		else
			return null
	}

	boolean isAllowed(String url, Authentication authentication) {
		webInvocationPrivilegeEvaluator.isAllowed(url, authentication)
	}
}
