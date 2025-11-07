package test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TaskExecutor {
    private final BlockingQueue<ITask> taskQueue = new LinkedBlockingQueue<>();
    private final Thread workerThread;

    public TaskExecutor() {
        workerThread = new Thread(this::run);
    }

    public void execute(final ITask task) {
        taskQueue.add(task);
    }

    public void start() {
        workerThread.start();
    }

    public void run() {
        while (true) {
            try {
                final ITask task = taskQueue.take();
                System.out.println("executing " + task);
                task.doWork();
            } catch (final InterruptedException e) {
                System.out.println("InterruptedException");
                //Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        final TaskExecutor taskExecutor = new TaskExecutor();
        taskExecutor.start();
        for (int i = 0; i < 50; i++) {
            taskExecutor.execute(new TestTask(i));
        }
        System.out.println("scheduled 50 tasks");
    }
}
