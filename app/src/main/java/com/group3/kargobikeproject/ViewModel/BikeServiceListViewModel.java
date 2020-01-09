package com.group3.kargobikeproject.ViewModel;

import android.app.Application;

import com.group3.kargobikeproject.Model.Entity.BikeService;
import com.group3.kargobikeproject.Model.Repository.BikeServiceRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class BikeServiceListViewModel extends AndroidViewModel {
    private BikeServiceRepository repository;
    private String time;
    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<BikeService>> observableBikeService;
    private final MediatorLiveData<List<BikeService>> observableBikeServiceDate;

    public BikeServiceListViewModel(@NonNull Application application, BikeServiceRepository repository, String time) {

        super(application);

        this.repository = repository;
        this.time = time;

        observableBikeService = new MediatorLiveData<>();
        observableBikeServiceDate = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableBikeService.setValue(null);
        observableBikeServiceDate.setValue(null);

        LiveData<List<BikeService>> bikeService = repository.getAllBikeService();
        LiveData<List<BikeService>> bikeServiceDate = repository.getBikeServiceDate(time);

        // observe the changes of the entities from the database and forward them
        observableBikeService.addSource(bikeService, observableBikeService::setValue);
        observableBikeServiceDate.addSource(bikeServiceDate, observableBikeServiceDate::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final String time;
        private final BikeServiceRepository repository;

        public Factory(@NonNull Application application, String time) {
            this.application = application;
            repository = BikeServiceRepository.getInstance();
            this.time = time;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new BikeServiceListViewModel(application, repository, time);
        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<List<BikeService>> getBikeServices() {
        return observableBikeService;
    }

    public LiveData<List<BikeService>> getBikeServicesDate() {
        return observableBikeServiceDate;
    }
    //one Service
    public LiveData<BikeService> getBikeService(String ID) {
        return repository.getBikeService(ID);
    }
}
