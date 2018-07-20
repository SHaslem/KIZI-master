

/*This code was developed for KIZI LLC by Nevada JumpStarter LLC */

package nvjumpstarter.kizi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class nextDrawer extends AppCompatActivity {

    String layoutName;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_next_drawer);
}


public void takeAnother(View view){

    Intent intent = new Intent(this, size_input.class);

    intent.putExtra("newDrawer", 1);
    intent.putExtra("name", name);
    intent.putExtra("layoutName", layoutName);
    startActivity(intent);
}
}
