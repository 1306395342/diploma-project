package lyytest.lyy.myapplication.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import lyytest.lyy.myapplication.R;
import lyytest.lyy.myapplication.config.Urls;
import lyytest.lyy.myapplication.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Refund_Activity extends AppCompatActivity {
   private  Spinner spinner;
   private EditText otherreason;
   private TextView oidt;
   String oid;
   String complaint;
   Button btn_refund;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_);
        spinner = (Spinner)findViewById(R.id.reason);
        otherreason=(EditText)findViewById(R.id.otherreason);
        oidt=(TextView)findViewById(R.id.oidre);
        btn_refund=(Button)findViewById(R.id.refundtheorder);

        oid = getIntent().getStringExtra("oid");
        oidt.setText("订单号: "+oid);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sInfo=parent.getItemAtPosition(position).toString();
                if(sInfo.equals("其他")){
                    otherreason.setVisibility(View.VISIBLE);
                }
                else otherreason.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       btn_refund.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               complaint=(String) spinner.getSelectedItem();
               if(complaint.equals("其他")){
                   complaint=otherreason.getText().toString();
               }
             complaintOrder(oid,complaint);
           }
       });
    }

    private void complaintOrder(String oid,String description){
        //1、创建retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Urls.BASIC_URL2)
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //2、用retrofit加工出对应的接口实例对象
        ApiService api = retrofit.create(ApiService.class);
        // 3、获取适配转换Call对象
        Call<Map<String,Object>> call=api.postComplaint(Integer.valueOf(oid),description);
        //4、调用call.enqueue方法获取数据
        call.enqueue(new Callback<Map<String,Object>>() {
            @Override
            public void onResponse(Call<Map<String,Object>> call, Response<Map<String,Object>> response) {
                Map<String,Object> res = response.body();
                String code=(String) res.get("code");
                if(code.equals("-1")){
                    Toast.makeText(getApplicationContext(),"请把退款原因填写完毕！",Toast.LENGTH_LONG).show();
                }
                else{ Toast.makeText(getApplicationContext(),"退款申请已发出",Toast.LENGTH_LONG).show();}
            }

            @Override
            public void onFailure(Call<Map<String,Object>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
