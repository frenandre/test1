package test1

import javax.servlet.http.HttpSession

import system.session.SessionDynamicMethods

class BootStrap {

	def init = { servletContext ->
		HttpSession.metaClass.getTempDir = {
			SessionDynamicMethods.getTempDir(delegate)
		}
		HttpSession.metaClass.createTempFile = {String prefix, String suffix ->
			SessionDynamicMethods.createTempFile(delegate, prefix, suffix)
		}
	}

	def destroy = {
	}
}
