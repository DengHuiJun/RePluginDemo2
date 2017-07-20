package denghui.me.replugindemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zero.mybody.ICallBack;
import com.zero.mybody.ICatchNews;
import com.zero.mybody.jsonResult.Category;

import java.util.List;

public class BindActivity extends AppCompatActivity {

    private TextView title;

    private ICatchNews catchNewsService;
    private ICallBack.Stub mCallBack = new ICallBack.Stub() {
        @Override
        public void setTitles(List<Category> data) throws RemoteException {
            if (data != null && !data.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (Category c : data) {
                    sb.append(c.getTitle() + "\n");
                }

                final String str = sb.toString();
                title.post(new Runnable() {
                    @Override
                    public void run() {
                        title.setText(str);
                    }
                });
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind);

        title = (TextView) findViewById(R.id.title);

        bindCatchNews();
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (catchNewsService != null)
                        catchNewsService.refreshData();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void bindCatchNews() {
        Intent intent = new Intent();
        intent.setPackage("com.zero.mybody");
        intent.setAction("denghui.me.replugindemo.plugin1.ICatchNews");
        bindService(intent, mConn, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            catchNewsService = ICatchNews.Stub.asInterface(service);

            try {
                catchNewsService.registerCallback(mCallBack);
                Toast.makeText(BindActivity.this, "绑定服务，注册回调", Toast.LENGTH_LONG).show();
            } catch (RemoteException e) {
                Toast.makeText(BindActivity.this, "RemoteException", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            catchNewsService = null;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConn);
    }
}
