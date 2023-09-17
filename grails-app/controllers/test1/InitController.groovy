package test1

import system.Company

class InitController {

	def author() {
		Author.withTransaction {
			(100..299).each {
				new Author(name: "Author $it", year: 1900 + it).save()
			}
		}
	}
}
