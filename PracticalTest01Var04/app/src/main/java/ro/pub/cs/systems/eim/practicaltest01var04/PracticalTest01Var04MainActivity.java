package ro.pub.cs.systems.eim.practicaltest01var04;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01Var04MainActivity extends AppCompatActivity {


    private EditText name_edit, group_edit;
    private CheckBox name_check, group_check;
    private Button navigate_button, display_button;
    private TextView info_text;

    private IntentFilter intentFilter = new IntentFilter();

    private ButtonOnClickListener listener = new ButtonOnClickListener();

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[recv_tag]", intent.getStringExtra("[broad]"));
        }
    }
    private class ButtonOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.display_button:
                    boolean name_checked = name_check.isChecked();
                    boolean group_checked = group_check.isChecked();
                    String name = name_edit.getText().toString();
                    String group = group_edit.getText().toString();
                    if(name_checked == true && name.compareTo("")==0){
                        Toast.makeText(getApplicationContext(), "Name checked but content is empty",Toast.LENGTH_SHORT).show();
                    }else if(group_checked == true && group.compareTo("")==0){
                        Toast.makeText(getApplicationContext(), "Group checked but content is empty",Toast.LENGTH_SHORT).show();
                    }else {
                        String s = null;
                        if (name_checked == true && group_checked == true) {
                            s = name + " " + group;
                            Intent intent = new Intent(getApplicationContext(), PracticalTest01Var04Service.class);
                            intent.putExtra("name_stud",name);
                            intent.putExtra("group_stud", group);
                            getApplicationContext().startService(intent);
                        }
                        if (name_checked == false && group_checked == true) {
                            s = group;
                        }
                        if (name_checked == true && group_checked == false) {
                            s = name;
                        }
                        if (name_checked == false && group_checked == false) {
                            s = "";
                        }
                        info_text.setText(s);
                    }
                    break;
                case R.id.navigate_button:
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var04SecondaryActivity.class);
                    intent.putExtra("stud_name", name_edit.getText().toString());
                    intent.putExtra("stud_group",group_edit.getText().toString());
                    startActivityForResult(intent, 12345);
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var04_main);

        name_edit = findViewById(R.id.name_edit);
        group_edit =findViewById(R.id.group_edit);
        name_check = findViewById(R.id.name_check);
        group_check = findViewById(R.id.group_check);
        navigate_button = findViewById(R.id.navigate_button);
        display_button = findViewById(R.id.display_button);
        info_text = findViewById(R.id.info_text);
        display_button.setOnClickListener(listener);
        navigate_button.setOnClickListener(listener);

        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("name_edit",name_edit.getText().toString());
        outState.putString("group_edit",group_edit.getText().toString());
        outState.putString("info_text",info_text.getText().toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(messageBroadcastReceiver);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState.containsKey("name_edit")){
            name_edit.setText(savedInstanceState.getString("name_edit"));
        }
        if(savedInstanceState.containsKey("group_edit")){
            group_edit.setText(savedInstanceState.getString("group_edit"));
        }
        if(savedInstanceState.containsKey("info_text")){
            info_text.setText(savedInstanceState.getString("info_text"));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 12345){
            Toast.makeText(getApplicationContext(), "Activty returned with " + resultCode,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(getApplicationContext(), PracticalTest01Var04Service.class);
        stopService(intent);
    }

}