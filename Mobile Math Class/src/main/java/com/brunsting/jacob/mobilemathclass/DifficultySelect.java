package com.brunsting.jacob.mobilemathclass;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DifficultySelect extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_select);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Values from previous activity
        final int quizType = getIntent().getExtras().getInt("quizType");

        // Preferences
        final SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        final int color1 = prefs.getInt("color1", 0);
        final int color2 = prefs.getInt("color2", 0);

        // Creating element objects
        final Button localEasyBtn = (Button)findViewById(R.id.difficultySelectEasyBtn);
        final Button localMedBtn = (Button)findViewById(R.id.difficultySelectMediumBtn);
        final Button localHardBtn = (Button)findViewById(R.id.difficultySelectHardBtn);
        final Button localExtBtn = (Button)findViewById(R.id.difficultySelectExtremeBtn);

        final TextView localTitleTxt = (TextView)findViewById(R.id.difficultySelectTitleTxt);

        final RelativeLayout localBackground = (RelativeLayout)findViewById(R.id.difficultySelectLayout);

        // Set properties of the elements based on the preferences
        localEasyBtn.setBackgroundColor(color2);
        localMedBtn.setBackgroundColor(color2);
        localHardBtn.setBackgroundColor(color2);
        localExtBtn.setBackgroundColor(color2);

        localEasyBtn.setTextColor(color1);
        localMedBtn.setTextColor(color1);
        localHardBtn.setTextColor(color1);
        localExtBtn.setTextColor(color1);

        localTitleTxt.setTextColor(color2);

        localBackground.setBackgroundColor(color1);

        // Button listeners
        // Start the quiz, using the quizType from the previous activity, and difficulty 0
        localEasyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz(quizType, 0);
            }
        });

        // Start the quiz, using the quizType from the previous activity, and difficulty 1
        localMedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz(quizType, 1);
            }
        });

        // Start the quiz, using the quizType from the previous activity, and difficulty 2
        localHardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz(quizType, 2);
            }
        });

        // Start the quiz, using the quizType from the previous activity, and difficulty 3
        localExtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz(quizType, 3);
            }
        });
    }

    // startQuiz(type difficulty) starts the QuizQuestions activity, setting the new quiz type to
    //   type (the type should have been determined by the QuizCreation activity, so we just have
    //   to pass it on), and setting the new quiz difficulty to difficulty, an integer value from
    //   0 to 3
    private void startQuiz(int type, int difficulty){
        Intent intentBundle = new Intent(DifficultySelect.this, QuizQuestions.class);
        Bundle selectBundle = new Bundle();
        selectBundle.putInt("quizType", type);
        selectBundle.putInt("quizDifficulty", difficulty);
        intentBundle.putExtras(selectBundle);
        startActivity(intentBundle);
    }
}
