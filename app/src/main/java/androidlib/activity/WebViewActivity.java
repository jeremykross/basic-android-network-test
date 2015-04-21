package androidlib.activity;

import org.nicktate.clientexample.R;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import android.webkit.WebView;

public class WebViewActivity extends BasePageActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_page);
        vWebView = (WebView)findViewById(R.id.webview);
        
        vWebView.getSettings().setJavaScriptEnabled(true);
        vWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        vWebView.loadUrl("http://jeremykross.cloudapp.net:3449/");
    }

    WebView vWebView;
}
