package chapter4;

/**
 * Created by xdcao on 2017/5/4.
 */
public interface ThreadPool<Job extends Runnable> {

    void execute(Job job);

    void shutDown();

    void addWorkers(int num);

    void removeWorker(int num);

    int getJobSize();

}
