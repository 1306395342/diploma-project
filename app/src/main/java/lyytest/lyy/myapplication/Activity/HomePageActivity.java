package lyytest.lyy.myapplication.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


import org.w3c.dom.Text;

import lyytest.lyy.myapplication.MainActivity;
import lyytest.lyy.myapplication.R;

public class HomePageActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageButton personalinfo,sendpackage,order;
    private TextView welcomeuser;
    private SharedPreferences sp;
    private  Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userindex);
        personalinfo = (ImageButton)findViewById(R.id.userinfo);
        sendpackage = (ImageButton)findViewById(R.id.sendpackage);
        order = (ImageButton)findViewById(R.id.order);
        welcomeuser = (TextView)findViewById(R.id.welcomeuser);

        sp = getSharedPreferences("data",MODE_PRIVATE);
        welcomeuser.setText("欢迎您 "+sp.getString("username","")+" Kitty物流用户");
        personalinfo.setOnClickListener(this);
        sendpackage.setOnClickListener(this);
        order.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.userinfo:
                 intent = new Intent(HomePageActivity.this, PersonalInfoActivity.class);
                startActivity(intent);
                break;
             case R.id.sendpackage:
             intent = new Intent(HomePageActivity.this, SendPackage.class);
            startActivity(intent);
            break;
            case R.id.order:
                intent = new Intent(HomePageActivity.this, OrderActivity.class);
                startActivity(intent);
        }

    }
}
