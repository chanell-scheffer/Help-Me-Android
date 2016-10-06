package com.yolandabreakfast.helpme;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;


/**
 * Created by YolandaBreakfast on 2016/09/08.
 */
public class Grade10Activity extends AppCompatActivity implements View.OnClickListener {

   TextView trigFunctions;
   TextView trigInvestigation;
   TextView euclidean;
   TextView quadrilaterals;
   TextView exPaperQuestions;
   TextView exPaperMemo;



    @Override
    public void setRequestedOrientation(int requestedOrientation) {
        super.setRequestedOrientation(requestedOrientation);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    @Override
    public void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_grade10);


    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(Grade10Activity.this, DisplayActivity.class);

        switch (view.getId()) {
            case R.id.trigFunctions:
                intent.putExtra(DisplayActivity.EXTRA_PDF_NAME, "gr_10_trig_ratios_functions_and_investigation_exercise_questions.pdf");
                break;
            case R.id.trigInvestigation:
                intent.putExtra(DisplayActivity.EXTRA_PDF_NAME, "gr_10_trig_ratios_investigation_exercise_answers.pdf");
                break;
            case R.id.euclidean:
                intent.putExtra(DisplayActivity.EXTRA_PDF_NAME, "gr_10_euclidean_geometry_and_exercise_questions_answers.pdf");
                break;
            case R.id.quadrilaterals:
                intent.putExtra(DisplayActivity.EXTRA_PDF_NAME, "gr_10_quadrilaterals_summary.pdf");
                break;
            case R.id.exemplarPaperQuestions:
                intent.putExtra(DisplayActivity.EXTRA_PDF_NAME, "grade_10_maths_exemplar_paper_1_and_paper_2_questions.pdf");
                break;
            case R.id.exemplarPaperMemos:
                intent.putExtra(DisplayActivity.EXTRA_PDF_NAME, "grade_10_maths_exemplar_paper_1_and_paper_2_memos.pdf");
                break;

        }

        startActivity(intent);
    }
}