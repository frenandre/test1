package system

trait SessionController {

	SessionService sessionServiceProxy

	User getUser() {
		return sessionServiceProxy.user
	}

	Company getCompany() {
		return sessionServiceProxy.company
	}
}
