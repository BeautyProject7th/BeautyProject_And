package com.soma.beautyproject_android.Utils.Connections;


import com.soma.beautyproject_android.Main.MainActivity;
import com.soma.beautyproject_android.Model.Brand;
import com.soma.beautyproject_android.Model.Category;
import com.soma.beautyproject_android.Model.Cosmetic;
import com.soma.beautyproject_android.Model.DressingTable;
import com.soma.beautyproject_android.Model.ExpirationCosmetic;
import com.soma.beautyproject_android.Model.GlobalResponse;
import com.soma.beautyproject_android.Model.RatingEach;
import com.soma.beautyproject_android.Model.Review;
import com.soma.beautyproject_android.Model.User;
import com.soma.beautyproject_android.Model.Video;
import com.soma.beautyproject_android.Model.Video_Youtuber;
import com.soma.beautyproject_android.Model.Youtuber;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by kksd0900 on 16. 9. 29..
 */
public interface CSConnection{

    @POST("/users/login")
    Observable<User> user_login(@Body  Map<String, Object> user);

    @GET("/users/logout")
    Observable<GlobalResponse> user_logout();

    @GET("/users/{user_id}")
    Observable<User> oneUser_get(@Path("user_id") String user_id);




    @POST("/users/{user_id}/cosmetics")
    Observable<GlobalResponse> myCosmetic_post(@Body Cosmetic cosmetic,
                                           @Path("user_id") String user_id);
    @GET("/users/{user_id}/cosmetics")
    Observable<List<Cosmetic>> myCosmetic_get(@Path("user_id") String user_id);

    @GET("/users/{user_id}/cosmetics/expiration_date/{push_interval}")
    Observable<List<ExpirationCosmetic>> expiration_cosmetic_get(@Path("user_id") String user_id, @Path("push_interval") String push_interval);

    @GET("/users/{user_id}/cosmetics/expiration_date/only_use/{push_interval}")
    Observable<List<ExpirationCosmetic>> expiration_cosmetic_get_only_use(@Path("user_id") String user_id, @Path("push_interval") String push_interval);

    @GET("/users/check/{user_name}")
    Observable<GlobalResponse> checkNickName(@Path("user_name") String user_name);




    @GET("/users/{user_id}/cosmetics/{cosmetic-id}")
    Observable<Cosmetic> myOneCosmetic_get(@Path("user_id") String user_id,
                                         @Path("cosmetic-id") String cosmetic_id);

    @PUT("/users/{user_id}/cosmetics/{cosmetic-id}")
    Observable<GlobalResponse> myOneCosmetic_put(@Body Map<String, Object> fields,
                                         @Path("user_id") String user_id,
                                         @Path("cosmetic-id") String cosmetic_id);

    @POST("/users/cosmetics/{stat}")
    Observable<GlobalResponse> myOneCosmetic_post(@Path("stat") String stat, @Body Map<String, Object> fields);

    @DELETE("/users/{user_id}/cosmetics/{cosmetic-id}")
    Observable<GlobalResponse> myOneCosmetic_delete(@Path("user_id") String user_id,
                                                    @Path("cosmetic-id") String cosmetic_id);



    @GET("/users/{user_id}/cosmetics")
    Observable<List<Cosmetic>> myCosmetic(@Path("user_id") String user_id);


    @GET("/users/{user_id}/cosmetics")
    Observable<List<Cosmetic>> myMainCategoryCosmetic(@Path("user_id") String user_id,
                                         @Query("main") String main_category,
                                         @Query("page") int page_num);

