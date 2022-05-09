package com.a.luxurycar.code_files.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.a.luxurycar.code_files.repository.CarListRepository
import com.a.luxurycar.code_files.repository.HomeRepository
import com.a.luxurycar.code_files.repository.LoginRepository
import com.a.luxurycar.code_files.repository.RegistrationRepository
import com.a.luxurycar.code_files.view_model.CarListViewModel
import com.a.luxurycar.code_files.view_model.HomeViewModel
import com.a.luxurycar.code_files.view_model.LoginViewModel
import com.a.luxurycar.code_files.view_model.RegistrationViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(
    private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(repository = repository as LoginRepository) as T
            modelClass.isAssignableFrom(RegistrationViewModel::class.java) -> RegistrationViewModel(repository = repository as RegistrationRepository) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository = repository as HomeRepository) as T
            modelClass.isAssignableFrom(CarListViewModel::class.java) -> CarListViewModel(repository = repository as CarListRepository) as T


            else -> {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }


}