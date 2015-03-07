package fertilizantes.com.projectfert;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Created by Christian Msc on 31/12/2014.
 */
public class ActivityWebView extends Activity{
    private final String ruta_web = "file:///android_asset/productos/";
    private WebView wv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        wv = (WebView) findViewById(R.id.detallehtml);
        String link = (String) getIntent().getExtras().getSerializable("link");
        wv.loadUrl(ruta_web + link.replace(".php","") + ".htm");

    }
}
