package system

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes = 'authority')

class Role implements Serializable {

	Short id
	String authority

	static mapping = {
		cache true
		table 'G_ROLE'
		id generator: 'sequence', params: [sequence: 'G_ROLE_SEQ']
	}

	static constraints = {
		authority blank: false, unique: true
	}

	Set<User> getUsers() {
		UserRole.findAllByRole(this).collect { it.user } as Set
	}

	String toString() {
		"$authority"
	}
}
