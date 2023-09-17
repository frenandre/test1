package system.session

import javax.servlet.http.HttpSessionEvent
import javax.servlet.http.HttpSessionListener

import groovy.util.logging.Slf4j

@Slf4j
class SessionListener implements HttpSessionListener {

	@Override
	void sessionCreated(HttpSessionEvent event) { }

	@Override
	void sessionDestroyed(HttpSessionEvent event) {
		def tmpdir = SessionDynamicMethods.getTempDir(event.session, false)
		if (tmpdir.isDirectory()) {
			if (!tmpdir.deleteDir()) {
				log.error "Unable to delete directory $tmpdir.absolutePath"
			}
		}
	}
}
