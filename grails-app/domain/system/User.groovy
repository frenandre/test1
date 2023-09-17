package system

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes = 'username')

class User implements Serializable {

	Short id
	Company company
	String username
	String password = 'secret'
	String description
	String firstName
	String lastName
	String email
	String sapId
	Short cmdbUserId
	boolean ms365
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	boolean showMobile

	static constraints = {
		company nullable: false
		username blank: false, unique: true
		password blank: false, display: false
		email nullable: true
		sapId nullable: true, maxSize: 20
		firstName nullable: false, maxSize: 50
		lastName nullable: false, maxSize: 50
		cmdbUserId nullable: true
	}

	static mapping = {
		cache true
		table 'G_USER'
		id generator: 'sequence', params: [sequence: 'G_USER_SEQ']
		columns {
			password column: 'PASSWORD_'
			company cascade: 'evict', lazy: false
		}
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}

	String getEmailAddress() {
		String address = email ?: "${username}@zimbra.site"
		return "${description} <${address}>"
	}

	String toString() {
		"$username"
	}
}
