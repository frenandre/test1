package system

import java.nio.charset.StandardCharsets

import groovy.transform.CompileStatic

@CompileStatic
class Zul {

	final static String PAGE = '/system/page.zul'
	final static String POPUP = '/system/popup.zul'
	final static String PANEL = '/system/panel.zul'

	private String app
	private String path
	private String folder
	private String file
	private String id
	private String company
	private Map args

	private Zul() {}

	Zul(String app, String path, String id = null, String company = null, Map args = null) {
		if (!path || path == 'index') {
			path = ''
		} else {
			if (path.startsWith('/')) {
				def paths = path.split('/')
				if (paths) {
					app = paths[1]
					path = path.substring(app.length() + 2)
				}
			}
			def i = path.indexOf(':')
			if (i >= 0) {
				id = path.substring(i + 1)
				path = path.substring(0, i)
			}
		}

		def paths = path.split('/').toList()
		if (paths.size() == 1) {
			this.file = (id ? (paths[0] ?: app) : 'index')
			this.folder = paths[0]
		} else {
			this.file = paths.removeLast()
			this.folder = paths.join('/')
		}

		this.path = path
		this.app = app
		this.id = id
		this.company = company?.toUpperCase()
		this.args = args ?: [:]
	}

	String getApp() {
		return app
	}

	String getPath() {
		return path
	}

	String getId() {
		return id
	}

	String getCompany() {
		return company
	}

	Map getArgs() {
		return args
	}

	boolean isPrint() {
		return 'print' == folder
	}

	String getAbsolutePath() {
		return '/' + "$app/$path".split('/').join('/') + '/'
	}

	String getSrc() {
		return '/' + ([app, folder, file].findAll { it }.join('/')) + '.zul'
	}

	Map<String, String> getParams() {
		return ([app: app, path: path, id: id, company: company] << args).findAll { it.value }
	}

	String getQueryString() {
		return params.collect { "${it.key}=${encode(it.value)}" }.join('&')
	}

	String getPageUri() {
		return "$PAGE?$queryString"
	}

	@Override
	String toString() {
		return "/$app/$path" + (id ? ":$id" : '')
	}

	private String encode(String value) {
		return URLEncoder.encode(value, StandardCharsets.UTF_8.toString())
	}
}
