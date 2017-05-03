package chapter4;

import java.util.concurrent.TimeUnit;

/**
 * Created by xdcao on 2017/5/3.
 */
public class SleepUtils {

    public static final void second(long seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        }catch (InterruptedException e){

        }
    }

}
