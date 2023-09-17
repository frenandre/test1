package system

import org.zkoss.zk.ui.Component

abstract class ShowComposer<T, S> extends DomainComposer<T, S> {

	protected T instance

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		load(parameterId ?: parameterInstance ?: null)
		super.doAfterCompose(comp)
	}

	@Override
	void onCreate() {
		if (validInstance) {
			super.onCreate()
			view()
		} else {
			exit(l('error.instance.not.found'))
		}
	}

	void toUI() {
	}

	void load(value) {
		instance = getInstance(value)
	}

	void view() {
		toUI()
	}

	void refresh() {
		load(instance.id)
		view()
	}

	T getInstance(Number value) {
		return dataService.get(value)
	}

	T getInstance(String value) {
		return dataService.get(value)
	}

	T getInstance(T value) {
		return value
	}

	boolean isValidInstance() {
		return instance != null
	}

	void close() {
		instance = null
		super.close()
	}

	void onClick_btnCancel() {
		cancel()
	}
}
