package system

class NotFoundException extends RuntimeException {

	NotFoundException() {
	}

	NotFoundException(String s) {
		super(s)
	}
}
