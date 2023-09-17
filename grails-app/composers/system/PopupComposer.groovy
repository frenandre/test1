package system

import org.zkoss.zk.ui.Executions
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.Label
import org.zkoss.zul.Window

import groovy.transform.CompileStatic
import system.IncludeComposer

@CompileStatic
class PopupComposer extends IncludeComposer {

	def afterCompose = {
		initInclude((Window) self.parent, Executions.getCurrent().getArg())
		if (param.width)
			((Window) self).width = param.width
		if (param.height)
			((Window) self).height = param.height
		zul()
	}

	void onStatus(Event evt) {
		((Label) self.getFellow('boxStatus')).value = evt.data
	}

	void onClose(Event evt) {
	}
}
