package chapter4;

import com.sun.xml.internal.bind.v2.model.annotation.RuntimeAnnotationReader;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by xdcao on 2017/5/4.
 */
public class WaitNotify {

    static boolean flag=false;
    static Object lock=new Object();

    static class Wait implements Runnable{
        @Override
        public void run() {
            synchronized (lock){
                while (flag){
                    try {
                        System.out.println(Thread.currentThread()+" flag is true, wait @ "+new SimpleDateFormat("HH:mm:ss").format(new Date()));
                        lock.wait();
                    }catch (InterruptedException e){

                    }
                }
                System.out.println(Thread.currentThread()+" flag is false, running @ "+new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        }
    }

    static class Notify implements Runnable{

        @Override
        public void run() {
            synchronized (lock){
                System.out.println(Thread.currentThread()+" hold lock, notify @ "+
                new SimpleDateFormat("HH:mm:ss").format(new Date()));
                lock.notifyAll();
                flag=false;
                SleepUtils.second(5);
            }

            synchronized (lock){
                System.out.println(Thread.currentThread()+" hold lock again, sleep @ "+
                new SimpleDateFormat("HH:mm:ss").format(new Date()));
                SleepUtils.second(5);
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread waitThread=new Thread(new Wait(),"WaitThread");
        waitThread.start();
        TimeUnit.SECONDS.sleep(1);
        Thread notifyThread=new Thread(new Notify(),"NotifyThread");
        notifyThread.start();
    }

}
