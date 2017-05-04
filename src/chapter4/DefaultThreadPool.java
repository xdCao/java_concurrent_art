package chapter4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by xdcao on 2017/5/4.
 */
public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {

    private static final int MAX_WORKER_NUMBERS=10;

    private static final int DEFAULT_WORKER_NUMBERS=5;

    private static final int MIN_WORKER_NUMBERS=1;

    private final LinkedList<Job> jobs=new LinkedList<Job>();

    private final List<Worker> workers= Collections.synchronizedList(new ArrayList<Worker>());

    private int workerNum=DEFAULT_WORKER_NUMBERS;

    private AtomicLong threadNum=new AtomicLong();


    public DefaultThreadPool() {
        initializeWorkers(DEFAULT_WORKER_NUMBERS);
    }

    public DefaultThreadPool(int workerNum){
        this.workerNum=workerNum>MAX_WORKER_NUMBERS?MAX_WORKER_NUMBERS:workerNum<MIN_WORKER_NUMBERS?MIN_WORKER_NUMBERS:workerNum;
        initializeWorkers(workerNum);
    }

    private void initializeWorkers(int defaultWorkerNumbers) {
        for (int i=0;i<defaultWorkerNumbers;i++){
            Worker worker=new Worker();
            workers.add(worker);
            Thread thread=new Thread(worker,"ThreadPool-worker-"+threadNum.incrementAndGet());
            thread.start();
        }
    }

    @Override
    public void execute(Job job) {
        if(job!=null){
            synchronized (jobs){
                jobs.addLast(job);
                jobs.notify();
            }
        }
    }

    @Override
    public void shutDown() {
        for (Worker worker:workers){
            worker.shutDown();
        }
    }

    @Override
    public void addWorkers(int num) {
        synchronized (jobs){
            if(num+this.workerNum>MAX_WORKER_NUMBERS){
                num=MAX_WORKER_NUMBERS-this.workerNum;
            }
            initializeWorkers(num);
            this.workerNum+=num;
        }
    }

    @Override
    public void removeWorker(int num) {
        synchronized (jobs){
            if(num>=this.workerNum){
                throw new IllegalArgumentException("beyond workNum");
            }
            int count=0;
            while (count<num){
                Worker worker=workers.get(count);
                if(workers.remove(worker)){
                    worker.shutDown();
                    count++;
                }
            }
            this.workerNum-=count;
        }
    }

    @Override
    public int getJobSize() {
        return jobs.size();
    }


    class Worker implements Runnable{

        private volatile boolean isRunning=false;

        @Override
        public void run() {
            while (isRunning){
                Job job=null;
                synchronized (jobs){
                    while (jobs.isEmpty()){
                        try {
                            jobs.wait();
                        }catch (InterruptedException e){
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    job=jobs.removeFirst();
                }
                if (job!=null){
                    try {
                        job.run();
                    }catch (Exception e){

                    }
                }
            }
        }

        public void shutDown() {
            isRunning=false;
        }
    }

}
