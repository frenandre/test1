package test1

import grails.gorm.MultiTenant

class Author implements MultiTenant<Author> {

	Short companyId
	String name
	Integer year

	static hasMany = [books: Book]

	static constraints = {
	}

	static mapping = {
		columns {
			tenantId name: 'companyId'
			books cascade: 'all-delete-orphan'
		}
	}

	String toString() {
		"$name"
	}
}
