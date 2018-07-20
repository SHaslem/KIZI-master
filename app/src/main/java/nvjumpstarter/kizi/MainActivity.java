/*This code was developed for Kizi LLC by Nevada Jumpstarter LLC
*
* The app is desgined to take a photo for KIZI's
* image processing software.
* The app takes in information from the user describing the size
* of the drawer they are purchasing KIZI lining for
* the app then ensure that the photo is taken from a distance to minimize
* camera lense distorion while maximizing percentage of screen taken up by the drawer
* contents.
* The app also ensure the photo is taken at a level angle to avoid distortion*/

package nvjumpstarter.kizi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                goToActivity();

            }

        });
    }

    private void goToActivity() {

        Intent intent = new Intent(this, size_input.class);

        startActivity(intent);

    }
}
