package lyytest.lyy.myapplication.service;


import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by hzy on 2019/1/10
 **/
public interface ApiService {
    //test
    @POST("user/hello")
    Call<Map<String,Object>> test1();

    @FormUrlEncoded
    @POST("user/login")
    Call<Map<String,Object>> postLogin(@Field("username") String username, @Field("password") String password,
                                       @Field("timestamp") String timestamp);

    @FormUrlEncoded
    @POST("user/signup")
    Call<Map<String,Object>> postSignUp(@Field("username") String username, @Field("password") String password,
                                        @Field("phone")String phone,@Field("realname")String real_name);
}
