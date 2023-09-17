package system

import java.lang.reflect.ParameterizedType

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.Page
import org.zkoss.zk.ui.metainfo.ComponentInfo

import grails.web.databinding.DataBindingUtils
import groovy.transform.CompileStatic

@CompileStatic
abstract class DomainComposer<T, S> extends AppComposer {

	protected S dataService

	@Override
	ComponentInfo doBeforeCompose(Page page, Component parent, ComponentInfo info) {
		dataService = (S) GrailsUtils.getDataService(dataServiceName)
		return super.doBeforeCompose(page, parent, info)
	}

	protected Class getDomainClass() {
		return GrailsUtils.getDomainClass(domainName)
	}

	protected String getDomainName() {
		return getActualTypeArgument(0)
	}

	protected String getDataServiceName() {
		return getActualTypeArgument(1)
	}

	protected Object bindData(Object instance, Object source) {
		return DataBindingUtils.bindObjectToInstance(instance, source)
	}

	private String getActualTypeArgument(int i) {
		return (((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[i]).getTypeName()
	}
}
