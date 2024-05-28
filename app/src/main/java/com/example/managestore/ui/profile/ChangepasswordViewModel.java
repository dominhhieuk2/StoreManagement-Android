package com.example.managestore.ui.profile;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChangepasswordViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ChangepasswordViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is change password fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
