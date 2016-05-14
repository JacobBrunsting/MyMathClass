package com.brunsting.jacob.mobilemathclass;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class QuizCreation extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_creation);

        // Buttons in the activity
        final Button localArithmeticBtn = (Button)findViewById(R.id.quizCreationType1Btn);
        final Button localMultiplyBtn = (Button)findViewById(R.id.quizCreationType2Btn);
        final Button localAddSubFraction = (Button)findViewById(R.id.quizCreationType3Btn);
        final Button locallMultDivFractionBtn = (Button)findViewById(R.id.quizCreationType4Btn);
        final Button localArithmeticAlgebraBtn = (Button)findViewById(R.id.quizCreationType5Btn);
        final Button localMultAlgebraBtn = (Button)findViewById(R.id.quizCreationType6Btn);
        final Button localFracAlgebraBtn = (Button)findViewById(R.id.quizCreationType7Btn);

        // Button click listeners
        // Set the quiz type to 1, and go to the difficulty selection screen
        localArithmeticBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterSelection(1);
            }
        });

        // Set the quiz type to 2, and go to the difficulty selection screen
        localMultiplyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {enterSelection(2);}
        });

        // Set the quiz type to 3, and go to the difficulty selection screen
        localAddSubFraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {enterSelection(3);}
        });

        // Set the quiz type to 4, and go to the difficulty selection screen
        locallMultDivFractionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {enterSelection(4);}
        });

        // Set the quiz type to 5, and go to the difficulty selection screen
        localArithmeticAlgebraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {enterSelection(5);}
        });

        // Set the quiz type to 6, and go to the difficulty selection screen
        localMultAlgebraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {enterSelection(6);}
        });

        // Set the quiz type to 7, and go to the difficulty selection screen
        localFracAlgebraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {enterSelection(7);}
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Preferences
        final SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        final int color1 = prefs.getInt("color1", 0);
        final int color2 = prefs.getInt("color2", 0);

        // Elements of the activity
        final Button localArithmeticBtn = (Button)findViewById(R.id.quizCreationType1Btn);
        final Button localMultiplyBtn = (Button)findViewById(R.id.quizCreationType2Btn);
        final Button localAddSubFraction = (Button)findViewById(R.id.quizCreationType3Btn);
        final Button locallMultDivFractionBtn = (Button)findViewById(R.id.quizCreationType4Btn);
        final Button localArithmeticAlgebraBtn = (Button)findViewById(R.id.quizCreationType5Btn);
        final Button localMultAlgebraBtn = (Button)findViewById(R.id.quizCreationType6Btn);
        final Button localFracAlgebraBtn = (Button)findViewById(R.id.quizCreationType7Btn);

        final TextView localTitleTxt = (TextView)findViewById(R.id.quizCreationTitleTxt);

        final RelativeLayout localLayout = (RelativeLayout)findViewById(R.id.questionCreationLayout);

        // Set the properties of the elements based on the preferences
        localArithmeticBtn.setBackgroundColor(color2);
        localMultiplyBtn.setBackgroundColor(color2);
        localAddSubFraction.setBackgroundColor(color2);
        locallMultDivFractionBtn.setBackgroundColor(color2);
        localArithmeticAlgebraBtn.setBackgroundColor(color2);
        localMultAlgebraBtn.setBackgroundColor(color2);
        localFracAlgebraBtn.setBackgroundColor(color2);

        localArithmeticBtn.setTextColor(color1);
        localMultiplyBtn.setTextColor(color1);
        localAddSubFraction.setTextColor(color1);
        locallMultDivFractionBtn.setTextColor(color1);
        localArithmeticAlgebraBtn.setTextColor(color1);
        localMultAlgebraBtn.setTextColor(color1);
        localFracAlgebraBtn.setTextColor(color1);

        localTitleTxt.setTextColor(color2);

        localLayout.setBackgroundColor(color1);
    }

    // enterSelection() starts the DifficultySelect activity, passing it the current quiz type
    private void enterSelection(int selection){
        Intent intentBundle = new Intent(QuizCreation.this, DifficultySelect.class);
        Bundle selectBundle = new Bundle();
        selectBundle.putInt("quizType", selection);
        intentBundle.putExtras(selectBundle);
        startActivity(intentBundle);
    }
}
