package system

import org.springframework.context.MessageSourceResolvable
import org.springframework.context.support.DefaultMessageSourceResolvable
import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.Executions
import org.zkoss.zk.ui.Page
import org.zkoss.zk.ui.event.Events
import org.zkoss.zk.ui.metainfo.ComponentInfo
import org.zkoss.zk.ui.util.Clients
import org.zkoss.zul.Messagebox
import org.zkoss.zul.Window

import groovy.transform.CompileStatic

@CompileStatic
abstract class AppComposer extends BaseComposer {

	private Map param
	private Window window
	private Window main
	private String company

	@Override
	ComponentInfo doBeforeCompose(Page page, Component parent, ComponentInfo info) {
		param = (Map) Executions.getCurrent().getArg().get('param')
		window = (Window) Executions.getCurrent().getArg().get('window')
		main = (Window) Executions.getCurrent().getArg().get('main')
		company = main.getAttribute('company')
		return super.doBeforeCompose(page, parent, info)
	}

	void onCancel() {
		cancel()
	}

	User getUser() {
		return sessionService.getUser()
	}

	Company getCompany() {
		return sessionService.getCompany(company)
	}

	String getCompanyCode() {
		return sessionService.getCompany(company).code
	}

	String getCompanyErp() {
		return sessionService.getCompany(company).erp
	}

	protected Object getParameter(String key) {
		return param.get(key)
	}

	protected Object getParameterId() {
		return param.get('id')
	}

	protected Object getParameterInstance() {
		return param.get('instance')
	}

	protected boolean hasParameter(String key) {
		return param.containsKey(key)
	}

	protected String getWindowTitle() {
		return ((Window) self).title
	}

	protected void setWindowTitle(String value) {
		((Window) self).title = value
	}

	protected String getWindowWidth() {
		return window.width
	}

	protected void setWindowWidth(String value) {
		window.width = value
	}

	protected String getPageTitle() {
		return self.page.title
	}

	protected void setPageTitle(String value) {
		self.page.title = value
	}

	protected void setStatusMessage(String value) {
		postEvent('onStatus', value)
	}

	protected boolean hasUserRole(String role) {
		return sessionService.hasUserRole(role)
	}

	protected Component createPopup(String path) {
		return createPopup(path, [:])
	}

	protected Component createPopup(String path, Object id) {
		return createPopup(path, [id: id])
	}

	protected Component createPopup(String path, Map args) {
		return createPopup(path, args, 0, 0)
	}

	protected Component createPopup(String path, Object id, int width, int height) {
		return createPopup(path, [id: id], width, height)
	}

	protected Component createPopup(String path, Map args, int width, int height) {
		if (args == null)
			args = [:]
		if (width)
			args.width = "${width}px"
		if (height)
			args.height = "${height}px"
		args.path = path
		return createPopup(args)
	}

	protected Component createPopup(Map args) {
		return Executions.createComponents(Zul.POPUP, main, args)
	}

	protected void openPage(String path) {
		openPage(path, null)
	}

	protected void openPage(String path, Object id) {
		def url = path + (id ? ":${id}" : '')
		Executions.getCurrent().sendRedirect(url, '_blank')
	}

	protected void openUrl(String url) {
		Executions.getCurrent().sendRedirect(url)
	}

	protected void openUrl(String url, String name) {
		Executions.getCurrent().sendRedirect(url, name)
	}

	protected void exit(String error) {
		window.setVisible(false)
		showErrorMessage(error)
		close()
	}

	protected void cancel() {
		close()
	}

	protected void close() {
		self.detach()
		postEvent('onClose')
	}

	protected void postEvent(String name, Object data = null) {
		Events.postEvent(name, window, data)
	}

	protected MessageSourceResolvable createMessage(String code, List arguments = null) {
		return arguments ? new DefaultMessageSourceResolvable([code] as String[], arguments as Object[]) : new DefaultMessageSourceResolvable(code)
	}

	protected void showInfoMessage(String text, String title = ' ') {
		Messagebox.show(text, title, null, null, Messagebox.INFORMATION, null, null, [width: '600'])
	}

	protected void showErrorMessage(String text, int width = 600) {
		Messagebox.show(text, l('error'), null, null, Messagebox.ERROR, null, null, [width: "$width".toString()])
	}

	protected void showErrorMessage(MessageSourceResolvable msg) {
		showErrorMessage(l(msg))
	}

	protected void showErrorMessage(List<MessageSourceResolvable> msgs) {
		showErrorMessage(msgs.collect { l(it) }.join('\n'))
	}

	protected void showNotification(String text, int delay = 1000) {
		Clients.showNotification(text, 'info', null, null, delay)
	}

	protected void confirm(String text, Closure c) {
		confirm(text, null, c)
	}

	protected void confirm(String text, String title, Closure c) {
		Messagebox.show(text, title, Messagebox.YES | Messagebox.NO, Messagebox.EXCLAMATION) { evt ->
			if (evt.name == Messagebox.ON_YES) c()
		}
	}

	protected void confirmClose(String text = null) {
		Clients.confirmClose(text)
	}
}
