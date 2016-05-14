package com.brunsting.jacob.mobilemathclass;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class QuizQuestions extends Activity {
    private int numQuestions;
    private int questionsCorrect;
    private int questionNumber;
    private int tutorialStage = -1;
    private String currentAnswer;
    private boolean[] questionScores;
    private String[] questionKey;
    private String[] answerKey;
    private String[] userAnswers;
    private int quizType;
    private Quiz quiz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_questions);

        // Button Elements
        final Button localOneBtn = (Button)findViewById(R.id.oneBtn);
        final Button localTwoBtn = (Button)findViewById(R.id.twoBtn);
        final Button localThreeBtn = (Button)findViewById(R.id.threeBtn);
        final Button localFourBtn = (Button)findViewById(R.id.fourBtn);
        final Button localFiveBtn = (Button)findViewById(R.id.fiveBtn);
        final Button localSixBtn = (Button)findViewById(R.id.sixBtn);
        final Button localSevenBtn = (Button)findViewById(R.id.sevenBtn);
        final Button localEightBtn = (Button)findViewById(R.id.eightBtn);
        final Button localNineBtn = (Button)findViewById(R.id.nineBtn);
        final Button localZeroBtn = (Button)findViewById(R.id.zeroBtn);
        final Button localDivideBtn = (Button)findViewById(R.id.divideBtn);
        final Button localDeleteBtn = (Button)findViewById(R.id.deleteBtn);
        final Button localEnterBtn = (Button)findViewById(R.id.enterBtn);
        final Button localNegativeBtn = (Button)findViewById(R.id.negativeBtn);
        final Button localClearBtn = (Button)findViewById(R.id.clearBtn);
        final Button localTutorialBtn = (Button)findViewById(R.id.quizQuestionsTutorialNextBtn);

        // Click listeners
        // Go to the next tutorial question
        localTutorialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tutorialStage < 6) {
                    nextTutorialStage();
                }
            }
        });

        // Add button input to the users answer
        localOneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToAnswer("1");
            }
        });

        localTwoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToAnswer("2");
            }
        });

        localThreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToAnswer("3");
            }
        });

        localFourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToAnswer("4");
            }
        });

        localFiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToAnswer("5");
            }
        });

        localSixBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToAnswer("6");
            }
        });

        localSevenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToAnswer("7");
            }
        });

        localEightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToAnswer("8");
            }
        });

        localNineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToAnswer("9");
            }
        });

        localZeroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToAnswer("0");
            }
        });

        localDivideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToAnswer("/");
            }
        });

        // Try to delete the last character in the answer
        localDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFromAnswer();
            }
        });

        // Clear the answer, maintining the required default characters
        localClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set the initial value to "x = " if the question has an algebraic solution, and to "" if it is not
                if (quizType == 5 || quizType == 6 || quizType == 7) {
                    currentAnswer = "x = ";
                } else {
                    currentAnswer = "";
                }

                TextView answerTxt = (TextView) findViewById(R.id.quizQuestionsAnswerTxt);
                answerTxt.setText(currentAnswer);
            }
        });

        // Check the users answer
        localEnterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Only enable the enter button if the tutorial is complete
                if (tutorialStage == -1) {
                    checkAnswer(currentAnswer);
                }
            }
        });

        // Add a negative sign if possible
        localNegativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Only add a negative button if it occurs before any other non-default characters
                if (currentAnswer.length() == 0 || (currentAnswer.length() == 4 && currentAnswer.charAt(0) == 'x')) {
                    addToAnswer("-");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Preferences
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        final int color1 = prefs.getInt("color1", 0);
        final int color2 = prefs.getInt("color2", 0);
        final boolean tutorialComplete = prefs.getBoolean("tutorialComplete", false);
        final int quizNumQuestions = prefs.getInt("numQuestions", 20);

        // Values from previous activity
        quizType = getIntent().getExtras().getInt("quizType");
        final int quizDifficulty = getIntent().getExtras().getInt("quizDifficulty");

        // Set variables to their initial values
        questionNumber = 0;
        currentAnswer = "";
        questionsCorrect = 0;
        quiz = new Quiz(quizType, quizDifficulty, quizNumQuestions);
        numQuestions = quiz.getNumQuestions();
        questionKey = quiz.getQuestionKey();
        answerKey = quiz.getAnswerKey();
        questionScores = new boolean[numQuestions];
        userAnswers = new String[numQuestions];

        // Activity Elements
        final Button localOneBtn = (Button)findViewById(R.id.oneBtn);
        final Button localTwoBtn = (Button)findViewById(R.id.twoBtn);
        final Button localThreeBtn = (Button)findViewById(R.id.threeBtn);
        final Button localFourBtn = (Button)findViewById(R.id.fourBtn);
        final Button localFiveBtn = (Button)findViewById(R.id.fiveBtn);
        final Button localSixBtn = (Button)findViewById(R.id.sixBtn);
        final Button localSevenBtn = (Button)findViewById(R.id.sevenBtn);
        final Button localEightBtn = (Button)findViewById(R.id.eightBtn);
        final Button localNineBtn = (Button)findViewById(R.id.nineBtn);
        final Button localZeroBtn = (Button)findViewById(R.id.zeroBtn);
        final Button localDivideBtn = (Button)findViewById(R.id.divideBtn);
        final Button localDeleteBtn = (Button)findViewById(R.id.deleteBtn);
        final Button localEnterBtn = (Button)findViewById(R.id.enterBtn);
        final Button localNegativeBtn = (Button)findViewById(R.id.negativeBtn);
        final Button localClearBtn = (Button)findViewById(R.id.clearBtn);
        final Button localTutorialBtn = (Button)findViewById(R.id.quizQuestionsTutorialNextBtn);

        final TextView localQuestionTxt = (TextView)findViewById(R.id.quizQuestionsQuestionTxt);
        final TextView localQuestionNumberTxt = (TextView)findViewById(R.id.quizQuestionsQuestionNumberTxt);
        final TextView localCurrentAnswer = (TextView)findViewById(R.id.quizQuestionsAnswerTxt);

        final ProgressBar localProgressBar = (ProgressBar)findViewById(R.id.quizQuestionsProgressBar);

        final FrameLayout localBackground1 = (FrameLayout) findViewById(R.id.correctQuestionBackground);
        final FrameLayout localBackground2 = (FrameLayout) findViewById(R.id.incorrectQuestionBackground);

        final RelativeLayout localLayout = (RelativeLayout) findViewById(R.id.quizQuestionsLayout);
        final RelativeLayout localTutorialLayout = (RelativeLayout) findViewById(R.id.quizQuestionsTutorialLayout);

        // Set the properties of the elements based on the preferences
        // (I think this is the least messy way of doing this, xml files shouldn't be modified at runtime, and the
        //    individual element preferences can't reference a java preference, so they have to be changed manually
        localOneBtn.setBackgroundColor(color2);
        localTwoBtn.setBackgroundColor(color2);
        localThreeBtn.setBackgroundColor(color2);
        localFourBtn.setBackgroundColor(color2);
        localFiveBtn.setBackgroundColor(color2);
        localSixBtn.setBackgroundColor(color2);
        localSevenBtn.setBackgroundColor(color2);
        localEightBtn.setBackgroundColor(color2);
        localNineBtn.setBackgroundColor(color2);
        localZeroBtn.setBackgroundColor(color2);
        localDivideBtn.setBackgroundColor(color2);
        localDeleteBtn.setBackgroundColor(color2);
        localEnterBtn.setBackgroundColor(color2);
        localNegativeBtn.setBackgroundColor(color2);
        localClearBtn.setBackgroundColor(color2);

        localOneBtn.setTextColor(color1);
        localTwoBtn.setTextColor(color1);
        localThreeBtn.setTextColor(color1);
        localFourBtn.setTextColor(color1);
        localFiveBtn.setTextColor(color1);
        localSixBtn.setTextColor(color1);
        localSevenBtn.setTextColor(color1);
        localEightBtn.setTextColor(color1);
        localNineBtn.setTextColor(color1);
        localZeroBtn.setTextColor(color1);
        localDivideBtn.setTextColor(color1);
        localDeleteBtn.setTextColor(color1);
        localEnterBtn.setTextColor(color1);
        localNegativeBtn.setTextColor(color1);
        localClearBtn.setTextColor(color1);

        localQuestionTxt.setTextColor(color2);
        localQuestionNumberTxt.setTextColor(color2);
        localCurrentAnswer.setTextColor(color2);

        localProgressBar.getProgressDrawable().setColorFilter(color1, PorterDuff.Mode.SRC_IN);

        localProgressBar.setBackgroundColor(color2);

        localBackground1.setAlpha(0);
        localBackground2.setAlpha(0);

        localLayout.setBackgroundColor(color1);

        // Skip the tutorial if it has already been completed
        if (tutorialComplete) {
            localTutorialLayout.setVisibility(View.INVISIBLE);
            tutorialStage = -1;
        } else {
            localTutorialLayout.setVisibility(View.VISIBLE);
            tutorialStage = 0;
            nextTutorialStage();
        }

        // Go to the first quesiton, since the default value for the question number is 0
        nextQuestion();
    }

    // nextTutorialStage() goes to the next tutorial stage, changing the button highlighting and
    //   tutorial text
    private void nextTutorialStage() {
        // Increment the tutorial stage
        tutorialStage ++;

        // Preferences
        final SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        final int buttonColor = prefs.getInt("color2", 0);

        // Set the highlight color to a brighter, desaturated version of the original button color
        final int highlightColor = brightenDesaturateColor(buttonColor);

        // Buttons in the keypad and tutorial screen
        final Button localOneBtn = (Button)findViewById(R.id.oneBtn);
        final Button localTwoBtn = (Button)findViewById(R.id.twoBtn);
        final Button localThreeBtn = (Button)findViewById(R.id.threeBtn);
        final Button localFourBtn = (Button)findViewById(R.id.fourBtn);
        final Button localFiveBtn = (Button)findViewById(R.id.fiveBtn);
        final Button localSixBtn = (Button)findViewById(R.id.sixBtn);
        final Button localSevenBtn = (Button)findViewById(R.id.sevenBtn);
        final Button localEightBtn = (Button)findViewById(R.id.eightBtn);
        final Button localNineBtn = (Button)findViewById(R.id.nineBtn);
        final Button localZeroBtn = (Button)findViewById(R.id.zeroBtn);
        final Button localDivideBtn = (Button)findViewById(R.id.divideBtn);
        final Button localDeleteBtn = (Button)findViewById(R.id.deleteBtn);
        final Button localEnterBtn = (Button)findViewById(R.id.enterBtn);
        final Button localNegativeBtn = (Button)findViewById(R.id.negativeBtn);
        final Button localClearBtn = (Button)findViewById(R.id.clearBtn);
        TextView localTutorialText = (TextView)findViewById(R.id.quizQuestionsTutorialTxt);

        switch(tutorialStage) {
            case 1:
                // Initially do not highlight any buttons
                localTutorialText.setText(R.string.tutorialText1);
                break;
            case 2:
                // Highlight the entire keypad
                localOneBtn.setBackgroundColor(highlightColor);
                localTwoBtn.setBackgroundColor(highlightColor);
                localThreeBtn.setBackgroundColor(highlightColor);
                localFourBtn.setBackgroundColor(highlightColor);
                localFiveBtn.setBackgroundColor(highlightColor);
                localSixBtn.setBackgroundColor(highlightColor);
                localSevenBtn.setBackgroundColor(highlightColor);
                localEightBtn.setBackgroundColor(highlightColor);
                localNineBtn.setBackgroundColor(highlightColor);
                localZeroBtn.setBackgroundColor(highlightColor);
                localDivideBtn.setBackgroundColor(highlightColor);
                localDeleteBtn.setBackgroundColor(buttonColor);
                localNegativeBtn.setBackgroundColor(highlightColor);

                localTutorialText.setText(R.string.tutorialText2);
                break;
            case 3:
                // Only highlight the division button, and remove the other highlights
                localOneBtn.setBackgroundColor(buttonColor);
                localTwoBtn.setBackgroundColor(buttonColor);
                localThreeBtn.setBackgroundColor(buttonColor);
                localFourBtn.setBackgroundColor(buttonColor);
                localFiveBtn.setBackgroundColor(buttonColor);
                localSixBtn.setBackgroundColor(buttonColor);
                localSevenBtn.setBackgroundColor(buttonColor);
                localEightBtn.setBackgroundColor(buttonColor);
                localNineBtn.setBackgroundColor(buttonColor);
                localZeroBtn.setBackgroundColor(buttonColor);
                localDeleteBtn.setBackgroundColor(buttonColor);
                localEnterBtn.setBackgroundColor(buttonColor);
                localNegativeBtn.setBackgroundColor(buttonColor);
                localClearBtn.setBackgroundColor(buttonColor);

                localTutorialText.setText(R.string.tutorialText3);
                break;
            case 4:
                // Only highlight the negative button, and remove the highlight from the division button
                localDivideBtn.setBackgroundColor(buttonColor);
                localNegativeBtn.setBackgroundColor(highlightColor);

                localTutorialText.setText(R.string.tutorialText4);
                break;
            case 5:
                // Only highlight the enter button, and remove the highlight from the negative button
                localNegativeBtn.setBackgroundColor(buttonColor);
                localEnterBtn.setBackgroundColor(highlightColor);

                localTutorialText.setText(R.string.tutorialText5);
                break;
            case 6:
                // Remove the highlight from the enter button
                localEnterBtn.setBackgroundColor(buttonColor);

                // Hide the tutorial layout
                findViewById(R.id.quizQuestionsTutorialLayout).setVisibility(View.INVISIBLE);

                // Make the stage -1, showing that the tutorial is complete
                tutorialStage = -1;

                // Make the tutorial complete preference true, preventing the tutorial from being run again
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("tutorialComplete", true);
                editor.apply();
                break;
        }
    }

    // addToAnswer(input) adds input on to the users current answer
    private void addToAnswer(String input){
        // Only add on to the answer if the length is less than 16
        if (currentAnswer.length() < 16) {
            currentAnswer = currentAnswer + input;
            final TextView answerTxt = (TextView) findViewById(R.id.quizQuestionsAnswerTxt);
            answerTxt.setText(currentAnswer);
        }
    }

    // setCurrentProgress(progress) sets the progress bar that tells the user the number of
    //   questions left in the quiz to progress
    private void setCurrentProgress(int progress) {
        ProgressBar localQuestionProgress = (ProgressBar)findViewById(R.id.quizQuestionsProgressBar);
        localQuestionProgress.setProgress(progress);
    }

    // setQuestion(question) sets the current question to question
    private void setQuestion(String question){
        ((TextView)findViewById(R.id.quizQuestionsQuestionTxt)).setText(question);
        String quizQuestionNumberText = questionNumber + ". ";
        ((TextView) findViewById(R.id.quizQuestionsQuestionNumberTxt)).setText(quizQuestionNumberText);
    }

    // deleteFromAnswer() removes the last character in the answer as long as it is not removing
    //   a default character, like '=', which should always stay in the answer
    private void deleteFromAnswer(){
        // Only remove if the length is not 0, and if either the length is smaller than two, meaning the question is not
        //   algebraic so the first character can be removed, or if the second last character is not '=', which would
        //   imply that no characters have been added to the initial answer, "x = "
        boolean canRemove = (currentAnswer.length() != 0) && (currentAnswer.length() < 2 || '=' != currentAnswer.charAt(currentAnswer.length() - 2));
        if (canRemove) {
            currentAnswer = currentAnswer.substring(0, currentAnswer.length() - 1);
            TextView answerTxt = (TextView) findViewById(R.id.quizQuestionsAnswerTxt);
            answerTxt.setText(currentAnswer);
        }
    }

    // checkAnswer(answer) determines if answer is correct or incorrect, stores the result, makes a
    //   screen flash based on the correctness of the question, and then proceeds to the next
    //   question
    private void checkAnswer(String answer){
        // Only check the answer if the question number is valid (Should always be true)
        if (questionNumber - 1 < numQuestions) {
            // Store the users answer in an array
            userAnswers[questionNumber - 1] = answer;

            // Check the users answer
            if (answer.equals(answerKey[questionNumber - 1])) {
                // If the users answer is correct, the question score for this question is true, and the number of
                //    questions correct is incremented
                questionScores[questionNumber - 1] = true;
                questionsCorrect++;

                // Make the screen flash green, since localBackground is just a green rectangle covering the screen
                FrameLayout localBackground = (FrameLayout) findViewById(R.id.correctQuestionBackground);
                localBackground.setAlpha(1);
                ObjectAnimator animation = ObjectAnimator.ofFloat(localBackground, View.ALPHA, 0);
                animation.setDuration(1000);
                animation.start();
            } else {
                // If the user answered incorrectly, they question score for this question is false
                questionScores[questionNumber - 1] = false;

                // Make the screen flash red, since localBackground is just a red rectangle covering the screen
                FrameLayout localBackground = (FrameLayout) findViewById(R.id.incorrectQuestionBackground);
                localBackground.setAlpha(1);
                ObjectAnimator animation = ObjectAnimator.ofFloat(localBackground, View.ALPHA, 0);
                animation.setDuration(1000);
                animation.start();
            }
        }

        // Go to the next question, or exit if the last question has been answered
        nextQuestion();
    }

    // nextQuestion() sets the interface to the next question
    private void nextQuestion(){
        // Increment the question number
        questionNumber ++;

        // Check if all of the question have been answered
        if (questionNumber - 1 == numQuestions){
            // Call the quizOver method if all of the questions have been answered
            quizOver();
        } else {
            // Set the default value of the current answer
            if (quizType == 5 || quizType == 6 || quizType == 7) {
                // If the question is algebraic, set the default answer to "x = "
                currentAnswer = "x = ";
            } else {
                // If the question is not algebraic, set the default answer to ""
                currentAnswer = "";
            }

            // Add nothing to the answer, forcing the answer text to reset
            addToAnswer("");

            // Set the new question
            setQuestion(questionKey[questionNumber - 1]);

            // Increment the progress bar
            setCurrentProgress(100 * questionNumber / numQuestions);
        }
    }

    // quizOver() goes on to the QuizOver activity, giving it the score for each question, and
    //   the quiz information
    private void quizOver(){
        Intent intentBundle = new Intent(QuizQuestions.this, QuizOver.class);
        Bundle selectBundle = new Bundle();
        selectBundle.putBooleanArray("Question Scores", questionScores);
        selectBundle.putStringArray("Question Key", questionKey);
        selectBundle.putStringArray("Answer Key", answerKey);
        selectBundle.putStringArray("User Answers", userAnswers);
        selectBundle.putInt("Score", (100 * (100 * questionsCorrect) / (100 * numQuestions)));
        intentBundle.putExtras(selectBundle);
        startActivity(intentBundle);
    }

    // brightenDesaturateColor(color) takes a color, color, desaturates it so it is grey, and
    //   brightens it if possible
    private int brightenDesaturateColor(int color) {
        // Get the RGB values of the color
        int R = (color & 0xFFFF0000);
        int G = (color & 0xFFFF00);
        int B = (color & 0xFFFF);

        // Find the HSV representation of the color
        float[] hsvRepresentation = new float[3];
        Color.RGBToHSV(R, G, B, hsvRepresentation);

        // Desaturate and increase the brightness by 20%
        hsvRepresentation[1] = 0;
        hsvRepresentation[2] += 0.2;

        // Ensure the brightness does not exceed 100%
        if (hsvRepresentation[2] > 1) {
            hsvRepresentation[2] = 1;
        }

        // Return the resulting color
        return Color.HSVToColor(hsvRepresentation);
    }
}
