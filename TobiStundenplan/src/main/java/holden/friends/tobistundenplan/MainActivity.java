package holden.friends.tobistundenplan;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.webkit.WebChromeClient;

public class MainActivity extends Activity implements View.OnClickListener{

    Button bRefresh,bTest;
    String planurl = "https://hypate.webuntis.com/WebUntis/#Timetable?type=1&formatId=4&id=103";
    String schoolurl = "http://cms.cbs.ka.bw.schule.de/cms/index.php";
    static WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initGuiComponents();

        WebSettings webSettings = webView.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webView.setWebViewClient(new Callback());


        webView.loadUrl(schoolurl);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bRefresh:
                webView.loadUrl(planurl);
                break;
        }
    }

    private void initGuiComponents() {
        webView = (WebView) findViewById(R.id.webView);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebChromeClient(new WebChromeClient());
        bRefresh = (Button) findViewById(R.id.bRefresh);
            bRefresh.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_close:
                finish();
                break;
        }
        return false;
    }

    private class Callback extends WebViewClient{  //HERE IS THE MAIN CHANGE.

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return (false);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.d("cookie",url);
            if(url.contentEquals(schoolurl))
                webView.loadUrl(planurl);
        }

        @Override
        public void onReceivedSslError (WebView view, SslErrorHandler handler, SslError error) {
            Log.e("ssl","ssl error");
            handler.proceed();
        }
    }
    
}
