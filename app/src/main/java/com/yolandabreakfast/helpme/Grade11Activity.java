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
public class Grade11Activity extends AppCompatActivity {

    @Override
    public void setRequestedOrientation(int requestedOrientation) {
        super.setRequestedOrientation(requestedOrientation);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }


    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_grade11);
    }


    public void onClick(View view) {
        Intent intent = new Intent(Grade11Activity.this, DisplayActivity.class);

        switch (view.getId()) {
            case R.id.functionsGraphs:
                intent.putExtra(DisplayActivity.EXTRA_PDF_NAME, "gr_11_functions_graphs_and_exercise_questions_answers.pdf");
                break;
            case R.id.functionsSummary:
                intent.putExtra(DisplayActivity.EXTRA_PDF_NAME, "gr_11_functions_summary_the_effects_of_the_parameters.pdf");
                break;
            case R.id.functionsDomain:
                intent.putExtra(DisplayActivity.EXTRA_PDF_NAME, "gr_11_functions_summary_domain_range.pdf");
                break;
            case R.id.analyticalGeometry:
                intent.putExtra(DisplayActivity.EXTRA_PDF_NAME, "gr_11_analytical_geometry_and_exercise_questions_answers.pdf");
                break;
            case R.id.measurementFormulae:
                intent.putExtra(DisplayActivity.EXTRA_PDF_NAME, "grade_11_maths_measurement_formulae_total_surface_area_and_volume.pdf");
                break;


        }

        startActivity(intent);
    }

}