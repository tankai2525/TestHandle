package test.kai.tan.com.testhandle;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;


public class GoodHandleActivity extends AppCompatActivity {

    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_handle);
        init();
    }

    private void init() {
        mActivity = this;

    }

    private static class GoodRunnable implements Runnable {

        @Override
        public void run() {

        }
    }

    private static class GoodHandler extends Handler {
        private WeakReference<Activity> activityWeakReference;

        public GoodHandler(Activity activity) {
            activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            activityWeakReference.get();
        }
    }

}
