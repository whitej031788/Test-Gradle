package tester;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
// 1. ADD THIS IMPORT
import org.postgresql.ds.PGSimpleDataSource;

public class TaskExecutor {
    private final BlockingQueue<ITask> taskQueue = new LinkedBlockingQueue<>();
    private final Thread workerThread;

    // 2. CHANGE CLASS NAME HERE
    private final PGSimpleDataSource dataSource;

    public TaskExecutor() {
        workerThread = new Thread(this::run);

        // create a fake use for the database
        // 3. CHANGE CLASS NAME HERE
        dataSource = new PGSimpleDataSource();
        dataSource.setURL("jdbc:postgresql://localhost:5432/test"); // Note: setURL uses capital 'URL'
        dataSource.setUser("postgres"); // Note: setUsername is usually setUser for PGSimpleDataSource
        dataSource.setPassword("postgres");
    }
    // ... rest of your methods
}