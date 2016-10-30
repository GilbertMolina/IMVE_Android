package com.gmolina.sistemaimve;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private ProgressBar progressBar;
    private LinearLayout layoutProgress;
    private String url = "http://sistemaimve.tigrimigri.com/IMVE/index.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        layoutProgress = (LinearLayout) findViewById(R.id.layoutProgress);

        webView.setVisibility(View.GONE);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setDisplayZoomControls(false);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                webView.setVisibility(View.VISIBLE);
                layoutProgress.setVisibility(View.GONE);
                progressBar.setIndeterminate(false);
                super.onPageFinished(view, url);

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                layoutProgress.setVisibility(View.VISIBLE);
                progressBar.setIndeterminate(true);
                super.onPageStarted(view, url, favicon);
            }
        });

        if(isOnline()) {
            webView.loadUrl(url);
        } else {
            setContentView(R.layout.activity_sin_conexion);
//            String summary = "<html><head><meta charset=\"UTF-8\"></head><body><font color='red'>No hay conexión a internet</font></body></html>";
//            webView.loadData(summary, "text/html", null);
            toast("No hay conexión a internet");
        }
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }
}
