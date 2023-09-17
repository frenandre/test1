package test1

import grails.gorm.multitenancy.CurrentTenant
import grails.gorm.services.Service

@Service(Book)
@CurrentTenant
abstract class BookService {

	abstract Book get(Serializable id)

	abstract List<Book> list(Map args)

	abstract Long count()

	abstract void delete(Serializable id)

	abstract Book save(Book book)

	List<Book> list(Map args, Closure criteria) {
		return Book.createCriteria().list(args, criteria)
	}
}