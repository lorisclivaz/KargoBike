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

public class ProductViewModel extends AndroidViewModel {

    private ProductRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<Product> observableProduct;

    public ProductViewModel(@NonNull Application application,
                             final String name, ProductRepository beverageRepository) {
        super(application);

        repository = beverageRepository;

        observableProduct = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableProduct.setValue(null);

        if (name != null) {
            LiveData<Product> product = repository.getProduct(name);

            // observe the changes of the client entity from the database and forward them
            observableProduct.addSource(product, observableProduct::setValue);
        }
    }

    /**
     * A creator is used to inject the product id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String name;

        private final ProductRepository repository;

        public Factory(@NonNull Application application, String id) {
            this.application = application;
            name = id;
            repository = ProductRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ProductViewModel(application, name, repository);
        }
    }

    /**
     * Expose the LiveData Porduct query so the UI can observe it.
     */
    public LiveData<Product> getProduct() {
        return observableProduct;
    }

    public void createProduct(Product beverage, OnAsyncEventListener callback) {
        ProductRepository.getInstance().insert(beverage, callback);
    }

    public void updateProduct(Product beverage, OnAsyncEventListener callback) {
        repository.update(beverage, callback);
    }

    public void deleteClient(Product beverage, OnAsyncEventListener callback) {
        repository.delete(beverage, callback);
    }
}