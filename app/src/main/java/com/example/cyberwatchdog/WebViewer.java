package com.example.cyberwatchdog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_viewer);

        Intent n = getIntent();
      String  pos = n.getStringExtra("web");
      int posInt=Integer.parseInt(pos.trim());





        WebView webView;
        webView = (WebView)findViewById(R.id.scamView);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);


if (posInt==0) {
    webView.loadUrl("https://www.gadgetsnow.com/tech-news/delhi-police-is-alerting-you-about-online-scams-related-to-social-engineering/articleshow/76254195.cms");

}
        if (posInt==1) {
            webView.loadUrl("https://indianexpress.com/article/cities/ahmedabad/3-arrested-cheating-rs-59000-6460414/");

        }
        if (posInt==2) {
            webView.loadUrl("https://www.theregister.com/2020/05/28/google_branded_phishing/");

        }
        if (posInt==3) {
            webView.loadUrl("https://www.forbes.com/sites/thomasbrewster/2020/06/11/watch-out-theres-a-big-black-lives-matter-scam-about/");

        }
        if (posInt==4) {
            webView.loadUrl("https://nakedsecurity.sophos.com/2020/05/21/scammers-target-covid-19-cares-act-relief-scheme/");

        }
        if (posInt==5) {
            webView.loadUrl("https://amp.theguardian.comtechnology/2020/jun/20/hackers-target-nsw-school-online-accounts-in-phishing-campaign");

        }





    }

}
