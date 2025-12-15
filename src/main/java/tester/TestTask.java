package tester;

import java.security.SecureRandom;

public class TestTask implements ITask {
    private static final SecureRandom random = new SecureRandom();
    final int id;

    public TestTask(int id) {
        this.id = id;
    }

    @Override
    public void doWork() {
        if (random.nextInt(10) == 0) {
            System.out.println("exception in " + this);
            throw new RuntimeException("emulated error");
        }
    }

    @Override
    public String toString() {
        return "TestTask#" + id;
    }
}
