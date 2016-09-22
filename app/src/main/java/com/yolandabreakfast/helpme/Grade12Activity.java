package com.yolandabreakfast.helpme;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by YolandaBreakfast on 2016/09/08.
 */
public class Grade12Activity extends AppCompatActivity{

    TextView functions;
    TextView calculus;
    TextView notes;
    TextView analyticGeometry;
    TextView nationalExam2014;
    TextView nationalExam2015;


    @Override
    public void setRequestedOrientation(int requestedOrientation) {
        super.setRequestedOrientation(requestedOrientation);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    @Override
    public void onCreate(Bundle saveInstanceState){

        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_grade12);

            }

    public void onClick(View view) {
        Intent intent = new Intent(Grade12Activity.this, DisplayActivity.class);

        switch (view.getId()) {
            case R.id.functions:
                intent.putExtra(DisplayActivity.EXTRA_PDF_NAME, "gr_12_maths_functions_questions_answers.pdf");
                break;
            case R.id.calculus:
                intent.putExtra(DisplayActivity.EXTRA_PDF_NAME, "gr_12_maths_calculus_questions_answers.pdf");
                break;
            case R.id.notes:
                intent.putExtra(DisplayActivity.EXTRA_PDF_NAME, "gr_12_maths_algebra_exponents_surds_notes_summaries.pdf");
                break;
            case R.id.analyticGeometry:
                intent.putExtra(DisplayActivity.EXTRA_PDF_NAME, "gr_12_maths_analytical_geometry_theory_2.pdf");
                break;
            case R.id.nationalExam2014:
                intent.putExtra(DisplayActivity.EXTRA_PDF_NAME, "gr_12_maths_national_november_2014_paper_1_and_paper_2_questions_and_memos");
                break;
            case R.id.nationalExam2015:
                intent.putExtra(DisplayActivity.EXTRA_PDF_NAME, "gr_12_maths_national_november_2015_exam_paper_1_paper_2_questions_memos.pdf");

        }

        startActivity(intent);
    }


        }

