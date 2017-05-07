package com.makejin.beautyproject_android.Utils.Connections;


import com.makejin.beautyproject_android.Model.Brand;
import com.makejin.beautyproject_android.Model.Category;
import com.makejin.beautyproject_android.Model.Cosmetic;
import com.makejin.beautyproject_android.Model.GlobalResponse;
import com.makejin.beautyproject_android.Model.User;

import java.util.List;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by kksd0900 on 16. 9. 29..
 */
public interface CSConnection{

    @POST("/users/login")
    Observable<User> user_login(@Body User user);

    @GET("/users/logout")
    Observable<GlobalResponse> user_logout();


    @GET("/users/{user_id}")
    Observable<User> oneUser_get(@Path("user_id") String user_id);




    @POST("/users/{user_id}/cosmetics")
    Observable<GlobalResponse> myCosmetic_post(@Body Cosmetic cosmetic,
                                           @Path("user_id") String user_id);
    @GET("/users/{user_id}/cosmetics")
    Observable<List<Cosmetic>> myCosmetic_get(@Path("user_id") String user_id);

    @GET("/users/{user_id}/cosmetics/expiration_date")
    Observable<List<Cosmetic>> expiration_cosmetic_get(@Path("user_id") String user_id);





    @GET("/users/{user_id}/cosmetics/{cosmetic-id}")
    Observable<Cosmetic> myOneCosmetic_get(@Path("user_id") String user_id,
                                         @Path("cosmetic-id") String cosmetic_id);

    @PUT("/users/{user_id}/cosmetics/{cosmetic-id}")
    Observable<GlobalResponse> myOneCosmetic_put(@Body Map<String, Object> fields,
                                         @Path("user_id") String user_id,
                                         @Path("cosmetic-id") String cosmetic_id);

    @DELETE("/users/{user_id}/cosmetics/{cosmetic-id}")
    Observable<GlobalResponse> myOneCosmetic_delete(@Path("user_id") String user_id,
                                                    @Path("cosmetic-id") String cosmetic_id);



    @GET("/users/{user_id}/cosmetics")
    Observable<List<Cosmetic>> myCosmetic(@Path("user_id") String user_id);


    @GET("/users/{user_id}/cosmetics")
    Observable<List<Cosmetic>> myMainCategoryCosmetic(@Path("user_id") String user_id,
                                         @Query("main") String main_category,
                                         @Query("page") int page_num);

    @GET("/users/{user_id}/cosmetics?main={sub-category}")
    Observable<List<Cosmetic>> mySubCategoryCosmetic(@Path("user_id") String user_id,
                                                      @Path("sub-category") String sub_category);



    @GET("/category")
    Observable<List<Category>> category();






    @GET("/brand")
    Observable<List<Brand>> brand();






    @GET("/cosmetics")
    Observable<List<Cosmetic>> cosmetic(@Query("brand") String brand,
                                        @Query("main") String main_category,
                                        @Query("sub") String sub_category,
                                        @Query("page") int page_num);






    @GET("/cosmetics?brand={brand-id}")
    Observable<List<Cosmetic>> cosmeticInBrand(@Path("brand-id") String brand_id);







    @GET("/cosmetics?main={main-category}")
    Observable<List<Cosmetic>> cosmeticInMainCategory(@Path("main-category") String main_category);






    @GET("/cosmetics?main={sub-category}")
    Observable<List<Cosmetic>> cosmeticInSubCategory(@Path("sub-category") String sub_category);






    @GET("/cosmetics/{cosmetic-id}")
    Observable<Cosmetic> oneCosmetic(@Path("cosmetic-id") String cosmetic_id);

    @POST("/cosmetics/request")
    Observable<GlobalResponse> cosmetic_request(@Body Map<String, Object> fields);

    @POST("/cosmetics/report")
    Observable<GlobalResponse> cosmetic_report(@Body Map<String, Object> fields);






    @PUT("/users/skin_type")
    Observable<GlobalResponse> user_updateSkinType(@Body Map<String, Object> fields);

    @PUT("/users/skin_trouble")
    Observable<GlobalResponse> user_updateSkinTrouble(@Body Map<String, Object> fields);

    @GET("/users/find/{user_id}")
    Observable<List<User>> user_recommendUsers(@Path("user_id") String user_id);

    @GET("/users/find/{user_id}/search/{search_keyword}")
    Observable<List<User>> user_searchUsers(@Path("user_id") String user_id, @Path("search_keyword") String search_keyword);

    @PUT("/users/token")
    Observable<GlobalResponse> user_updateToken(@Body Map<String, Object> fields);

}


