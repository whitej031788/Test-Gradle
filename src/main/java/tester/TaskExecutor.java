package tester;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TaskExecutor {
    private final BlockingQueue<ITask> taskQueue = new LinkedBlockingQueue<>();
    private final Thread workerThread;
    private final PostgresqlDataSource dataSource;

    public TaskExecutor() {
        workerThread = new Thread(this::run);

        // create a fake use for the database
        dataSource = new PostgresqlDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/test");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres"); 
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
