package com.example.scoredatabaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText scoreEditText;
    TextView displayTextView;
    MyDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // instantiate references to these XML components
        nameEditText = (EditText) findViewById(R.id.name);
        scoreEditText = (EditText) findViewById(R.id.score);
        displayTextView = (TextView) findViewById(R.id.myScores);
        databaseHelper = new MyDatabaseHelper(this, null, null, 1);
        printDB();  // prints current database contents

    }


    public void addButtonClicked(View v) {
        // gets score name and value from edittext and uses it to create a new Scores object
        // it adds this element to the database and then reprints the database to show the change

        String name = nameEditText.getText().toString();
        if (name.length() != 0) {
            try {
                int scoreNum = Integer.parseInt(scoreEditText.getText().toString());
                Scores score = new Scores(name, scoreNum);
                databaseHelper.addScore(score);
                nameEditText.setText("");
                scoreEditText.setText("");

            } catch (Exception e) {
                Toast.makeText(this, "Enter a valid score", Toast.LENGTH_SHORT).show();
            }
        }

        else {
            Toast.makeText(this, "Enter a name", Toast.LENGTH_SHORT).show();
        }

        printDB();
    }

    public void removeButtonClicked(View v){
        // Requires the user to enter the name and score of an entry to delete
        // If the same name/score combo are in table more than once all entries are
        // deleted with that combination.

        String nameToRemove = nameEditText.getText().toString();
        int scoreToRemove = -1;

        // Verify the name field isn't empty
        if(nameToRemove.length() != 0) {

            try {
                // Try to convert score field to an int
                scoreToRemove = Integer.parseInt(scoreEditText.getText().toString());

                // If there is a name, and an int, then call removeScore method
                databaseHelper.removeScore(nameToRemove, scoreToRemove);

                // clear the edittext fields
                nameEditText.setText("");
                scoreEditText.setText("");

            } catch (Exception e) {
                // the value in the score edittext wasn't an int
                Toast.makeText(this, "Invalid score", Toast.LENGTH_SHORT).show();
            }
        }

        else {
            Toast.makeText(this, "Enter a name", Toast.LENGTH_SHORT).show();
        }


        printDB();
    }

    public void printDB() {
        // calls the method that creates a string of all the database elements
        // sets this string to the text in Textview component

        String dbString = databaseHelper.databasetoString();
        displayTextView.setText(dbString);
    }
}
