package system

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes = 'id')

class Company implements Serializable {

	Short id
	String description
	String code
	String erp
	String logo

	static mapping = {
		cache true
		table 'G_COMPANY'
		id generator: 'sequence', params: [sequence: 'G_COMPANY_SEQ']
	}

	static constraints = {
		description blank: false
		code blank: false, maxSize: 2, unique: true
		erp nullable: true, maxSize: 2
		logo nullable: true, maxSize: 50
	}

	String toString() {
		"$description"
	}
}
