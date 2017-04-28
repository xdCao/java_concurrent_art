package chapter1;

import jdk.nashorn.internal.runtime.ECMAErrors;

/**
 * Created by xdcao on 2017/4/28.
 */
public class DeadLockDemo {

    private static String A="A";
    private static String B="B";

    public static void main(String[] args){
        new DeadLockDemo().deadLocak();
    }

    private void deadLocak(){

        Thread th1=new Thread(() -> {
            synchronized (A){
                try {
                    Thread.currentThread().sleep(2000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                synchronized (B){
                    System.out.println(1);
                }
            }
        });

        Thread th2=new Thread(() -> {
            synchronized (B){
                synchronized (A){
                    System.out.println(2);
                }
            }
        });

        th1.start();
        th2.start();

    }

}
