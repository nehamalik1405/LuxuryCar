package com.a.luxurycar.code_files.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.a.luxurycar.code_files.repository.*
import com.a.luxurycar.code_files.view_model.*
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
            modelClass.isAssignableFrom(TransportExportViewModel::class.java) -> TransportExportViewModel(repository = repository as TransportExportRepository) as T
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(repository = repository as AuthRepository) as T
            modelClass.isAssignableFrom(UpdateDetailViewModel::class.java) -> UpdateDetailViewModel(repository = repository as UpdateDetailRepository) as T
            modelClass.isAssignableFrom(ChangePasswordViewModel::class.java) -> ChangePasswordViewModel(repository = repository as ChangePasswordRepository) as T


            else -> {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }


}