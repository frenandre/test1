package system

import org.zkoss.zk.ui.Executions
import org.zkoss.zul.Window

import groovy.transform.CompileStatic
import system.IncludeComposer

@CompileStatic
class PanelComposer extends IncludeComposer {

	def afterCompose = {
		initInclude((Window) self.parent, Executions.getCurrent().getArg())
		zul()
	}
}
