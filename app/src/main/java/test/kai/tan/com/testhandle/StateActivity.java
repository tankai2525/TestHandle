package test.kai.tan.com.testhandle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RadioButton;

import java.io.File;

import static android.os.Environment.DIRECTORY_MUSIC;

public class StateActivity extends AppCompatActivity {

    private RadioButton radioButton;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);

        mContext = this;
        radioButton = findViewById(R.id.radioButton);
        StateListDrawable drawable = new StateListDrawable();

//        mContext.getFilesDir();

        Log.d("tag", Environment.getExternalStorageDirectory().getPath());// /storage/emulated/0
        Log.d("tag", Environment.getExternalStorageDirectory().getAbsolutePath());// /storage/emulated/0
        Log.d("tag", Environment.getExternalStoragePublicDirectory(DIRECTORY_MUSIC).getAbsolutePath());// /storage/emulated/0/Music

        //Tencent/QQfile_recv/1.png
        //Tencent/QQfile_recv/2.png

        String path1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Tencent/QQfile_recv/1.png";
        String path2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Tencent/QQfile_recv/2.png";

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file1 = new File(path1);
            File file2 = new File(path2);
            if (file1.exists()) {
                Log.d("tag", file1.getAbsolutePath());
                Bitmap bitmap1 = BitmapFactory.decodeFile(path1);
                Drawable drawable1 = new BitmapDrawable(getResources(), bitmap1);
                drawable.addState(new int[]{-android.R.attr.state_checked}, drawable1);
            }
            if (file2.exists()) {
                Bitmap bitmap2 = BitmapFactory.decodeFile(path2);
                Drawable drawable2 = new BitmapDrawable(getResources(), bitmap2);
                drawable.addState(new int[]{android.R.attr.state_checked}, drawable2);
            }
        }

//        Drawable drawableDefault = getResources().getDrawable(R.mipmap.tab_bookshelf_default);
//        Drawable drawableCheckable = getResources().getDrawable(R.mipmap.tab_bookshelf_press);
//        drawable.addState(new int[]{android.R.attr.state_checked}, drawableCheckable);
//        drawable.addState(new int[]{}, drawableDefault);

        drawable.setBounds(0, 0, DensityUtil.dip2px(this, 22), DensityUtil.dip2px(this, 23));
        radioButton.setCompoundDrawables(null, drawable, null, null);

    }
}
