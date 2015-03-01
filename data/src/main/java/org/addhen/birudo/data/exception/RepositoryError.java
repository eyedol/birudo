package org.addhen.birudo.data.exception;


import org.addhen.birudo.core.exception.ErrorWrap;

public class RepositoryError implements ErrorWrap {

    private final Exception mException;

    public RepositoryError(Exception exception) {
        mException = exception;
    }
    @Override
    public Exception getException() {
        return mException;
    }

    @Override
    public String getErrorMessage() {
        String message = "";

        if(mException !=null) {
            message = mException.getMessage();
        }
        return message;
    }
}
