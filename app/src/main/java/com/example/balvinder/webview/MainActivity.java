package com.example.balvinder.webview;

import android.app.Activity;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.clans.fab.FloatingActionMenu;

import java.io.File;
import java.io.FileOutputStream;


public class MainActivity extends Activity {
    com.github.clans.fab.FloatingActionButton floatingActionButton2;
    FloatingActionMenu materialDesignFAM;
    private WebView wv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wv1 = (WebView) findViewById(R.id.webView);
        wv1.setWebViewClient(new MyBrowser());

        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv1.loadUrl("https://www.google.co.in/");

        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);

        floatingActionButton2 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.action_menu_item2);

        //SAVE
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                materialDesignFAM.toggle(true);
                downloadToPDF();


            }
        });
    }

    public void downloadToPDF() {

        PdfDocument doc = new PdfDocument();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
      //  PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(500, 1500, 1).create();

        PdfDocument.Page page = doc.startPage(pageInfo);


        page.getCanvas().setDensity(200);

        wv1.draw(page.getCanvas());

        doc.finishPage(page);

        try {
            File root = Environment.getExternalStorageDirectory();
            File file = new File(root, wv1.getTitle()+".pdf");
            FileOutputStream out = new FileOutputStream(file);
            doc.writeTo(out);
            out.close();
            doc.close();

        } catch (Exception e) {
            throw new RuntimeException("Error generating file", e);
        }

    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }


    }

}











