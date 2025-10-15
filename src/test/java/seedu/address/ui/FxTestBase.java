package seedu.address.ui;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;

import javafx.application.Platform;

/** Minimal base for JavaFX tests. */
public abstract class FxTestBase {

    private static volatile boolean initialised = false;

    static {
        if (!initialised) {
            final CountDownLatch latch = new CountDownLatch(1);

            Platform.startup(() -> {
                latch.countDown();
            });

            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            initialised = true;
        }
    }

    protected void runOnFx(Runnable r) {
        if (Platform.isFxApplicationThread()) {
            r.run();
        } else {
            final CountDownLatch latch = new CountDownLatch(1);

            Platform.runLater(() -> {
                try {
                    r.run();
                } finally {
                    latch.countDown();
                }
            });

            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected <T> T callOnFx(Callable<T> c) {
        if (Platform.isFxApplicationThread()) {
            try {
                return c.call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        FutureTask<T> task = new FutureTask<>(() -> {
            return c.call();
        });

        runOnFx(task);

        try {
            return task.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
