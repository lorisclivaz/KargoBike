package com.group3.kargobikeproject.ViewModel;

import android.app.Application;

import com.group3.kargobikeproject.Model.Entity.User;
import com.group3.kargobikeproject.Model.Repository.UserRepository;
import com.group3.kargobikeproject.Utils.OnAsyncEventListener;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class UserViewModel extends AndroidViewModel {
    private Application application;
    private UserRepository repository;
    private final MediatorLiveData<User> observableUser;

    public UserViewModel(@NonNull Application application,
                         final String userId, UserRepository repository) {
        super(application);

        this.application = application;
        this.repository = repository;

        observableUser = new MediatorLiveData<>();
        observableUser.setValue(null);

        LiveData<User> user = repository.getUser(userId);

        observableUser.addSource(user, observableUser::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final String userID;
        private final UserRepository repository;

        public Factory(@NonNull Application application, String userID) {
            this.application = application;
            this.userID = userID;
            repository = UserRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new UserViewModel(application, userID, repository);
        }
    }

    public LiveData<User> getUser() {
        return observableUser;
    }

    public void createUser(User user, OnAsyncEventListener callback) {
        repository.insert(user, callback);
    }

    public void updateUser(User user, OnAsyncEventListener callback) {
        repository.update(user, callback);
    }

    public void deleteUser(User user, OnAsyncEventListener callback) {
        repository.delete(user, callback);
    }
}
