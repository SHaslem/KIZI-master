/*
 * Copyright 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * Changes made to open licensed software for use by KIZI LLC on behalf of Nevada Jumpstarter LLC:
 * Addition of sensor orientation readings for purpose of sensing 'levelness' of device
 * Addition of Sending email after photo is taken
 *
 *
 */


package nvjumpstarter.kizi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class capture extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();

        //intake of passed parameters from form
        Float height = bundle.getFloat("height");
        Float width  = bundle.getFloat("width");
        Float depth =  bundle.getFloat("depth");
        String name  = bundle.getString("name");
        String notes = bundle.getString("notes");
        String layoutName  = bundle.getString("layoutName");


        setContentView(R.layout.activity_capture);
        //initialize drawing of box and circle based on the sizes passed by user
        Box box = new Box(this, width, height, depth, name, notes, layoutName);
        addContentView(box, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

        if (null == savedInstanceState) {
            Camera2BasicFragment cameraInstance = Camera2BasicFragment.newInstance();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, cameraInstance)
                    .commit();
            cameraInstance.setValues(bundle);
        }
    }




}