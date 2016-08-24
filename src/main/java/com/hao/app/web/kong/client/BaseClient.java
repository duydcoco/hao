package com.hao.app.web.kong.client;

import org.slf4j.Logger;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;

/**
 * Created by donghao on 16/8/20.
 */
public abstract class BaseClient {

    private Retrofit retrofit;

    public BaseClient(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    protected <T> T handleResponse(Call<T> call) {
        return handleResponse(call,null);
    }

    protected <T> T handleResponse(Call<T> call,T defaultValue) {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                getLogger().error(response.errorBody().string());
            }
        } catch (IOException e) {
            getLogger().error("",e);
        }
        return defaultValue;
    }

    protected <T> boolean noContentResponse(Call<T> call) {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return true;
            }
            getLogger().error(response.errorBody().string());
        } catch (IOException e) {
            getLogger().error("",e);
        }
        return false;
    }

    protected abstract Logger getLogger();
}
