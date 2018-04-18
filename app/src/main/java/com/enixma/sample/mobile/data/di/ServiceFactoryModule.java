package com.enixma.sample.mobile.data.di;

import android.content.Context;

import com.enixma.sample.mobile.R;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.RealmObject;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */
@Module
public class ServiceFactoryModule {

    private Context context;

    public ServiceFactoryModule(Context context){
        this.context = context;
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })

                .create();
    }

    @Provides
    @Singleton
    public Interceptor provideInterceptor() {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest = null;
                newRequest = chain.request().newBuilder().build();
                return chain.proceed(newRequest);
            }
        };
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(Interceptor interceptor) {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(context.getResources().getInteger(R.integer.CONNECTION_TIMEOUT_IN_SECOND), TimeUnit.SECONDS);
        builder.readTimeout(context.getResources().getInteger(R.integer.READ_TIMEOUT_IN_SECOND), TimeUnit.SECONDS);
        builder.writeTimeout(context.getResources().getInteger(R.integer.WRITE_TIMEOUT_IN_SECOND), TimeUnit.SECONDS);
        builder.addInterceptor(interceptor);

        if (Boolean.valueOf(context.getString(R.string.LOG_REQUEST))) {
            HttpLoggingInterceptor mInterceptor = new HttpLoggingInterceptor();
            mInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            mInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(mInterceptor);
        }

        return builder.build();
    }

    @Provides
    @Singleton
    public Retrofit provideRestAdapter(OkHttpClient okHttpClient, Gson gson) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(okHttpClient)
                .baseUrl(context.getString(R.string.BASE_URL))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson));
        return builder.build();
    }

}
