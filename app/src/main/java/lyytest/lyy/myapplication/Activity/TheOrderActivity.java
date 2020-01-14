package lyytest.lyy.myapplication.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Map;

import lyytest.lyy.myapplication.R;
import lyytest.lyy.myapplication.config.Urls;
import lyytest.lyy.myapplication.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TheOrderActivity extends AppCompatActivity {
    private String oid,ostatus;
    private RatingBar ratingBar;
    private TextView location,update_time,end_time,ostatust,srealname,sphone,accept_time,oidt,to_where,from_where;
    private ImageView locationicon;
    private Button refund;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_order);
        ratingBar = (RatingBar)findViewById(R.id.rating);
        location=(TextView)findViewById(R.id.location);
        locationicon=(ImageView)findViewById(R.id.locationicon);
        refund = (Button)findViewById(R.id.refund);
        update_time=(TextView)findViewById(R.id.update_time);
        end_time = (TextView)findViewById(R.id.end_time);
        ostatust = (TextView)findViewById(R.id.ostatuso);
        srealname = (TextView)findViewById(R.id.srealname);
        sphone = (TextView)findViewById(R.id.srealname);
        accept_time=(TextView)findViewById(R.id.accept_time);
        oidt = (TextView)findViewById(R.id.oidt);
        to_where=(TextView)findViewById(R.id.to_where) ;
        from_where=(TextView)findViewById(R.id.to_where);

        oid=getIntent().getStringExtra("oid");
        ostatus=getIntent().getStringExtra("ostatus").toString();
        //  ratingBar.setVisibility(View.INVISIBLE);
        // ratingBar.setRating(0);
        //  ratingBar.setIsIndicator(true);
        if(Integer.valueOf(ostatus)==0){
            location.setVisibility(View.INVISIBLE);
            locationicon.setVisibility(View.INVISIBLE);
            ratingBar.setVisibility(View.INVISIBLE);
            ostatust.setText("状态:已接受,未配送");
        }
        else if(Integer.valueOf(ostatus)==1){
            ratingBar.setVisibility(View.INVISIBLE);
            ostatust.setText("状态:已接受,开始配送");
        }
        else if(Integer.valueOf(ostatus)==2){
            location.setVisibility(View.INVISIBLE);
            locationicon.setVisibility(View.INVISIBLE);
            ostatust.setText("状态:已完成");
        }
        else if(Integer.valueOf(ostatus)==-1){
            location.setVisibility(View.INVISIBLE);
            locationicon.setVisibility(View.INVISIBLE);
            refund.setVisibility(View.INVISIBLE);
            ostatust.setText("状态:已退款");
        }
        else if(Integer.valueOf(ostatus)==-2){
            location.setVisibility(View.INVISIBLE);
            locationicon.setVisibility(View.INVISIBLE);
            refund.setVisibility(View.INVISIBLE);
            ostatust.setText("状态:退款申请中");
        }
        getTheOrderInformation(oid);

        refund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent   intent = new Intent(TheOrderActivity.this, Refund_Activity.class);
                intent.putExtra("oid",oid);
                startActivity(intent);
            }
        });
    }

    private void getTheOrderInformation(String oid){
        //1、创建retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Urls.BASIC_URL2)
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //2、用retrofit加工出对应的接口实例对象
        ApiService api = retrofit.create(ApiService.class);
        // 3、获取适配转换Call对象
        Call<Map<String,Object>> call=api.postgetTheOrderInformation(Integer.valueOf(oid));
        //4、调用call.enqueue方法获取数据
        call.enqueue(new Callback<Map<String,Object>>() {
            @Override
            public void onResponse(Call<Map<String,Object>> call, Response<Map<String,Object>> response) {
                Map<String,Object> res = response.body();
                oidt.setText("订单号: "+getIntent().getStringExtra("oid"));
                srealname.setText("快递员名字: "+res.get("srealname"));
                sphone.setText("快递员手机号: "+res.get("sphone"));
                accept_time.setText("申请快递时刻: "+res.get("accept_time"));
                update_time.setText("开始配送时刻: "+res.get("update_time"));
                end_time.setText("订单完成时刻: "+res.get("end_time"));
                from_where.setText("起始地: "+res.get("from_where"));
                to_where.setText("出发地: "+res.get("to_where"));
            }

            @Override
            public void onFailure(Call<Map<String,Object>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
