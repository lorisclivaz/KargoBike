package com.example.kargobikeproject.ViewModel;

import android.app.Application;

import com.example.kargobikeproject.Model.Entity.BikeService;
import com.example.kargobikeproject.Model.Entity.User;
import com.example.kargobikeproject.Model.Repository.BikeServiceRepository;
import com.example.kargobikeproject.Model.Repository.UserRepository;
import com.example.kargobikeproject.Utils.OnAsyncEventListener;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class BikeServiceViewModel extends AndroidViewModel {
    private Application application;
    private BikeServiceRepository repository;
    private final MediatorLiveData<BikeService> observableService;

    public BikeServiceViewModel(@NonNull Application application,
                                final String idService, BikeServiceRepository repository) {
        super(application);

        this.application = application;
        this.repository = repository;

        observableService = new MediatorLiveData<>();
        observableService.setValue(null);

        LiveData<BikeService> bikeService = repository.getBikeService(idService);

        observableService.addSource(bikeService, observableService::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final String serviceID;
        private final BikeServiceRepository repository;

        public Factory(@NonNull Application application, String serviceID) {
            this.application = application;
            this.serviceID = serviceID;
            repository = BikeServiceRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new BikeServiceViewModel(application, serviceID, repository);
        }
    }

    public LiveData<BikeService> getBikeService() {
        return observableService;
    }

    public void createBikeService(BikeService bikeService, OnAsyncEventListener callback) {
        repository.insert(bikeService, callback);
    }

    public void updateBikeService(BikeService bikeService, OnAsyncEventListener callback) {
        repository.update(bikeService, callback);
    }

    public void deleteBikeService(BikeService bikeService, OnAsyncEventListener callback) {
        repository.delete(bikeService, callback);
    }
}
