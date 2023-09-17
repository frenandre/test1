package system

import org.apache.commons.lang.exception.ExceptionUtils
import org.codehaus.groovy.runtime.InvokerHelper
import org.springframework.context.MessageSource
import org.springframework.context.MessageSourceResolvable
import org.springframework.context.i18n.LocaleContextHolder
import org.zkoss.util.media.Media
import org.zkoss.zk.grails.composer.GrailsComposer
import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.Page
import org.zkoss.zk.ui.metainfo.ComponentInfo
import org.zkoss.zul.Filedownload

import grails.util.Environment
import grails.util.GrailsClassUtils
import groovy.util.logging.Slf4j

@Slf4j
abstract class BaseComposer extends GrailsComposer {

	MessageSource messageSource
	SessionService sessionService

	@Override
	ComponentInfo doBeforeCompose(Page page, Component parent, ComponentInfo compInfo) {
		compInfo = super.doBeforeCompose(page, parent, compInfo)
		handleBeforeComposeClosure()
		return compInfo
	}

	void onCreate() {
		handleAfterCreateClosure()
	}

	protected String l(code, args = null) {
		try {
			return messageSource.getMessage("$code", args as Object[], locale)
		} catch (Exception e) {
			log.warn(e.message)
			return "$code"
		}
	}

	protected String l(MessageSourceResolvable msg) {
		return messageSource.getMessage(msg, locale)
	}

	protected File createTempFile(String prefix, String suffix) {
		return session.nativeSession.createTempFile(prefix, suffix)
	}

	protected void download(Media media) {
		Filedownload.save(media)
	}

	protected void download(File file, String contentType) {
		Filedownload.save(file, contentType)
	}

	protected void download(byte[] content, String contentType, String filename) {
		Filedownload.save(content, contentType, filename)
	}

	protected Locale getLocale() {
		return LocaleContextHolder.getLocale()
	}

	protected String formatNumber(value, String pattern = null) {
		if (pattern)
			return CommonFns.formatNumber(value, pattern)
		else
			return CommonFns.formatNumber(value)
	}

	protected String formatDate(value, String pattern = null) {
		if (pattern)
			return CommonFns.formatDate(value, pattern)
		else
			return CommonFns.formatDate(value)
	}

	protected void logDebug(Throwable e) {
		if (Environment.current != Environment.PRODUCTION)
			log.error(e.message)
	}

	protected void logError(Throwable e) {
		if (Environment.current != Environment.PRODUCTION)
			log.error(ExceptionUtils.getFullStackTrace(e))
		else
			log.error(e.message)
	}

	private void handleBeforeComposeClosure() {
		Object c = GrailsClassUtils.getPropertyOrStaticPropertyOrFieldValue(this, 'beforeCompose')
		if (c instanceof Closure)
			InvokerHelper.invokeClosure(c, null)
	}

	private void handleAfterCreateClosure() {
		Object c = GrailsClassUtils.getPropertyOrStaticPropertyOrFieldValue(this, 'afterCreate')
		if (c instanceof Closure)
			InvokerHelper.invokeClosure(c, null)
	}
}
