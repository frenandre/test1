package system

import org.zkoss.zk.ui.Component
import org.zkoss.zul.Include
import org.zkoss.zul.Window

import groovy.transform.CompileStatic

@CompileStatic
abstract class IncludeComposer extends BaseComposer {

	private Include include
	private Window main
	protected Map param

	protected initInclude(Window main, Map param) {
		this.include = (Include) getFellow('include')
		this.main = main
		this.param = param.asUnmodifiable()
		this.include.setDynamicProperty('main', this.main)
		this.include.setDynamicProperty('param', this.param)
		this.include.setDynamicProperty('window', self)
	}

	protected initPage() {
		main.setAttribute('app', param.app)
		main.setAttribute('company', param.company)
	}

	protected String getApp() {
		return main.getAttribute('app')
	}

	protected String getCompany() {
		return main.getAttribute('company')
	}

	protected Component getFellow(String id) {
		return self.getFellow(id)
	}

	protected void zul() {
		Zul zul = new Zul(app, (String) param.path, (String) param.id)
		if (sessionService.isAllowed(company, app, zul.absolutePath))
			include.src = zul.src
	}
}
