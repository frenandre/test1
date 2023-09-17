package test1.author

import system.AppComposer
import test1.Book

class AuthorBookComposer extends AppComposer {

	def boxAuthor
	def boxTitle

	Book instance

	def afterCompose = {
		instance = getParameterInstance()

		windowTitle = "${l('author.label')} $instance.author.id"

		boxAuthor.value = instance.author.name
		boxTitle.value = instance.title
	}

	def onClick_btnOK() {
		instance.title = boxTitle.value
		postEvent('onOK', instance)
		close()
	}

	def onClick_btnRemove() {
		postEvent('onRemove')
		close()
	}

	void onClick_btnCancel() {
		postEvent('onCancel')
		cancel()
	}
}
