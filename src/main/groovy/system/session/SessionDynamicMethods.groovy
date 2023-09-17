package system.session

import javax.servlet.http.HttpSession

import groovy.util.logging.Slf4j

@Slf4j
class SessionDynamicMethods {

	private SessionDynamicMethods() {}

	static File getTempDir(HttpSession session, boolean create = true) {
		def tmpDir = new File(System.properties."java.io.tmpdir", "SessionTmp_$session.id")
		if (create && !tmpDir.isDirectory()) {
			log.debug "Creating temp directory $tmpDir"
			tmpDir.mkdirs()
		}
		return tmpDir
	}

	static File createTempFile(HttpSession session, String prefix, String suffix) {
		def tmpDir = getTempDir(session)
		File.createTempFile(prefix, suffix, tmpDir)
	}
}