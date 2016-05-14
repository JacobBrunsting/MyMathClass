package com.brunsting.jacob.mobilemathclass;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Home extends Activity {

    private final int DEFAULT_COLOR_1 = -10694170;
    private final int DEFAULT_COLOR_2 = -10658467;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Buttons of the activity
        final Button localMainBtn = (Button)findViewById(R.id.homeMainBtn);
        final Button localSettingsBtn = (Button)findViewById(R.id.homeSettingsBtn);

        // Button listeners
        // Go to the QuizCreation screen
        localMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, QuizCreation.class));
            }
        });

        // Go to the QuizSettings screen
        localSettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity((new Intent(Home.this, QuizSettings.class)));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Preferences
        final SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        final int color1 = prefs.getInt("color1", DEFAULT_COLOR_1);
        final int color2 = prefs.getInt("color2", DEFAULT_COLOR_2);

        // Set the colors to their default value if they are unset
        if (color1 == DEFAULT_COLOR_1) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("color1", DEFAULT_COLOR_1);
            editor.apply();
        }

        if (color2 == DEFAULT_COLOR_2) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("color2", DEFAULT_COLOR_2);
            editor.apply();
        }

        // Elements of the activity
        final Button localMainBtn = (Button)findViewById(R.id.homeMainBtn);
        final Button localSettingsBtn = (Button)findViewById(R.id.homeSettingsBtn);

        final TextView localTitleTxt = (TextView)findViewById(R.id.homeTitleTxt);

        final RelativeLayout localLayout = (RelativeLayout)findViewById(R.id.homeLayout);

        // Set the properties of the elements based on the preferences
        localMainBtn.setBackgroundColor(color2);
        localSettingsBtn.setBackgroundColor(color2);

        localMainBtn.setTextColor(color1);
        localSettingsBtn.setTextColor(color1);

        localTitleTxt.setTextColor(color2);

        localLayout.setBackgroundColor(color1);
    }
}
