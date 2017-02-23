package ljss.projekti;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface RegisterAPI {
    @FormUrlEncoded
    @POST("/~t5saja01/insert.php")
    public void insertData(
            @Field("longitude") Double longitude,
            @Field("latitude") Double latitude,
            @Field("altitude") Double altitude,
            @Field("distanceTravelled") Double distanceTravelled,
            @Field("speed") Double speed,
            Callback<Response> callback);
}