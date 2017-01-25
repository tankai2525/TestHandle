package test.kai.tan.com.testhandle;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * handler内存泄漏
 */
public class OutMemoryActivity extends AppCompatActivity {

    private Handler mHandler;
    private static final String TAG = "OutMemoryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_memory);

        init();
    }

    private void init() {
        //1匿名类new handler(){}持有外部类一个引用
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.d(TAG,"msg:" + msg.what);
            }
        };

        Message msg = Message.obtain();
        msg.what = 100;
        mHandler.sendMessage(msg);

        //2匿名类new Runnable(){}持有外部类一个引用
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"11111");
            }
        },20*1000);
        //3 new Runnable(){}会封装成Message的callback,message会持有Runnable引用。
        //4 在入队的方法中messagequeue的target会指向handler messagequeue持有handler引用
        //所以Message和messagequeue间接持有activity引用

        //如果：进入该activity20秒内，旋转屏幕导致activity重绘。runnable还未执行，message仍持有runnable的引用，
        // 而runnable持有activity引用，故activity不能被回收。

    }
}