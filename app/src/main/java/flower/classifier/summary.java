package flower.classifier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import java.util.ArrayList;

public class summary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        Intent intent = getIntent();
        ArrayList<String> flower_names = (ArrayList<String>) getIntent().getSerializableExtra("classes");
        ArrayList<Float> confidences = (ArrayList<Float>) getIntent().getSerializableExtra("confidences");

        TextView classiffication = findViewById(R.id.textView6);
        TextView alt1 = findViewById(R.id.textView);
        TextView alt2 = findViewById(R.id.textView2);
        TextView alt3 = findViewById(R.id.textView4);

        ArrayList<String> alternatives=new ArrayList<String>();

        // Add first 3 runner ups
        for (int i = 0; i < 3; i++) {
            String resultString = "";
            resultString += flower_names.get(i) + " ";
            resultString += String.format("(%.1f%%) ", confidences.get(i) * 100.0f);
            alternatives.add(resultString);
        }

        alt1.setText(alternatives.get(0));
        alt2.setText(alternatives.get(1));
        alt3.setText(alternatives.get(2));
        classiffication.setText(flower_names.get(0));
    }

    /** Called when the user taps the Send button */
    public void callPictureTaker(View view) {
        Intent intent = new Intent(this, ClassifierActivity.class);
        startActivity(intent);
    }
}