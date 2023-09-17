package spring

beans = {
	sessionServiceProxy(org.springframework.aop.scope.ScopedProxyFactoryBean) {
		targetBeanName = 'sessionService'
		proxyTargetClass = true
	}
	httpSessionServletListener(org.springframework.boot.web.servlet.ServletListenerRegistrationBean) {
		listener = bean(system.session.SessionListener)
	}
}
