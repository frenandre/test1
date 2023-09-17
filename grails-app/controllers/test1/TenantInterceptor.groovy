package test1

class TenantInterceptor {

	TenantInterceptor() {
		matchAll()
	}

	boolean before() {
		session['gorm.tenantId'] = 1 as Short
		true
	}

	boolean after() {
		true
	}

	void afterView() {
		// no-op
	}
}
