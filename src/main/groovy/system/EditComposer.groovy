package system

import org.grails.datastore.mapping.validation.ValidationErrors
import org.grails.datastore.mapping.validation.ValidationException
import org.springframework.dao.OptimisticLockingFailureException
import org.zkoss.zk.ui.select.annotation.Wire


abstract class EditComposer<T, S> extends ShowComposer<T, S> {

	@Wire def btnSaveAndClose
	@Wire def btnSave
	@Wire def btnDelete
	@Wire def btnCancel

	protected int mode

	protected final MODE_ADD = 1
	protected final MODE_UPDATE = 2

	void fromUI() {
	}

	@Override
	void load(value) {
		mode = (!value || value == '0') ? MODE_ADD : MODE_UPDATE
		if (modeAdd) {
			instance = newInstance()
		} else {
			instance = getInstance(value)
		}
	}

	@Override
	void view() {
		confirmClose(l('warning.close'))
		btnDelete?.visible = modeUpdate
		toUI()
	}

	boolean update() {
		try {
			fromUI()
			if (!validate())
				throw new ValidationException('validation errors', errors)
			saveInstance()
			showNotification(l('info.domain.updated'))
			return true
		} catch (OptimisticLockingFailureException e) {
			showErrorMessage(l('error.optimistic.lock'))
		} catch (ValidationException e) {
			logDebug(e)
			showErrorMessage(errors.allErrors)
		} catch (Exception e) {
			logError(e)
			showErrorMessage("$e.message")
		}
		return false
	}

	void delete(String title = null) {
		confirm(l('default.button.delete.confirm.message'), title ?: l('default.button.delete.label')) {
			try {
				deleteInstance()
				showNotification(l('info.domain.deleted'))
				close()
			} catch (Exception e) {
				logError(e)
				showErrorMessage("$e.message")
			}
		}
	}

	void deleteInstance() {
		dataService.delete(instance.id)
	}

	void saveInstance() {
		instance = dataService.save(instance)
	}

	boolean validate() {
		return instance.validate()
	}

	void close() {
		confirmClose()
		super.close()
	}

	T newInstance() {
		T domain = domainClass.newInstance()
		return domain
	}

	boolean isModeAdd() {
		return mode == MODE_ADD
	}

	boolean isModeUpdate() {
		return mode == MODE_UPDATE
	}

	void onClick_btnSaveAndClose() {
		if (update()) {
			close()
		}
	}

	void onClick_btnSave() {
		if (update()) {
			view()
		}
	}

	void onClick_btnDelete() {
		delete()
	}

	ValidationErrors getErrors() {
		return instance.errors
	}
}
