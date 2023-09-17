package system

import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder

import grails.util.Holders
import groovy.util.logging.Slf4j

@Slf4j
public class CommonFns {

	static String getLabel(String key) {
		return getMessage(key, null)
	}

	static String getLabel(String key, String arg1) {
		String msg1 = getMessage(arg1, null)
		return getMessage(key, [msg1] as Object[])
	}

	static String getLabel(String key, String arg1, String arg2) {
		String msg1 = getMessage(arg1, null)
		String msg2 = getMessage(arg2, null)
		return getMessage(key, [msg1, msg2] as Object[])
	}

	static String getMessage(String key, Object[] args) {
		try {
			return getMessageSource().getMessage(key, args, LocaleContextHolder.getLocale())
		} catch (Exception e) {
			if (!key.endsWith('.'))
				log.warn(e.message)
			return null
		}
	}

	static final String formatNumber(Object value) {
		return formatNumber(value, '#,##0.00')
	}

	static final String formatNumber(Object value, String format) {
		if (value == null)
			return ''
		else
			return org.zkoss.xel.fn.CommonFns.formatNumber(value, format, null)
	}

	static final String formatDate(Date date) {
		if (date == null)
			return ''
		else
			return org.zkoss.xel.fn.CommonFns.formatDate(date, 'dd.MM.yyyy', null, null, null, null)
	}

	static final String formatDate(Date date, String pattern) {
		if (date == null)
			return ''
		else
			return org.zkoss.xel.fn.CommonFns.formatDate(date, pattern, null, null, null, null)
	}

	private static MessageSource getMessageSource() {
		if (messageSource == null)
			messageSource = (MessageSource) Holders.grailsApplication.mainContext.getBean('messageSource')
		return messageSource
	}

	private static MessageSource messageSource
}
