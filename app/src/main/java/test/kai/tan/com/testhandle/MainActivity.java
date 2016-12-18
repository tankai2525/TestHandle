package test.kai.tan.com.testhandle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        open(TestThreadLocalActivity.class);
    }

    private void open(Class c){
        Intent intent= new Intent(this, c);
        startActivity(intent);
    }
}
