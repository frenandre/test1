package test1

class UrlMappings {

	static mappings = {

		"/"(view:"/index")
		"500"(view:'/error')
		"404"(view:'/notFound')

		"/login/$action?"(controller: 'login')
		"/logout"(controller: 'logout')

		"/user/$action?/$id?(.$format)?"(controller: 'user')
		"/role/$action?/$id?(.$format)?"(controller: 'role')
		"/company/$action?/$id?(.$format)?"(controller: 'company')

		"/book/$action?/$id?(.$format)?"(controller: 'book')
		"/author/$action?/$id?(.$format)?"(controller: 'author')
	}
}
