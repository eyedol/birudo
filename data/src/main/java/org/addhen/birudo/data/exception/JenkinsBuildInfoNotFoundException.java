package org.addhen.birudo.data.exception;


public class JenkinsBuildInfoNotFoundException extends Exception {
    public JenkinsBuildInfoNotFoundException() {
        super();
    }

    public JenkinsBuildInfoNotFoundException(final String message) {
        super(message);
    }

    public JenkinsBuildInfoNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public JenkinsBuildInfoNotFoundException(final Throwable cause) {
        super(cause);
    }
}
