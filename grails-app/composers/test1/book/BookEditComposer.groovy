package test1.book

import system.EditComposer
import test1.AuthorService
import test1.Book
import test1.BookService

class BookEditComposer extends EditComposer<Book, BookService> {

	BookService bookService
	AuthorService authorService

	def boxTitle
	def boxAuthor

	def afterCompose = {
		windowTitle = "${l('book.label')}"
	}

	@Override
	void toUI() {
		boxTitle.value = instance.title
		boxAuthor.domainValue = instance.author
	}

	@Override
	void fromUI() {
		instance.title = boxTitle.value
		instance.author = boxAuthor.domainValue
	}
}
