package system

import org.springframework.security.core.Authentication
import org.springframework.web.context.request.RequestContextHolder

import system.Company
import system.Role
import system.User

class SessionService {

	static scope = 'session'

	static transactional = false

	SecurityService securityService

	private User _user
	private Company _company
	private Set<Role> _authorities
	private Authentication _authentication

	User getUser() {
		if (_user == null) {
			if (securityService.userId) {
				_user = User.read(securityService.userId)
				_authorities = _user.authorities.collect()
				RequestContextHolder.currentRequestAttributes().getSession().setAttribute('userName', _user.username)
			}
		}
		return _user
	}

	Authentication getAuthentication() {
		if (_authentication == null) {
			_authentication = securityService.authentication
		}
		return _authentication
	}

	Company getCompany(String code = null) {
		if (user == null) {
			return null
		}
		if (code == null || code == _user.company.code) {
			return _user.company
		}
		if (_company == null || code != _company.code) {
			_company = Company.findByCode(code)
		}
		return _company
	}

	String getCompanyLogo(String code = null) {
		return getCompany(code).logo ?: 'logo.gif'
	}

	boolean isAllowed(String company, String app, String path) {
		String url = (path.startsWith('/')) ? path : "/$app/$path"
		if (company) url = "/$company".toLowerCase() + url
		return isAllowed(url)
	}

	boolean isAllowed(String url) {
		return securityService.isAllowed(url, authentication)
	}

	boolean hasUserRole(String role) {
		return _authorities.any { Role it -> it.authority == role }
	}

	boolean hasUserAdminRole() {
		return _authorities.any { Role it -> it.authority == Constants.UserRole.ADMIN }
	}
}
