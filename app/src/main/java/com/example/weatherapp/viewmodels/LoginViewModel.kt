package com.example.weatherapp.viewmodels

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.models.Result
import com.example.weatherapp.repositories.LoginRepository
import com.example.weatherapp.utils.*
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: LoginRepository
) : ViewModel() {

    val loader = ObservableBoolean(false)
    val nameError by lazy { ObservableField<String>() }
    val usernameError by lazy { ObservableField<String>() }
    val pwdError by lazy { ObservableField<String>() }
    val isButtonEnabled by lazy { ObservableBoolean(true) }

    private var _loginLiveData: MutableLiveData<Result<String>?> = MutableLiveData()
    var loginLiveData: LiveData<Result<String>?> = _loginLiveData

    fun loginUser(username: String, password: String) {
        //validate credentials
        if (validateCreds(username, password)) {
            loader.set(true)
            isButtonEnabled.set(false)
            viewModelScope.launch {
                when (val result = repository.loginUser(username, password)) {
                    is Result.Success -> {
                        if (result.data != null) {
                            if (password == result.data.password) {
                                _loginLiveData.postValue(Result.Success(login_success))
                            } else {
                                _loginLiveData.postValue(Result.Error(invalid_creds))
                            }
                        } else {
                            _loginLiveData.postValue(Result.Error(no_user_found))
                        }
                    }
                    is Result.Error -> _loginLiveData.postValue(result)
                }
            }
            return
        }

    }

    fun signupUser(name: String, username: String, password: String) {
        if (validateCreds(name, username, password)) {
            loader.set(true)
            isButtonEnabled.set(false)

            viewModelScope.launch {
                when (val result = repository.insertUser(name, username, password)) {
                    is Result.Success -> {
                        _loginLiveData.postValue(Result.Success(registration_success))
                    }
                    is Result.Error -> {
                        _loginLiveData.postValue(
                            if (result.errorMsg.contains(unique_constraint_failed, false)) {
                                Result.Error(user_already_exist)
                            } else
                                result
                        )
                    }
                }
            }
            return
        }
        isButtonEnabled.set(true)
    }

    private fun validateCreds(name: String, username: String, password: String): Boolean {
        return if (name.isEmpty() && username.isEmpty() && password.isEmpty()) {
            nameError.set(please_enter_name)
            usernameError.set(please_enter_username)
            pwdError.set(please_enter_pwd)
            false
        } else if (name.isEmpty()) {
            nameError.set(please_enter_name)
            false
        } else if (username.isEmpty()) {
            usernameError.set(please_enter_username)
            false
        } else if (password.isEmpty()) {
            pwdError.set(please_enter_pwd)
            false
        } else true

    }

    private fun validateCreds(username: String, password: String): Boolean {
        return if (username.isEmpty() && password.isEmpty()) {
            usernameError.set(please_enter_username)
            pwdError.set(please_enter_pwd)
            false
        } else if (username.isEmpty()) {
            usernameError.set(please_enter_username)
            false
        } else if (password.isEmpty()) {
            pwdError.set(please_enter_pwd)
            false
        } else true
    }


    fun resetLoginLiveData() {
        _loginLiveData.postValue(null)
        nameError.set(null)
        usernameError.set(null)
        pwdError.set(null)
        isButtonEnabled.set(true)
    }

}