package system

import grails.plugin.springsecurity.SpringSecurityUtils

class LogoutController {

	def index = {
		request.logout()
		redirect uri: SpringSecurityUtils.securityConfig.logout.filterProcessesUrl
	}
}