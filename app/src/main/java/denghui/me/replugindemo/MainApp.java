package denghui.me.replugindemo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.RePluginApplication;
import com.qihoo360.replugin.RePluginCallbacks;
import com.qihoo360.replugin.RePluginConfig;

/**
 * Created by allen on 2017/7/18.
 */

public class MainApp extends RePluginApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        RePlugin.enableDebugger(base, BuildConfig.DEBUG);
    }

    @Override
    protected RePluginConfig createConfig() {
        RePluginConfig c = new RePluginConfig();

        c.setUseHostClassIfNotFound(true);

        c.setVerifySign(false);
        return c;
    }

    @Override
    protected RePluginCallbacks createCallbacks() {
        return new HostCallbacks(this);
    }

    /**
     * 宿主针对RePlugin的自定义行为
     */
    private class HostCallbacks extends RePluginCallbacks {

        private static final String TAG = "HostCallbacks";

        private HostCallbacks(Context context) {
            super(context);
        }

        @Override
        public boolean onPluginNotExistsForActivity(Context context, String plugin, Intent intent, int process) {
            // FIXME 当插件"没有安装"时触发此逻辑，可打开您的"下载对话框"并开始下载。
            // FIXME 其中"intent"需传递到"对话框"内，这样可在下载完成后，打开这个插件的Activity
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onPluginNotExistsForActivity: Start download... p=" + plugin + "; i=" + intent);
            }
            return super.onPluginNotExistsForActivity(context, plugin, intent, process);
        }
    }
}
