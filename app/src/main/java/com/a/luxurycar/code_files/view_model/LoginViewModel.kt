package com.a.luxurycar.code_files.view_model

import androidx.lifecycle.ViewModel
import com.a.luxurycar.code_files.base.BaseViewModel
import com.a.luxurycar.code_files.repository.LoginRepository

class LoginViewModel(val repository: LoginRepository) : BaseViewModel(repository){

}