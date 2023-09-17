server.port = 8080

grails.plugin.springsecurity.interceptUrlMap = [
	[pattern: '/company/**',     access: ['permitAll']],
	[pattern: '/user/**',        access: ['permitAll']],
	[pattern: '/role/**',        access: ['permitAll']],
	[pattern: '/init/**',        access: ['permitAll']],

	[pattern: '/book/**',        access: ['permitAll']],
	[pattern: '/author/**',      access: ['permitAll']],

	[pattern: '/test1/**',       access: ['isAuthenticated()']],

	[pattern: '/',               access: ['permitAll']],
	[pattern: '/error',          access: ['permitAll']],
	[pattern: '/index',          access: ['permitAll']],
	[pattern: '/index.gsp',      access: ['permitAll']],
	[pattern: '/assets/**',      access: ['permitAll']],
	[pattern: '/**/js/**',       access: ['permitAll']],
	[pattern: '/**/css/**',      access: ['permitAll']],
	[pattern: '/**/images/**',   access: ['permitAll']],
	[pattern: '/**/favicon.ico', access: ['permitAll']],
	[pattern: '/login',          access: ['permitAll']],
	[pattern: '/login/**',       access: ['permitAll']],
	[pattern: '/logout',         access: ['permitAll']],
	[pattern: '/logout/**',      access: ['permitAll']],
	[pattern: '/zkau/**',        access: ['permitAll']],
 ]

// spring security core
grails.plugin.springsecurity.securityConfigType = 'InterceptUrlMap'
grails.plugin.springsecurity.authority.className = 'system.Role'
grails.plugin.springsecurity.userLookup.userDomainClassName = 'system.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'system.UserRole'
grails.plugin.springsecurity.useSessionFixationPrevention = false

// spring security cas
grails.plugin.springsecurity.cas.active = true
grails.plugin.springsecurity.cas.loginUri = '/login'
grails.plugin.springsecurity.cas.filterProcessesUrl = '/cas'
grails.plugin.springsecurity.cas.key = 'bjk235sdj2'
grails.plugin.springsecurity.cas.serviceUrl = 'http://dev.local:8080/cas'
grails.plugin.springsecurity.cas.serverUrlPrefix = 'https://local.asmb.it/cas'
grails.plugin.springsecurity.cas.useSingleSignout = true
