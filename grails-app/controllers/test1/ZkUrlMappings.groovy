package test1

class ZkUrlMappings {

	static excludes = [
		'/zkau/*',
		'/zkcomet/*',
		'/static/**',
		'/assets/**',
		'/images/**',
		'/css/**',
		'/js/**',
		'/favicon.ico'
	]

	static mappings = {
		"/$app"(controller:'zul', action:'index')
		"/$app:$id"(controller:'zul', action:'index')
		"/$app/$path**"(controller:'zul', action:'index')
		"/$app/$path**:$id"(controller:'zul', action:'index')
	}
}
