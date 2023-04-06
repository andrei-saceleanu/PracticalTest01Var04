package ro.pub.cs.systems.eim.practicaltest01var04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PracticalTest01Var04SecondaryActivity extends AppCompatActivity {


    private TextView name_text, group_text;
    private Button ok_button, cancel_button;

    private ButtonOnClickListener listener = new ButtonOnClickListener();

    private class ButtonOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.ok_button:
                    setResult(RESULT_OK, null);
                    break;
                case R.id.cancel_button:
                    setResult(RESULT_CANCELED, null);
                    break;

            }
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var04_secondary);

        name_text = findViewById(R.id.name2);
        group_text = findViewById(R.id.group2);
        ok_button = findViewById(R.id.ok_button);
        cancel_button = findViewById(R.id.cancel_button);
        ok_button.setOnClickListener(listener);
        cancel_button.setOnClickListener(listener);

        Intent intent = getIntent();
        if(intent != null && intent.getExtras().containsKey("stud_name")){
            name_text.setText(intent.getStringExtra("stud_name"));
        }
        if(intent != null && intent.getExtras().containsKey("stud_group")){
            group_text.setText(intent.getStringExtra("stud_group"));
        }

    }
}