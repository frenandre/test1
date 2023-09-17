package system

import org.apache.commons.lang.WordUtils
import org.zkoss.zk.ui.WrongValueException
import org.zkoss.zk.ui.event.CreateEvent
import org.zkoss.zk.ui.event.InputEvent
import org.zkoss.zk.ui.event.OpenEvent
import org.zkoss.zk.ui.event.SelectEvent
import org.zkoss.zk.ui.ext.AfterCompose
import org.zkoss.zk.ui.ext.DynamicPropertied
import org.zkoss.zul.Combobox
import org.zkoss.zul.Comboitem

class DomainCombobox extends Combobox implements AfterCompose, DynamicPropertied {

	String domainName
	String domainProperty
	String domainSort
	boolean readonly
	int min
	Map filter = [:]
	String searchCriterion = 'ilike'
	String searchPrefix = '%'
	String searchSuffix = '%'
	String sibling

	Closure filterCriteria = { }
	Closure label = { domainProperty ? it[domainProperty] : it.toString() }

	DomainCombobox() {
		super()
		min = 0
		readonly = true
		buttonVisible = false
		autodrop = true
	}

	void setDomainName(String value) {
		domainName = value
		domainClass = GrailsUtils.getDomainClass(domainName)
	}

	void setDomainValue(Object value) {
		init()
		if (min) {
			if (!selectedItem || !value || selectedItem.id != value.id) {
				items.clear()
				if (!value) {
					setValue(value)
				} else {
					setSelectedItem(appendDomain(value))
				}
			}
		} else {
			for (Comboitem item : items) {
				if (item.value.id == value.id) {
					setSelectedItem(item)
					break
				}
			}
		}
	}

	Object getDomainValue() {
		return selectedItem?.value
	}

	void setReadonly(boolean value) {
		this.readonly = value
	}

	void onCreate(CreateEvent event) {
		if (!min) {
			super.setReadonly(readonly)
		}
	}

	void onOpen(OpenEvent event) {
		init()
	}

	void onChanging(InputEvent event) {
		if (!event.isChangingBySelectBack() && min) {
			items.clear()
			if (min <= event.value.length()) {
				loadItems(event.value)
			}
		}
	}

	void onChange(InputEvent event) {
		if (readonly && !selectedItem) {
			value = null
		}
	}

	void onSelect(SelectEvent event) {
		if (sibling) {
			((DomainCombobox) getFellow(sibling)).domainValue = domainValue
		}
	}

	@Override
	void afterCompose() {
		if (!min) {
			autodrop = false
			buttonVisible = true
		}
	}

	@Override
	boolean hasDynamicProperty(String name) {
		return name.startsWith('filter')
	}

	@Override
	Object getDynamicProperty(String name) {
		if (name.startsWith('filter')) {
			return filter.get(WordUtils.uncapitalize(name.substring(6)))
		} else {
			return null
		}
	}

	@Override
	void setDynamicProperty(String name, Object value) throws WrongValueException {
		if (name.startsWith('filter')) {
			filter.put(WordUtils.uncapitalize(name.substring(6)), value)
		}
	}

	void init() {
		if (!loaded && !min) {
			loadItems(null)
		}
	}

	private Comboitem appendDomain(value) {
		Comboitem item = appendItem(label(value))
		item.value = value
		item.onMouseOver { evt ->
			evt.target.tooltiptext = evt.target.value.toString()
		}
		return item
	}

	private void loadItems(String value) {
		def criteria = {
			readOnly(true)
			filter.each { k, v ->
				eq(k, v)
			}
			if (domainProperty && value) {
				"$searchCriterion"(domainProperty, searchPrefix + value + searchSuffix)
			}
			if (domainSort) {
				order(domainSort, 'asc')
			} else if (domainProperty) {
				order(domainProperty, 'asc')
			}
		} << filterCriteria
		domainClass.createCriteria().list(criteria).each { appendDomain(it) }
		loaded = true
	}

	private boolean loaded
	private Class domainClass
}
