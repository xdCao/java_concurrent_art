package chapter3;

import java.util.SplittableRandom;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by xdcao on 2017/5/2.
 */
public class ReentrantLockExample {

    int a=0;

    ReentrantLock lock=new ReentrantLock();

    public void writer(){
        lock.lock();
        try {
            a++;
        }finally {
            lock.unlock();
        }
    }


    public static void main(String[] args){
        ReentrantLockExample reentrantLockExample=new ReentrantLockExample();
        reentrantLockExample.writer();
    }


}
