package com.davidmackessy.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    private TextView onePlayer;
    private TextView twoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onePlayer = (TextView) findViewById(R.id.txt_one_player);
        addClickListener(onePlayer);
        twoPlayer = (TextView) findViewById(R.id.txt_two_player);
        addClickListener(twoPlayer);
        setupSharedPreferences();
    }

    private void setupSharedPreferences() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String computerLevel = sp.getString("computerLevel", "rock hard");
        //if showing computer level then show here

        sp.registerOnSharedPreferenceChangeListener(this);
    }

    private void addClickListener(TextView textView) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewGame(v);
            }
        });
    }

    private void startNewGame(View onClickListener) {
        if(((TextView)onClickListener).getText().equals("1 Player\nv\nComputer")) {
            startOnePlayerGame();
        }else if(((TextView)onClickListener).getText().equals("2 Player\nlocally")){
            startTwoPlayerGame();
        }
    }

    private void startTwoPlayerGame() {
        Intent intent = new Intent(MainActivity.this, PlayGameActivity.class);
        intent.putExtra("gameType", 2);
        MainActivity.this.startActivity(intent);
    }

    private void startOnePlayerGame() {
        Intent intent = new Intent(MainActivity.this, PlayGameActivity.class);
        intent.putExtra("gameType", 1);
        MainActivity.this.startActivity(intent);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals("computerLevel")){
            String computerLevel = sharedPreferences.getString(key, "rock hard");
            //if showing computer level then show here
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_preferences, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItem = item.getItemId();
        if(menuItem == R.id.menu_pref){
            showPreferencesScreen();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showPreferencesScreen(){
        Context context = MainActivity.this;
        Intent intent = new Intent(context, PreferencesActivity.class);
        startActivity(intent);
    }
}
