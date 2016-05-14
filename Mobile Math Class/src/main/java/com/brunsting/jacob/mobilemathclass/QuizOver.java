package com.brunsting.jacob.mobilemathclass;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class QuizOver extends Activity {
    private boolean[] questionScores;
    private String[] questionKey;
    private String[] answerKey;
    private String[] userAnswers;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_over);

        // Buttons of the activity
        final Button localNewQuizBtn = (Button)findViewById(R.id.quizOverNewQuizBtn);
        final  Button localViewAllIncorrectBtn = (Button)findViewById(R.id.quizOverIncQuestionsBtn);

        // Button click listener
        // Go to the home screen
        localNewQuizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuizOver.this, Home.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Preferences
        final SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        final int color1 = prefs.getInt("color1", 0);
        final int color2 = prefs.getInt("color2", 0);

        // Get the values from the previous activity
        questionScores = getIntent().getExtras().getBooleanArray("Question Scores");
        questionKey = getIntent().getExtras().getStringArray("Question Key");
        answerKey = getIntent().getExtras().getStringArray("Answer Key");
        score = getIntent().getExtras().getInt("Score");
        userAnswers = getIntent().getExtras().getStringArray("User Answers");

        // Elements of the activity
        final Button localNewQuizBtn = (Button)findViewById(R.id.quizOverNewQuizBtn);
        final  Button localViewAllIncorrectBtn = (Button)findViewById(R.id.quizOverIncQuestionsBtn);

        final TextView localTitleTxt = (TextView)findViewById(R.id.quizOverTitleTxt);
        final TextView localScoreTxt = (TextView)findViewById(R.id.quizOverScoreTxt);

        final RelativeLayout localLayout = (RelativeLayout)findViewById(R.id.quizOverLayout);

        // Set the properties of the elements based on the program preferences
        localNewQuizBtn.setTextColor(color1);
        localViewAllIncorrectBtn.setTextColor(color1);

        localNewQuizBtn.setBackgroundColor(color2);
        localViewAllIncorrectBtn.setBackgroundColor(color2);

        localTitleTxt.setTextColor(color2);
        localScoreTxt.setTextColor(color2);

        String scoreText = score + "%";
        localScoreTxt.setText(scoreText);

        localLayout.setBackgroundColor(color1);

        // Only set a click listener if there were incorrect questions
        if (score == 100) {
            // Fade out the view incorrect questions button if there were no incorrect questions
            localViewAllIncorrectBtn.setAlpha((float)0.5);
        } else {
            // If there were incorrect questions, set the click listener, and make the view
            //   incorrect questions button fully faded in
            localViewAllIncorrectBtn.setAlpha(1);
            // Go to thee IncorrectQuestionViewer activity, passing it the quiz information
            localViewAllIncorrectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentBundle = new Intent(QuizOver.this, IncorrectQuestionViewer.class);
                    Bundle selectBundle = new Bundle();
                    selectBundle.putBooleanArray("Question Scores", questionScores);
                    selectBundle.putStringArray("Question Key", questionKey);
                    selectBundle.putStringArray("Answer Key", answerKey);
                    selectBundle.putStringArray("User Answers", userAnswers);
                    selectBundle.putInt("Score", score);
                    intentBundle.putExtras(selectBundle);
                    startActivity(intentBundle);
                }
            });
        }
    }
}
