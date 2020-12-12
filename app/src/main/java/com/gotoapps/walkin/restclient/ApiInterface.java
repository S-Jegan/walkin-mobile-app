package com.gotoapps.walkin.restclient;

import com.gotoapps.walkin.model.Category;
import com.gotoapps.walkin.model.Industry;
import com.gotoapps.walkin.model.InterviewJSON;
import com.gotoapps.walkin.model.Interviews;
import com.gotoapps.walkin.model.Keywords;
import com.gotoapps.walkin.model.Location;
import com.gotoapps.walkin.model.Properties;
import com.gotoapps.walkin.model.Resume;
import com.gotoapps.walkin.response.InterviewListRestResponse;
import com.gotoapps.walkin.response.ResumeResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by C2CSLS on 9/17/2018.
 */

public interface ApiInterface {

    /**
     * Retrieve the list of application properties
     * @return List<Properties>
     */
    @GET("properties")
    Call<List<Properties>> getAppProperties();

    /**
     * Retrieve the list of Verified Interviews
     * @return Interviews
     */
    @POST("interviews/verified")
    Call<Interviews> fetchVerifiedInterviews(@Body PostData postData);

    /**
     * Retrieve the list of Visa Sponsored Interviews
     * @return Interviews
     */
    @POST("interviews/visa-sponsored")
    Call<Interviews> fetchVisaSponsoredInterviews(@Body PostData postData);

    /**
     * Retrieve the list of all interviews
     * @return Interviews
     */
    @POST("interviews")
    Call<Interviews> fetchAllInterviews(@Body PostData postData);

    /**
     * Retrieve the list of all interviews
     * @return Interviews
     */
    @POST("interviews/freshers")
    Call<Interviews> fetchFresherInterviews(@Body PostData postData);

    /**
     * Retrieve the list of IndustrySpecificInterviews
     * @return Interviews
     */
    @POST("interviews/industry/{id}")
    Call<Interviews> fetchIndustrySpecificInterviews(@Path("id") int id,@Body PostData postData);

    /**
     * Retrieve the list of CategorySpecificInterviews
     * @return Interviews
     */
    @POST("interviews/category/{id}")
    Call<Interviews> fetchCategorySpecificInterviews(@Path("id") int id,@Body PostData postData);

    /**
     * Retrieve the list of Newly Added Interviews
     * @return Interviews
     */
    @POST("interviews/new")
    Call<Interviews> fetchNewlyAddedInterviews(@Body PostData postData);

    /**
     * Retrieve the list of Location Specific Jobs
     * @return Interviews
     */
    @POST("interviews/location/{name}")
    Call<Interviews> fetchLocationSpecificInterviews(@Path("name") String name,@Body PostData postData);

    /**
     * Retrieve the list of Interviews By Multiple Ids
     * @return Interviews
     */
    @POST("interviews/ids")
    Call<Interviews> fetchInterviewsByMultipleIds(@Body PostData postData);

    /**
     * Retrieve the list of Interviews By Multiple Ids
     * @return Interviews
     */
    @POST("interviews/many-category/ids")
    Call<Interviews> fetchInterviewsByMultipleCategoryIds(@Body PostData postData);

    /**
     * Retrieve the interview details by Interview Id
     * @param id
     * @return
     */
    @GET("interview/{id}")
    Call<List<InterviewJSON>> getInterviewByID(@Path("id") int id);

    /**
     * Retrieve the interview details by Interview Id
     * @param id
     * @return
     */
    @GET("interview/add/sharecount/{id}")
    Call<List<InterviewJSON>> addShareCount(@Path("id") int id);


    /**
     * Retrieve the list of interview categories
     * @param
     * @return
     */
    @GET("categories")
    Call<List<Category>> getCategoriesList();

    /**
     * Get the default interviews
     * @param postData
     * @return
     */
    @POST("interviews")
    Call<Interviews> getInterviews(@Body PostData postData);

    /**
     * Get the default interviews
     * @param postData
     * @return
     */
    @POST("interviews/today")
    Call<Interviews> getTodayInterviews(@Body PostData postData);

    /**
     * Get the default interviews
     * @param postData
     * @return
     */
    @POST("interviews/tomorrow")
    Call<Interviews> getTomorrowInterviews(@Body PostData postData);

    /**
     * Get the keywords list
     * @return
     */
    @GET("keywords")
    Call<List<Keywords>> getSearchKeywords();

    /**
     * Search interview based on Search Form
     * @param postData
     * @return
     */
    @POST("interviews/search")
    Call<Interviews> fetchInterviewsByKewords(@Body PostSearch postData);

    @POST("interviews/{category}")
    Call<Interviews> getInterviewByCategory(@Body PostData postData,@Path("category") String category);

    @POST("interview/viewed")
    Call<Interviews> getInterviewByViewedIDs(@Body PostData postData);

    @GET("locations")
    Call<List<Location>> getLocationBasedInterviews();

    @GET("resumes")
    Call<ResumeResponse> getResumes();

    @GET("resume/add-download/{id}")
    Call<List<Resume>> addResumeDownloadCount(@Path("id") int id);

    @GET("/data.json")
    Call<InterviewListRestResponse> getAllInterviews();

}

