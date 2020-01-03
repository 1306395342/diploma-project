package lyytest.lyy.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import lyytest.lyy.myapplication.MainActivity;
import lyytest.lyy.myapplication.R;
import lyytest.lyy.myapplication.config.Urls;
import lyytest.lyy.myapplication.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private EditText phone;
    private EditText real_name;
    private TextView errors;
    private Button signup;
    private String usernamet,passwordt,phonet,real_namet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        phone = (EditText)findViewById(R.id.phone);
        real_name = (EditText)findViewById(R.id.real_name);
        errors = (TextView)findViewById(R.id.errors);
        signup = (Button)findViewById(R.id.btn_signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernamet = username.getText().toString();
                passwordt=password.getText().toString();
                phonet = phone.getText().toString();
                real_namet=real_name.getText().toString();
                //Toast.makeText(getApplicationContext(),usernamet,Toast.LENGTH_LONG).show();
               signup(usernamet,passwordt,phonet,real_namet);

            }
        });

    }
       private void signup(String username,String password,String phone,String real_name){
           //1、创建retrofit
           Retrofit retrofit = new Retrofit.Builder()
                   .baseUrl(Urls.BASIC_URL2)
                   //设置数据解析器
                   .addConverterFactory(GsonConverterFactory.create())
                   .build();
           //2、用retrofit加工出对应的接口实例对象
           ApiService api = retrofit.create(ApiService.class);
           // 3、获取适配转换Call对象
           Call<Map<String,Object>> call=api.postSignUp(username,password,phone,real_name);
           //4、调用call.enqueue方法获取数据
           call.enqueue(new Callback<Map<String,Object>>() {
               @Override
               public void onResponse(Call<Map<String,Object>> call, Response<Map<String,Object>> response) {
                    Map<String,Object> res = response.body();
                    //Toast.makeText(getApplicationContext(),res.get("code").toString(),Toast.LENGTH_LONG).show();
                   Double code=(Double)res.get("code");
                    if(code==-1){
                        errors.setText("信息不能为空");
                    }
                    else if(code==-2){
                        errors.setText("用户名已存在");
                    }
                    else if(code==1){
                        Toast.makeText(getApplicationContext(),"注册成功".toString(),Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
               }

               @Override
               public void onFailure(Call<Map<String,Object>> call, Throwable t) {
                   Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
               }
           });
       }

}
