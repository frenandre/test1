package system

class OptimisticLockException extends RuntimeException {

	OptimisticLockException() {
	}

	OptimisticLockException(String s) {
		super(s)
	}
}
