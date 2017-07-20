package denghui.me.replugindemo;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.qihoo360.replugin.RePlugin;

public class MainActivity extends AppCompatActivity {

    private Button btn1;
    private Button btn2;

//    private String path = android.os.Environment.getExternalStorageDirectory() + "/klogger/plugin1.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.load_1_btn);
        btn2 = (Button) findViewById(R.id.load_2_btn);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("plugin1", "com.zero.mybody.activities.MainActivity"));
                RePlugin.startActivity(v.getContext(), intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setComponent(new ComponentName("plugin2", "com.mymoney.anychat.AnyChatLaunchActivity"));
//                RePlugin.startActivity(v.getContext(), intent);
                startActivity(new Intent(getApplicationContext(), BindActivity.class));
            }
        });
    }
}
