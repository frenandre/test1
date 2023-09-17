package test1.author

import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.ListModelList

import system.EditComposer
import test1.Author
import test1.AuthorService
import test1.Book

class AuthorEditComposer extends EditComposer<Author, AuthorService> {

	def boxName
	def boxYear
	def lstBooks

	def afterCompose = {
		windowTitle = "${l('author.label')}"
	}

	@Override
	void toUI() {
		boxName.value = instance.name ?: null
		boxYear.value = instance.year ?: null
		toBooksUI()
	}

	@Override
	void fromUI() {
		instance.name = boxName.value
		instance.year = boxYear.value
	}

	void toBooksUI() {
		lstBooks.model = instance.books ? new ListModelList(instance.books) : null
	}

	void onEdit() {
		popup(lstBooks.selectedItem.value)
	}

	void onAdd() {
		popup(new Book(author: instance))
	}

	private popup(Book book) {
		def popup = createPopup('author/book', [instance: new Book(book.properties)])
		popup.onOK { Event evt ->
			book.properties = evt.data.properties
			if (!book.id) {
				instance.addToBooks(book)
			}
			toBooksUI()
		}
		popup.onRemove {
			instance.removeFromBooks(book)
			toBooksUI()
		}
		popup.doModal()
	}
}
