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

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<BikeService>> observableBikeService;

    public BikeServiceListViewModel(@NonNull Application application, BikeServiceRepository repository) {

        super(application);

        this.repository = repository;

        observableBikeService = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableBikeService.setValue(null);

        LiveData<List<BikeService>> bikeService = repository.getAllBikeService();

        // observe the changes of the entities from the database and forward them
        observableBikeService.addSource(bikeService, observableBikeService::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final BikeServiceRepository repository;

        public Factory(@NonNull Application application) {
            this.application = application;
            repository = BikeServiceRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new BikeServiceListViewModel(application, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<List<BikeService>> getBikeServices() {
        return observableBikeService;
    }
    //one Service
    public LiveData<BikeService> getBikeService(String ID) {
        return repository.getBikeService(ID);
    }
}
