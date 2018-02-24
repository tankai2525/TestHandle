package test.kai.tan.com.testhandle;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 理解ThreadLocal特性
 */
public class TestThreadLocalActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private String hot1, hot2, hot3;
    private ThreadLocal<String> mThreadLocal = new ThreadLocal<>();
    private Handler handler;
    private ProgressBar progressBar;
    private Button btn;
    private Handler mHandler;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_thread_local);
//        testThreadLocal();

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.d("tag", "--------testThread；" + Thread.currentThread().getName() + " - " + msg.getCallback() + " - " + SystemClock.uptimeMillis() + " - " + System.currentTimeMillis());
            }
        };

        progressBar = findViewById(R.id.progressBar);
        btn = findViewById(R.id.btn_down);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(0);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Looper.prepare();
                            URL url = new URL("http://blog.csdn.net/");
                            new DownloadFilesTask().execute(url);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

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

        @SuppressLint("HandlerLeak")
        @Override
        public void run() {
            super.run();
            Looper.prepare();
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    Log.d("tag", "--------testThread；" + Thread.currentThread().getName() + " - " + msg.getCallback() + " - " + SystemClock.uptimeMillis() + " - " + System.currentTimeMillis());
                }
            };
            handler.sendEmptyMessage(0);
            mHandler.sendEmptyMessage(0);
            Looper.loop();
        }

    }

    private class DownloadFilesTask extends AsyncTask<URL,Integer,Long>{

        public DownloadFilesTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Long doInBackground(URL... urls) {
            long totalSize = 0;
            int i = 0;
            try {
                while (i < 100) {
                    Thread.sleep(500);
                    i+=5;
                    publishProgress(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            totalSize = totalSize + i;
            return totalSize;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            Toast.makeText(TestThreadLocalActivity.this, "下载完成，结果是" + aLong, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
