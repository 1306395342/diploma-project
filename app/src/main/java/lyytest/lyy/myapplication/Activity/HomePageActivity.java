package lyytest.lyy.myapplication.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import lyytest.lyy.myapplication.MainActivity;
import lyytest.lyy.myapplication.R;

public class HomePageActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userindex);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.info:
                Intent intent = new Intent(HomePageActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;
        }

    }
}
