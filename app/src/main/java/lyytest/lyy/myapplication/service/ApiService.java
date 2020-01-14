package lyytest.lyy.myapplication.service;


import java.util.List;
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

    @FormUrlEncoded
    @POST("user/submitpackage")
    Call<Map<String,Object>> postSubmitPackage(@Field("spr") String spregiont, @Field("spdt") String spdetailsregiont,
                                               @Field("dpr")String dpregiont,@Field("dpdt")String dpregiondetailst,@Field("uid")int uid,
                                               @Field("size")String size
      );

    @FormUrlEncoded
    @POST("user/showuserorder")
    Call<List<Map<String,Object>>> postShowUserOrder(@Field("orderstatus") int orderstatus, @Field("uid")int  uid);

    @FormUrlEncoded
    @POST("user/getTheOrderInformation")
    Call<Map<String,Object>> postgetTheOrderInformation(@Field("oid") int oid);

    @FormUrlEncoded
    @POST("user/complaintorder")
    Call<Map<String,Object>> postComplaint(@Field("oid") int oid,@Field("description") String description);
}
