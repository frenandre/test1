package system

class LoginController {

	def index() {
		redirect action: 'denied', params: params
	}

	def auth() {
		redirect action: 'denied', params: params
	}

	def denied() {
		render status: 403
	}
}
