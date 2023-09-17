package system

import org.grails.core.artefact.DomainClassArtefactHandler
import org.grails.datastore.mapping.services.Service
import org.springframework.context.MessageSource

import grails.core.GrailsApplication
import grails.util.GrailsClassUtils
import grails.util.Holders
import groovy.transform.CompileStatic

@CompileStatic
class GrailsUtils {

	private static MessageSource messageSource
	private static GrailsApplication grailsApplication

	static Class getDomainClass(String name) {
		return getGrailsApplication().getArtefact(DomainClassArtefactHandler.TYPE, name).clazz
	}

	static Class getPropertyType(String name, String property) {
		return GrailsClassUtils.getPropertyType(getDomainClass(name), property)
	}

	static Service getDataService(String name) {
		return (Service) getGrailsApplication().mainContext.getBean(Class.forName(name))
	}

	static MessageSource getMessageSource() {
		if (messageSource == null)
			messageSource = (MessageSource) getGrailsApplication().mainContext.getBean('messageSource')
		return messageSource
	}

	static GrailsApplication getGrailsApplication() {
		if (grailsApplication == null)
			grailsApplication = Holders.grailsApplication
		return grailsApplication
	}
}
