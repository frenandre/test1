package test1.book

import system.ListComposer
import test1.Book
import test1.BookService

class BookListComposer extends ListComposer<Book, BookService> {

	def afterCompose = { window ->

		pageTitle = l('book.label')
		windowTitle = l('book.list')

		filters.exclusive = ['id']
		filters.like = ['title']
		filters.eq = ['name']
	}

	void onSelect() {
		popup(lst.selectedItem.value)
	}

	void onAdd() {
		popup(0)
	}

	private popup(id = null) {
		def popup = createPopup('book', id)
		popup.onClose { redraw() }
		popup.doModal()
	}
}
