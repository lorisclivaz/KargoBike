package com.example.kargobikeproject.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.kargobikeproject.Model.Entity.Product;
import com.example.kargobikeproject.Model.Repository.ProductRepository;
import com.example.kargobikeproject.Utils.OnAsyncEventListener;

import java.util.List;

public class ProductListViewModel extends AndroidViewModel {

    private ProductRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<Product>> observableBeverages;

    public ProductListViewModel(@NonNull Application application, ProductRepository beverageRepository) {
        super(application);

        repository = beverageRepository;

        observableBeverages = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableBeverages.setValue(null);

        LiveData<List<Product>> beverages = repository.getAllProducts();

        // observe the changes of the entities from the database and forward them
        observableBeverages.addSource(beverages, observableBeverages::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final ProductRepository beverageRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            beverageRepository = ProductRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ProductListViewModel(application, beverageRepository);
        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<List<Product>> getBeverages() {
        return observableBeverages;
    }

    public void deleteBeverage(Product beverage) {
        repository.delete(beverage, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {}

            @Override
            public void onFailure(Exception e) {}
        });
    }


}
