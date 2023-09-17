package system

import grails.plugin.springsecurity.SpringSecurityUtils

class LogoutController {

	def index = {
		session.invalidate()
		redirect uri: SpringSecurityUtils.securityConfig.logout.filterProcessesUrl
	}
}