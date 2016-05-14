package com.brunsting.jacob.mobilemathclass;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class QuizSettings extends Activity {

    // Default color saturation and brightness, applied to every color the user selects
    private final double COLOR_1_SATURATION = 0.6;
    private final double COLOR_1_BRIGHTNESS = 0.9;
    private final double COLOR_2_SATURATION = 0.6;
    private final double COLOR_2_BRIGHTNESS = 0.65;
    private boolean color1Changed = false;
    private boolean color2Changed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_settings);
    }

    @Override
    protected void onResume() {
        super.onResume();


        // Preferences
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        final int color1 = prefs.getInt("color1", 0);
        final int color2 = prefs.getInt("color2", 0);
        final int lastColor1Progress = prefs.getInt("color1ProgressBar", 0);
        final int lastColor2Progress = prefs.getInt("color2ProgressBar", 0);
        final int numQuestions = prefs.getInt("numQuestions", 20);

        // Elements in the activity (final because they are used inside other functions in the click listeners)
        final Button localReturnBtn = (Button)findViewById(R.id.quizSettingsHomeBtn);

        final TextView localTxt1 = (TextView)findViewById(R.id.quizSettingsTitleTxt);
        final TextView localTxt2 = (TextView)findViewById(R.id.quizSettingsColor1TitleTxt);
        final TextView localTxt3 = (TextView)findViewById(R.id.quizSettingsColor2TitleTxt);
        final TextView localTxt4 = (TextView)findViewById(R.id.quizSettingsNumQuestionsTitleTxt);
        final TextView localNumQuestionsTxt = (TextView)findViewById(R.id.quizSettingsNumQuestionsValueTxt);

        final SeekBar localSettingsNumQuestionsBar = (SeekBar)findViewById(R.id.quizSettingsNumQuestionsBar);
        final SeekBar localSettingsColor1Bar = (SeekBar)findViewById(R.id.quizSettingsPrimaryColor);
        final SeekBar localSettingsColor2Bar = (SeekBar)findViewById(R.id.quizSettingsSecondaryColor);

        // Set the properties of the elements based on the preferences
        localReturnBtn.setBackgroundColor(color2);

        localReturnBtn.setTextColor(color1);
        findViewById(R.id.settingsLayout).setBackgroundColor(color1);

        localTxt1.setTextColor(color2);
        localTxt2.setTextColor(color2);
        localTxt3.setTextColor(color2);
        localTxt4.setTextColor(color2);
        localNumQuestionsTxt.setTextColor(color2);

        localSettingsNumQuestionsBar.setProgress(numQuestions);
        localSettingsColor1Bar.setProgress(lastColor1Progress);
        localSettingsColor2Bar.setProgress(lastColor2Progress);
        localNumQuestionsTxt.setText(String.valueOf(numQuestions));

        // Set the button click listeners and seekbar change listeners
        // Go back to the home screen and save the new preferences
        localReturnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the color values from the seekbars
                final int color1 = generateColor(localSettingsColor1Bar.getProgress(), COLOR_1_SATURATION, COLOR_1_BRIGHTNESS);
                final int color2 = generateColor(localSettingsColor2Bar.getProgress(), COLOR_2_SATURATION, COLOR_2_BRIGHTNESS);

                // Get the new number of questions from the seekbar
                int numQuestions = localSettingsNumQuestionsBar.getProgress();

                if (numQuestions < 5) {
                    numQuestions = 5;
                }

                //Edit the preferences
                final SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                final SharedPreferences.Editor editor = prefs.edit();

                // Only save the color values if the colors are changed
                if (color1Changed) {
                    editor.putInt("color1", color1);
                    editor.putInt("color1ProgressBar", localSettingsColor1Bar.getProgress());
                }

                if (color2Changed) {
                    editor.putInt("color2", color2);
                    editor.putInt("color2ProgressBar", localSettingsColor2Bar.getProgress());
                }

                editor.putInt("numQuestions", numQuestions);
                editor.apply();

                // Go back to the home screen
                startActivity(new Intent(QuizSettings.this, Home.class));
            }
        });

        // Adjust some of the on screen colors to match the new seekbar value, in order to give
        //   the user a preview of what the rest of the application will look like with the new
        //   color
        localSettingsColor1Bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // If the first seekbar is changed, adjust the background and button text colors
                final int color = generateColor(localSettingsColor1Bar.getProgress(), COLOR_1_SATURATION, COLOR_1_BRIGHTNESS);

                final RelativeLayout localBackground = (RelativeLayout) findViewById(R.id.settingsLayout);
                localBackground.setBackgroundColor(color);
                localReturnBtn.setTextColor(color);

                color1Changed = true;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // Adjust some of the on screen colors to match the new seekbar value, in order to give
        //   the user a preview of what the rest of the application will look like with the new
        //   color
        localSettingsColor2Bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // If the second seekbar is changed, adjust the text colors and button background colors
                final int color = generateColor(localSettingsColor2Bar.getProgress(), COLOR_2_SATURATION, COLOR_2_BRIGHTNESS);

                localReturnBtn.setBackgroundColor(color);
                localTxt1.setTextColor(color);
                localTxt2.setTextColor(color);
                localTxt3.setTextColor(color);
                localTxt4.setTextColor(color);
                localNumQuestionsTxt.setTextColor(color);

                color2Changed = true;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Change the text that tells the user how many questions will be in the quiz
        localSettingsNumQuestionsBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // If the third seekbar is changed, set the number of questions text to the new value
                int nProgress = progress;
                if (nProgress < 5) {
                    nProgress = 5;
                }

                localNumQuestionsTxt.setText(String.valueOf(nProgress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    // generateColor(color, saturation, brightness) generates a color from an integer color value
    //    (from 0 - 150), a saturation value from 0-1, and a brightness value from 0-1
    private int generateColor(int color, double saturation, double brightness) {
        // Initiate the RGB and HSV variables
        int R;
        int G;
        int B;
        float[] hsvRepresentation = new float[3];

        // Check if the color is smaller than 120, meaning it should be a color, not a shade
        if (color < 120) {
            // Set the RGB variables based on several formulas designed to cover all possible combinations of one or two
            //   RGB values, where the the remaining values are either 0, or 255, covering most aesthetically pleasing
            //   possible background colors
            R = Math.abs(color - 60) * (255 / 20) - 255;
            G = (40 - Math.abs(color - 80)) * (255 / 20);
            B = (40 - Math.abs(color - 40)) * (255 / 20);

            // Restrict all RGB values to between 0 and 255
            if (R > 255) R = 255;
            if (R < 0) R = 0;
            if (G > 255) G = 255;
            if (G < 0) G = 0;
            if (B > 255) B = 255;
            if (B < 0) B = 0;

            // Convert the resulting RGB value to an HSV value to more easily adjust the saturation/brightness
            Color.RGBToHSV(R, G, B, hsvRepresentation);

            // Adjust the brightness based on the function arguments
            hsvRepresentation[1] *= saturation;
            hsvRepresentation[2] *= brightness;

        } else {
            // If the color is greater than 120, make it a shade, so remove all saturation
            hsvRepresentation[0] = 0;
            hsvRepresentation[1] = 0;

            // Then set the brightness linearly based on the distance to the extreme end of the progress bar
            hsvRepresentation[2] = (((float)(150 - color) / (float) 30));
        }

        // Return a hex color value from the HSV value
        return Color.HSVToColor(255, hsvRepresentation);
    }
}
