/* This code was developed for KIZI LLC by Nevada JumpStarter LLC */

package nvjumpstarter.kizi;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class size_input extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_size_input);
        //Bundle bundle = getIntent().getExtras();
        Intent intent = getIntent();
        Integer cameFromNext = intent.getIntExtra("newDrawer", -1);
        if(cameFromNext != -1) {
            String name = intent.getStringExtra("name");
            String layoutName = intent.getStringExtra("layoutName");

            TextView nameField = (TextView) findViewById(R.id.nameText);
            nameField.setText(name);
            TextView layoutNameField = (TextView) findViewById(R.id.layoutNameText);
            layoutNameField.setText(layoutName);

        }
    }

    //Gather inputs from form, do basic sanity checks and call camera activity
    public void continueToPhoto(View v){

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        float width = 0;
        float height = 0;
        String title = "No Title Given";
        float depth = 0;
        String name = "John Doe";
        String layoutName = "None Provided";
        String notes = "No Notes";

        TextInputEditText widthField = findViewById(R.id.widthField);
        //widthField.setText("8");
        if (!widthField.getText().toString().isEmpty())
             width = Float.parseFloat(widthField.getText().toString());

        TextInputEditText heightField = findViewById(R.id.heightField);
        //heightField.setText("24");

        if (!heightField.getText().toString().isEmpty())
            height = Float.parseFloat(heightField.getText().toString());

        TextInputEditText titleField = findViewById(R.id.layoutField);
        if(!titleField.getText().toString().trim().isEmpty())
            title = titleField.getText().toString();

        TextInputEditText depthField =  findViewById(R.id.depthField);
        //depthField.setText("2");

        if(!depthField.getText().toString().trim().isEmpty())
            depth = Float.parseFloat(depthField.getText().toString());

        TextInputEditText nameField =  findViewById(R.id.nameField);
        //nameField.setText("Megan");
        if(!nameField.getText().toString().trim().isEmpty())
            name = nameField.getText().toString();

        TextInputEditText notesField = findViewById(R.id.notesField);
        if(!notesField.getText().toString().trim().isEmpty())
            notes = notesField.getText().toString();

        TextInputEditText layoutNameField =  findViewById(R.id.layoutField);
        if(!layoutNameField.getText().toString().trim().isEmpty())
            layoutName = layoutNameField.getText().toString();


        Log.i("values: ", width + " " + notes + " " + height);
        if ( width <=0 || height <= 0)
        {
            Toast.makeText(this, "Error: Width and Height must be positive non null values", Toast.LENGTH_LONG).show();
            Log.i("Error:", "Width and Height must be positive non null values");
            return;
        }


        Intent intent = new Intent(this, capture.class);

        intent.putExtra("width", width);
        intent.putExtra("height", height);
        intent.putExtra("title", title);
        intent.putExtra("name", name);
        intent.putExtra("depth", depth);
        intent.putExtra("notes", notes);
        intent.putExtra("layoutName", layoutName);

        startActivity(intent);

    }

}
