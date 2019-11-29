package com.example.kargobikeproject.ViewModel;

import android.app.Application;

import com.example.kargobikeproject.Model.Entity.User;
import com.example.kargobikeproject.Model.Repository.UserRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class UserListViewModel extends AndroidViewModel {
    private UserRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<User>> observableUser;

    public UserListViewModel(@NonNull Application application, UserRepository repository) {

        super(application);

        this.repository = repository;

        observableUser = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableUser.setValue(null);

        LiveData<List<User>> users = repository.getAllUser();

        // observe the changes of the entities from the database and forward them
        observableUser.addSource(users, observableUser::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final UserRepository repository;

        public Factory(@NonNull Application application) {
            this.application = application;
            repository = UserRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new UserListViewModel(application, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<List<User>> getUsers() {
        return observableUser;
    }
    //einen Artikel
    public LiveData<User> getUser(String ID) {
        return repository.getUser(ID);
    }
}
