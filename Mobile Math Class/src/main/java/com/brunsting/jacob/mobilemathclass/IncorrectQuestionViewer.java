package com.brunsting.jacob.mobilemathclass;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class IncorrectQuestionViewer extends Activity {
    private boolean[] questionScores;
    private String[] questionKey;
    private String[] answerKey;
    private String[] userAnswers;
    private int score;
    private int numQuestions;
    private int questionNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incorrect_question_viewer);

        // Activity Button
        final Button localNextBtn = (Button)findViewById(R.id.incorrectQuestionViewerNextBtn);

        // Proceed to the next question
        localNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });

        // Proceed to the first question
        nextQuestion();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Preferences
        final SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        final int color1 = prefs.getInt("color1", 0);
        final int color2 = prefs.getInt("color2", 0);

        // Values from previous activity
        questionScores = getIntent().getExtras().getBooleanArray("Question Scores");
        questionKey = getIntent().getExtras().getStringArray("Question Key");
        answerKey = getIntent().getExtras().getStringArray("Answer Key");
        score = getIntent().getExtras().getInt("Score");
        userAnswers = getIntent().getExtras().getStringArray("User Answers");

        // Set critical variables
        numQuestions = questionScores.length;
        questionNumber = 0;

        // Activity Elements
        final Button localNextBtn = (Button)findViewById(R.id.incorrectQuestionViewerNextBtn);

        final TextView localTitleTxt = (TextView) findViewById(R.id.incorrectQuestionViewerTitleTxt);
        final TextView localQuestionTitleTxt = (TextView) findViewById(R.id.incorrectQuestionViewerQuestionTitleTxt);
        final TextView localQuestionTxt = (TextView) findViewById(R.id.incorrectQuestionViewerQuestionTxt);
        final TextView localAnswerTitleTxt = (TextView) findViewById(R.id.incorrectQuestionViewerAnswerTitleTxt);
        final TextView localAnswerTxt = (TextView) findViewById(R.id.incorrectQuestionViewerAnswerTxt);
        final TextView localActualAnswerTitleTxt = (TextView) findViewById(R.id.incorrectQuestionViewerActualAnswerTitleTxt);
        final TextView localActualAnswerTxt = (TextView) findViewById(R.id.incorrectQuestionViewerActualAnswerTxt);

        final RelativeLayout localLayout = (RelativeLayout)findViewById(R.id.incorrectQuestionViewerLayout);

        // Set the element properties based on the preferences
        localNextBtn.setBackgroundColor(color2);

        localNextBtn.setTextColor(color1);

        localNextBtn.setText(R.string.IncorrectViewerNextBtnText1);

        localTitleTxt.setTextColor(color2);
        localQuestionTitleTxt.setTextColor(color2);
        localQuestionTxt.setTextColor(color2);
        localAnswerTitleTxt.setTextColor(color2);
        localAnswerTxt.setTextColor(color2);
        localActualAnswerTitleTxt.setTextColor(color2);
        localActualAnswerTxt.setTextColor(color2);

        localLayout.setBackgroundColor(color1);
    }

    private void nextQuestion() {
        questionNumber++;

        if (questionNumber - 1 > numQuestions) {
            // Return to the score screen if the last question, and the return to score screen, have both been viewed
            returnToFinalScreen();
        } else if (questionNumber - 1 == numQuestions) {
            // If the last question was viewed, prepare to return to the score screen
            setFinalScreen();
        } else {
            if (questionScores[questionNumber - 1]) {
                // Skip this question if the score was true, i.e. the question was correctly answered
                nextQuestion();
            } else {
                // Assign names to the TextView elements that are changing
                TextView localQuestionAskedTxt = (TextView)findViewById(R.id.incorrectQuestionViewerQuestionTxt);
                TextView localIncorrectAnswerTxt = (TextView)findViewById(R.id.incorrectQuestionViewerAnswerTxt);
                TextView localCorrectAnswerTxt = (TextView)findViewById(R.id.incorrectQuestionViewerActualAnswerTxt);

                // Set the elements based on the current question number
                // Subtract one because arrays start at 0, and the questionNumber starts at 1
                localQuestionAskedTxt.setText(questionKey[questionNumber - 1]);
                localIncorrectAnswerTxt.setText(userAnswers[questionNumber - 1]);
                localCorrectAnswerTxt.setText(answerKey[questionNumber - 1]);
            }
        }
    }

    // setFinalScreen() is called once all of the quiz questions have been answered, it just
    //   changes the next button text to indicate the quiz is complete, and the user can view their
    //   results
    private void setFinalScreen() {
        // Set the next button to display the return to score screen message
        final Button localNextBtn = (Button)findViewById(R.id.incorrectQuestionViewerNextBtn);
        localNextBtn.setText(R.string.IncorrectViewerNextBtnText2);
    }

    // returnToFinalScreen() starts the final score screen
    private void returnToFinalScreen(){
        Intent intentBundle = new Intent(IncorrectQuestionViewer.this, QuizOver.class);
        Bundle selectBundle = new Bundle();
        selectBundle.putBooleanArray("Question Scores", questionScores);
        selectBundle.putStringArray("Question Key", questionKey);
        selectBundle.putStringArray("Answer Key", answerKey);
        selectBundle.putStringArray("User Answers", userAnswers);
        selectBundle.putInt("Score", score);
        intentBundle.putExtras(selectBundle);
        startActivity(intentBundle);
    }
}
