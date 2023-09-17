package test1.author

import system.ListComposer
import test1.Author
import test1.AuthorService

class AuthorListComposer extends ListComposer<Author, AuthorService> {

	def afterCompose = { window ->

		pageTitle = l('author.label')
		windowTitle = l('author.list')

		filters.exclusive = ['id']
		filters.like = ['name']
		filters.eq = ['year']
		sort = 'name'
	}

	void onSelect() {
		popup(lst.selectedItem.value)
	}

	void onAdd() {
		popup(0)
	}

	private popup(id) {
		def popup = createPopup('author', id)
		popup.onClose { redraw() }
		popup.doModal()
	}
}
