package helpers;

import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

import java.util.Random;

public interface Helper {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();

    String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiYm9uam92aWUyQG1haWwuY29tIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE2OTEzNDExMzAsImlhdCI6MTY5MDc0MTEzMH0.O-1lLBBvk95euXHhhdmkNLs_pdUjaNJhNInEbujN4-A";

    String BASE_URI = "https://contactapp-telran-backend.herokuapp.com";
    String PATH = "v1";

    String authHeader = "Authorization";

    int i = new Random().nextInt(1000) + 1000;

}
