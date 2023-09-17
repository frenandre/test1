package system

import org.zkoss.zk.ui.Executions
import org.zkoss.zk.ui.event.Event
import org.zkoss.zk.ui.util.Clients
import org.zkoss.zul.Image
import org.zkoss.zul.Label
import org.zkoss.zul.Menu
import org.zkoss.zul.Window

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import system.IncludeComposer

@Slf4j
@CompileStatic
class PageComposer extends IncludeComposer {

	def afterCompose = {
		try {
			initInclude((Window) self, toMap(Executions.getCurrent().getParameterMap()))
			initPage()
			initWindow()
			zul()
		} catch (Exception e) {
			log.error e.message
			self.detach()
		}
	}

	void onClose(Event evt) {
		Clients.evalJavaScript('window.close()')
	}

	void onStatus(Event evt) {
		((Label) getFellow('boxStatus')).value = evt.data
	}

	void onClick_mnuLogout() {
		Executions.sendRedirect('/logout')
	}

	private void initWindow() {
		if (!page.title) page.title = app.toUpperCase()
		((Image) getFellow('imgLogo')).src = '/static/images/' + sessionService.getCompanyLogo(company)
		((Menu) getFellow('boxUser')).label = sessionService.getUser().description
		((Label) getFellow('boxCompany')).value = sessionService.getCompany(company)?.description
	}

	private Map toMap(Map<String, String[]> map) {
		Map result = [:]
		map.keySet().each { result[it] = map.get(it)[0] }
		return result
	}
}
