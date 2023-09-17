package test1

import grails.gorm.multitenancy.CurrentTenant
import grails.gorm.services.Service

@Service(Author)
@CurrentTenant
abstract class AuthorService {

	abstract Author get(Serializable id)

	abstract List<Author> list(Map args)

	abstract Long count()

	abstract void delete(Serializable id)

	abstract Author save(Author author)

	List<Author> list(Map args, Closure criteria) {
		return Author.createCriteria().list(args, criteria)
	}
}