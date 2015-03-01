package org.addhen.birudo.core.task;

/**
 * Thread abstraction created to change the execution context from any thread to any other thread.
 * implementation of this interface will change context and update the UI.
 *
 * @author Henry Addo
 */
public interface PostExecutionThread {

    /**
     * Causes the {@link Runnable} to be added to the message queue of the Main UI Thread of the
     * application.
     *
     * @param runnable {@link Runnable} to be executed.
     */
    void post(Runnable runnable);
}
