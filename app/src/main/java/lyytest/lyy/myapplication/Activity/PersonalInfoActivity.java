package lyytest.lyy.myapplication.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import lyytest.lyy.myapplication.MainActivity;
import lyytest.lyy.myapplication.R;

public class PersonalInfoActivity extends AppCompatActivity {
    private EditText usernameinfo,realnameinfo,phoneinfo;
    private Button logout;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalinfo);
        usernameinfo = (EditText)findViewById(R.id.usernameinfo);
        realnameinfo = (EditText)findViewById(R.id.real_nameinfo);
        phoneinfo = (EditText)findViewById(R.id.phoneinfo);
        logout = (Button)findViewById(R.id.btn_logout);
        sp = getSharedPreferences("data",MODE_PRIVATE);
        usernameinfo.setText(sp.getString("username",""));
        realnameinfo.setText(sp.getString("realname",""));
        phoneinfo.setText(sp.getString("phone",""));
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.edit().clear().commit();
                Toast.makeText(getApplicationContext(),"以撤销登陆",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(PersonalInfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
