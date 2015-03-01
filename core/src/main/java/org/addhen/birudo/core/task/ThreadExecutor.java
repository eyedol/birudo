package org.addhen.birudo.core.task;

/**
 * Use this class to execute an {@link org.addhen.birudo.core.usecase.IInteractor}.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface ThreadExecutor {

    /**
     * Executes a {@link Runnable}.
     *
     * @param runnable The class that implements {@link Runnable} interface.
     */
    void execute(final Runnable runnable);
}
