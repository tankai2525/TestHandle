package test.kai.tan.com.testhandle;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * 理解ThreadLocal特性
 */
public class TestThreadLocalActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private String hot1, hot2, hot3;
    private ThreadLocal<String> mThreadLocal = new ThreadLocal<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_thread_local);
//        testThreadLocal();

        new TestThread().start();
    }

    private void testThreadLocal() {
        mThreadLocal.set("饭老师");
        new Hot1Thread().start();
        new Hot2Thread().start();
        hot3 = mThreadLocal.get();
        try {
            Thread.sleep(2000);
            Log.i(TAG, "hot1:" + hot1);
            Log.i(TAG, "hot2:" + hot2);
            Log.i(TAG, "hot3:" + hot3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class Hot1Thread extends Thread {
        @Override
        public void run() {
            super.run();
            mThreadLocal.set("苍老师");
            hot1 = mThreadLocal.get();
        }
    }

    private class Hot2Thread extends Thread {
        @Override
        public void run() {
            super.run();
            mThreadLocal.set("武老师");
            hot2 = mThreadLocal.get();
        }
    }

    private class TestThread extends Thread {

        @Override
        public void run() {
            super.run();
            Looper.prepare();
            Handler handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    Log.d("tag","--------testThread");
                }
            };
            handler.sendEmptyMessage(1);
            Looper.loop();


        }

    }
}
