package system

class ZulController {

	def index() {
		zul(params.app, params.path, params.id, params.company)
	}

	def notfound() {
		redirect status: 404
	}

	private def zul(String app, String path, String id, String company = null, Map args = null) {
		try {
			def zul = new Zul(app, path, id, company, args)
			if (zul.print) {
				forward controller: zul.folder, action: zul.file, namespace: zul.app, id: zul.id, company: zul.company, params: zul.args
			} else {
				forward uri: zul.pageUri
			}
		} catch (e) {
			log.error e.message
		}
	}
}