    @GET("/users/{user_id}/cosmetics/only_use")
    Observable<List<Cosmetic>> myMainCategoryCosmetic_only_use(@Path("user_id") String user_id,
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

    @POST("/users/follow/{follower_id}/{followee_id}")
    Observable<GlobalResponse> user_follow_post(@Path("follower_id") String follower_id, @Path("followee_id") String followee_id);

    @GET("/users/follow/{follower_id}/{followee_id}")
    Observable<GlobalResponse> user_follow_get(@Path("follower_id") String follower_id, @Path("followee_id") String followee_id);

    @GET("/users/follow_number/{user_id}")
    Observable<List<String>> user_follow_number(@Path("user_id") String user_id);

    @GET("/users/load/following/{user_id}")
    Observable<List<User>> user_loadFollowingUsers(@Path("user_id") String user_id);

    @GET("/users/load/follower/{user_id}")
    Observable<List<User>> user_loadFollowerUsers(@Path("user_id") String user_id);

    @Multipart
    @POST("post/{user_id}/image/upload")
    Observable<GlobalResponse> fileUploadWrite(@Path("user_id") String user_id,
                                     @Part("post_image\"; filename=\"android_post_image_file") RequestBody file);

    @GET("/cosmetics/rank")
    Observable<List<Cosmetic>> cosmetic_rank();

    @GET("/users/match/user/{user_id}")
    Observable<List<User>> match_user(@Path("user_id") String user_id);

    @GET("/users/match/creator/{user_id}")
    Observable<List<User>> match_creator(@Path("user_id") String user_id);

    @GET("/users/get/follower/number/{user_id}")
    Observable<List<MainActivity.Count>> get_follower_number(@Path("user_id") String user_id);

    @GET("/users/get/cosmetic/number/{user_id}")
    Observable<List<MainActivity.Count>> get_cosmetic_number(@Path("user_id") String user_id);

    @GET("/cosmetics/dressing_table/rank")
    Observable<List<User>> dressing_table_rank();

    @GET("/cosmetics/search/auto_complete/{keyword}")
    Observable<List<String>> auto_complete_search(@Path("keyword") String keyword);

    @GET("/brand/search/{keyword}")
    Observable<List<Brand>> search_brand(@Path("keyword") String keyword);

    @GET("/cosmetics/search/{keyword}")
    Observable<List<Cosmetic>> search_cosmetic(@Path("keyword") String keyword);

    @GET("/cosmetics/search/more/{keyword}/{page_num}")
    Observable<List<Cosmetic>> search_cosmetic_more(@Path("keyword") String keyword, @Path("page_num") int page_num);

    @GET("/cosmetics/search/more/quantity/{keyword}")
    Observable<List<Integer>> search_cosmetic_more_quantity(@Path("keyword") String keyword);

    @GET("/cosmetics/search/limit_3/{keyword}")
    Observable<List<Cosmetic>> search_cosmetic_limit_3(@Path("keyword") String keyword);

    @GET("/cosmetics/search/by_brand/{keyword}/{page_num}")
    Observable<List<Cosmetic>> search_cosmetic_by_brand(@Path("keyword") String keyword, @Path("page_num") int page_num);

    @GET("/cosmetics/search/by_brand/quantity/{keyword}")
    Observable<List<Integer>> search_cosmetic_by_brand_quantity(@Path("keyword") String keyword);

    @GET("/video/search/{keyword}/{page_num}")
    Observable<List<Video_Youtuber>> search_video_more(@Path("keyword") String keyword, @Path("page_num") int page_num);

    @GET("/video/search/more/quantity/{keyword}")
    Observable<List<Integer>> search_video_more_quantity(@Path("keyword") String keyword);

    @GET("/video/search/{keyword}")
    Observable<List<Video_Youtuber>> search_video_one(@Path("keyword") String keyword);

    @GET("/youtuber/{youtuber_name}")
    Observable<Youtuber> get_youtuber(@Path("youtuber_name") String youtuber_name);

    @GET("/video/cosmetics/{video_id}")
    Observable<List<Cosmetic>> video_product(@Path("video_id") String id);

    @GET("/cosmetics/detail/dressing_table/review/{cosmetic_id}/{user_id}/{page_num}")
    Observable<List<Review>> get_review(@Path("cosmetic_id") String cosmetic_id, @Path("user_id") String user_id, @Path("page_num") int page_num);

    @GET("/cosmetics/detail/dressing_table/my_review/{cosmetic_id}/{user_id}")
    Observable<List<Review>> get_my_review(@Path("cosmetic_id") String cosmetic_id, @Path("user_id") String user_id);

    @GET("/cosmetics/get_each_rating/{cosmetic_id}")
    Observable<List<RatingEach>> get_each_rating(@Path("cosmetic_id") String cosmetic_id);

    @GET("/users/dressing_table/{user_id}/{cosmetic-id}")
    Observable<List<DressingTable>> my_cosmetic_get(@Path("user_id") String user_id,
                                                       @Path("cosmetic-id") String cosmetic_id);

    @GET("/users/my_cosmetic_info/{user_id}/{push_interval}")
    Observable<List<Integer>> get_my_cosmetic_info(@Path("user_id") String user_id,
                                                    @Path("push_interval") String push_interval);

    @PUT("/users/join")
    Observable<User> join(@Body Map<String, Object> fields);

    @GET("/users/my_info/{user_id}")
    Observable<User> get_my_info(@Path("user_id") String user_id);

    @POST("/users/like/cosmetic")
    Observable<GlobalResponse> post_like_cosmetic(@Body Map<String, Object> fields);

    @POST("/users/like/video")
    Observable<GlobalResponse> post_like_video(@Body Map<String, Object> fields);

    @DELETE("/users/like/cosmetic")
    Observable<GlobalResponse> delete_like_cosmetic(@Body Map<String, Object> fields);

    @POST("/users/delete/like/video")
    Observable<GlobalResponse> delete_like_video(@Body Map<String, Object> fields);

    @GET("/users/like/cosmetic/{user_id}/{cosmetic_id}")
    Observable<GlobalResponse> get_my_like_cosmetic(@Path("user_id") String user_id,@Path("cosmetic_id") String cosmetic_id);

    @GET("/users/like/video/{user_id}/{id}")
    Observable<GlobalResponse> get_my_like_video(@Path("user_id") String user_id,@Path("id") String id);

    @GET("/users/like/cosmetic/{user_id}")
    Observable<List<Cosmetic>> like_cosmetic_get(@Path("user_id") String user_id);

    @GET("/users/like/video/{user_id}")
    Observable<List<Video_Youtuber>> like_video_get(@Path("user_id") String user_id);

    @GET("/cosmetics/get_brand_product_quantity/{brand}")
    Observable<List<String>> get_brand_product_quantity(@Path("brand") String brand);
/*
    @GET("/video/view_video/{user_id}/{id}")
    Observable<GlobalResponse> view_video(@Path("user_id") String user_id, @Path("id") String id);
*/

    @POST("cosmetics/train/view")
    Observable<GlobalResponse> train_cosmetic_view(@Body Map<String, Object> fields);


    @POST("video/train/view")
    Observable<GlobalResponse> train_video_view(@Body Map<String, Object> fields);

    @GET("cosmetics/search2/{user_id}/{query}")
    Observable<List<Cosmetic>> search_cosmetic_get(@Path("user_id") String user_id,@Path("query") String query);

    @GET("cosmetics/recommend/{user_id}")
    Observable<List<Cosmetic>> ranking_cosmetic_get(@Path("user_id") String user_id);

    @GET("cosmetics/recommend2/{user_id}")
    Observable<List<Cosmetic>> recommend_cosmetic_get(@Path("user_id") String user_id);

    @GET("video/search2/{user_id}/{query}")
    Observable<List<Video_Youtuber>> search_video_get(@Path("user_id") String user_id,@Path("query") String query);

    @GET("video/recommend/cosmetic/{cosmetic}/{user_id}")
    Observable<List<Video_Youtuber>> relative_video_get(@Path("user_id") String user_id,@Path("cosmetic") String cosmetic);

    @GET("video/recommend/{user_id}")
    Observable<List<Video_Youtuber>> recommend_video_get(@Path("user_id") String user_id);


    @Multipart
    @POST("users/{user_id}/image/upload")
    Observable<GlobalResponse> fileUpload_Camera(@Path("user_id") String user_id,
                                          @Part("post_image\"; filename=\"android_post_image_file") RequestBody file);

    @GET("users/camera/image/{user_id}")
    Observable<List<String>> camera_image(@Path("user_id") String user_id);


    @GET("users/get_creator_camera/{user_id}")
    Observable<List<Youtuber>> get_creator_camera(@Path("user_id") String user_id);



}


