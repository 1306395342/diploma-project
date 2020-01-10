package lyytest.lyy.myapplication.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MapView;

import java.util.ArrayList;
import java.util.List;
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

public class SendPackage extends AppCompatActivity {
    private MapView mapView;
    private LocationClient mlc;
    private EditText spdetailsregion, spregion,dpregion,dpregiondetails;
    private Button submitpackage;
    private String spdetailsregiont,spregiont,dpregiont,dpregiondetailst;
    private SharedPreferences sp;
    private Spinner spinner;
    private  TextView errorsubmitpackage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mlc = new LocationClient(getApplicationContext());
        mlc.registerLocationListener(new MyLocationListener());
        setContentView(R.layout.sendpackage);
        spdetailsregion = (EditText) findViewById(R.id.spdetailsregion);
        spregion = (EditText) findViewById(R.id.spregion);
        dpregion = (EditText)findViewById(R.id.dpregion);
        dpregiondetails = (EditText)findViewById(R.id.dpdetailsregion);
        submitpackage = (Button) findViewById(R.id.btn_submitpackage);
        spinner = (Spinner)findViewById(R.id.size);
        errorsubmitpackage = (TextView)findViewById(R.id.errorsubmitpackage);


        submitpackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    spregiont = spregion.getText().toString();
                    spdetailsregiont  =spdetailsregion.getText().toString();
                    dpregiont = dpregion.getText().toString();
                    dpregiondetailst = dpregiondetails.getText().toString();

                     sp = getSharedPreferences("data",MODE_PRIVATE);
                    submitpackage(spregiont,spdetailsregiont,dpregiont,dpregiondetailst);
            }
        });

        List<String> permissionList = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(SendPackage.this, Manifest.
                permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(SendPackage.this, Manifest
                .permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(SendPackage.this, Manifest
                .permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String [] permissions =permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(SendPackage.this,permissions,1);
        }else{
            requestLocation();
        }
    }

    private void submitpackage(String spregiont,String spdetailsregiont,String dpregiont,String dpdetailsregiont){
        //1、创建retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Urls.BASIC_URL2)
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //2、用retrofit加工出对应的接口实例对象
        ApiService api = retrofit.create(ApiService.class);
        // 3、获取适配转换Call对象
        Call<Map<String,Object>> call=api.postSubmitPackage(spregiont,spdetailsregiont,dpregiont,dpdetailsregiont,sp.getInt("uid",-1),spinner.getSelectedItemId()+"");
        //4、调用call.enqueue方法获取数据
        call.enqueue(new Callback<Map<String,Object>>() {
            @Override
            public void onResponse(Call<Map<String,Object>> call, Response<Map<String,Object>> response) {
                Map<String,Object> res = response.body();
                Double code=(Double)res.get("code");
                if(code==-1){
                    errorsubmitpackage.setText("请将信息填完整！");
                }
                else if(code==-2){
                    errorsubmitpackage.setText("包裹申请订单提交失败");
                }
               else{errorsubmitpackage.setText("包裹申请成功");
                    Toast.makeText(getApplicationContext(),"包裹申请成功,该包裹由快递员："+res.get("sendername")+"配送"+"  快递员手机号为"+res.get("senderphone"),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Map<String,Object>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void requestLocation() {
        initLocation();
        mlc.start();
    }

    public void initLocation() {
        LocationClientOption locationClientOption = new LocationClientOption();
        locationClientOption.setIsNeedAddress(true);
        mlc.setLocOption(locationClientOption);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须统一所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            StringBuilder currentPosition = new StringBuilder();
            currentPosition.append(bdLocation.getProvince());
            currentPosition.append(bdLocation.getCity());
            currentPosition.append(bdLocation.getDistrict());
            currentPosition.append(bdLocation.getStreet());
            spregion.setText(currentPosition);
        }
    }
    protected void onStop() {
        super.onStop();
        //停止定位
        mlc.stop();
    }
    protected  void onDestory(){
        super.onDestroy();
        mlc.stop();
    }
}
