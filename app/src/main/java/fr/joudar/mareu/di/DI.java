package fr.joudar.mareu.di;

import android.os.Build;

import androidx.annotation.RequiresApi;

import fr.joudar.mareu.service.ApiService;
import fr.joudar.mareu.service.DummyApiService;

/**
 * Dependency injector to get instance of services
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class DI {

    private static ApiService apiService = new DummyApiService();

    /**
     * Get an instance on @{@link ApiService}
     *
     * @return ApiService
     */
    public static ApiService getApiService() {
        return apiService;
    }

    /**
     * Get always a new instance on @{@link ApiService}. Useful for tests, so we ensure the context is clean.
     *
     * @return DummyApiService
     */
    public static ApiService getNewInstanceApiService() {
        return new DummyApiService();
    }
}
