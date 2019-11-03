package com.example.mvpdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.text);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                // 设置拦截器，添加统一的请求头
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        // 以拦截到的请求为基础创建一个新的请求对象，然后插入Header
                        Request request = chain.request().newBuilder()
                                .addHeader("Content-Type","application/json; charset=UTF-8")
                                .addHeader("imei","")
                                .addHeader("version","3.4.0")
                                .addHeader("client","1")
                                .addHeader("token","")
                                .build();
                        // 开始请求
                        return chain.proceed(request);
                    }
                })
                .build();



        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://haoyijieb.hzed.net")
                .build();

        BookService bookService = retrofit.create(BookService.class);
        Call<ResponseBody> call = bookService.getBook();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    String s = response.body().string();
                    Log.e("www",s);
                    textView.setText(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("www",t.toString());
            }
        });
    }

}
