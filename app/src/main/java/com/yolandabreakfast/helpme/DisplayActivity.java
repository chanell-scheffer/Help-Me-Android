package com.yolandabreakfast.helpme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.joanzapata.pdfview.PDFView;

/**
 * Created by YolandaBreakfast on 2016/09/08.
 */
public class DisplayActivity extends AppCompatActivity {

    public static final String EXTRA_PDF_NAME = "pdfName";

    @Override
    public void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.display);

        if (getIntent() != null && getIntent().hasExtra(EXTRA_PDF_NAME)) {
            PDFView pdfview = (PDFView) findViewById(R.id.pdfview);
            pdfview.fromAsset(getIntent().getStringExtra(EXTRA_PDF_NAME))
                    .defaultPage(1)
                    .showMinimap(true)
                    .swipeVertical(true)
                    .enableSwipe(true)
                    .load();
        }
    }
}
