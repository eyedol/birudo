package org.addhen.birudo.core.exception;

/**
 * Wrapper for managing exceptions
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface ErrorWrap {

    Exception getException();

    String getErrorMessage();
}
