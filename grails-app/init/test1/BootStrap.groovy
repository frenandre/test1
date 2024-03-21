package test1

import javax.servlet.http.HttpSession

import system.Company
import system.Role
import system.User
import system.UserRole
import system.session.SessionDynamicMethods

class BootStrap {

	def init = { servletContext ->
		HttpSession.metaClass.getTempDir = {
			SessionDynamicMethods.getTempDir(delegate)
		}
		HttpSession.metaClass.createTempFile = {String prefix, String suffix ->
			SessionDynamicMethods.createTempFile(delegate, prefix, suffix)
		}

		User.withTransaction {
			def company = Company.read(1) ?: new Company(description: 'admin', code: '00').save(flush: true)
			def adminRole = Role.read(1) ?: new Role(authority: 'admin').save(flush: true)
			def adminUser = User.read(1) ?: new User(username: 'admin', firstName: 'admin', company: company, enabled: true).save(flush: true)
			def adminUserRole = adminUser.authorities[0] ?: UserRole.create(adminUser, adminRole, true)
		}

	}

	def destroy = {
	}
}
