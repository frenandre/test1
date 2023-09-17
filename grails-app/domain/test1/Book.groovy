package test1

import grails.gorm.MultiTenant

class Book implements MultiTenant<Book> {

	Short companyId
	String title

	static belongsTo = [author:Author]

	static constraints = {
		title blank: false
	}

	static mapping = {
		tenantId name: 'companyId'
	}

	String toString() {
		"$title"
	}
}
