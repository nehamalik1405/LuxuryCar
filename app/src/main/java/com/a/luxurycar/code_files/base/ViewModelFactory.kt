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
            modelClass.isAssignableFrom(BuyerRegistrationViewModel::class.java) -> BuyerRegistrationViewModel(repository = repository as BuyerRegistrationRepository) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository = repository as HomeRepository) as T
            modelClass.isAssignableFrom(CarListViewModel::class.java) -> CarListViewModel(repository = repository as CarListRepository) as T
            modelClass.isAssignableFrom(TransportExportViewModel::class.java) -> TransportExportViewModel(repository = repository as TransportExportRepository) as T
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(repository = repository as AuthRepository) as T
            modelClass.isAssignableFrom(UpdateDetailViewModel::class.java) -> UpdateDetailViewModel(repository = repository as UpdateDetailRepository) as T
            modelClass.isAssignableFrom(ChangePasswordViewModel::class.java) -> ChangePasswordViewModel(repository = repository as ChangePasswordRepository) as T
            modelClass.isAssignableFrom(ForgotPasswordViewModel::class.java) -> ForgotPasswordViewModel(repository = repository as ForgotPasswordRepository) as T
            modelClass.isAssignableFrom(AboutUsViewModel::class.java) -> AboutUsViewModel(repository = repository as AboutUsRepository) as T
            modelClass.isAssignableFrom(AdvertiseWithUsViewModel::class.java) -> AdvertiseWithUsViewModel(repository = repository as AdvertiseWithUsRepository) as T
            modelClass.isAssignableFrom(BookAnAppointmentViewmodel::class.java) -> BookAnAppointmentViewmodel(repository = repository as BookAnAppointmentRepository) as T
            modelClass.isAssignableFrom(ContactViewModel::class.java) -> ContactViewModel(repository = repository as ContactRepository) as T
            modelClass.isAssignableFrom(FindGaragesViewModel::class.java) -> FindGaragesViewModel(repository = repository as FindGaragesRepository) as T
            modelClass.isAssignableFrom(FollowUsViewModel::class.java) -> FollowUsViewModel(repository = repository as FollowUsRepository) as T
            modelClass.isAssignableFrom(InspectingViewModel::class.java) -> InspectingViewModel(repository = repository as InspectingRepository) as T
            modelClass.isAssignableFrom(LanguageViewModel::class.java) -> LanguageViewModel(repository = repository as LanguageRepository) as T
            modelClass.isAssignableFrom(NewEnquiryFormViewModel::class.java) -> NewEnquiryFormViewModel(repository = repository as NewEnquiryFormRepository) as T
            modelClass.isAssignableFrom(ProductDetailViewModel::class.java) -> ProductDetailViewModel(repository = repository as ProductDetailRepository) as T
            modelClass.isAssignableFrom(SaurceMyCarViewModel::class.java) -> SaurceMyCarViewModel(repository = repository as SaurceMyCarRepository) as T
            modelClass.isAssignableFrom(SourcingViewModel::class.java) -> SourcingViewModel(repository = repository as SourcingRepository) as T
            modelClass.isAssignableFrom(StorageViewModel::class.java) -> StorageViewModel(repository = repository as StorageRepository) as T
            modelClass.isAssignableFrom(TermAndConditionViewModel::class.java) -> TermAndConditionViewModel(repository = repository as TermAndConditionRepository) as T
            modelClass.isAssignableFrom(SellerHomeViewModel::class.java) -> SellerHomeViewModel(repository = repository as SellerHomeRepository) as T
            modelClass.isAssignableFrom(AddCarViewModel::class.java) -> AddCarViewModel(repository = repository as AddCarRepository) as T
            modelClass.isAssignableFrom(SellYourCarViewModel::class.java) -> SellYourCarViewModel(repository = repository as SellYourCarRepository) as T
            modelClass.isAssignableFrom(SellerViewModel::class.java) -> SellerViewModel(repository = repository as SellerRepository) as T
            modelClass.isAssignableFrom(SellerProfileViewModel::class.java) -> SellerProfileViewModel(repository = repository as SellerProfileRepository) as T
            modelClass.isAssignableFrom(PaymentHistoryViewModel::class.java) -> PaymentHistoryViewModel(repository = repository as PaymentHistoryRepository) as T
            modelClass.isAssignableFrom(BuyerMyProfileViewModel::class.java) -> BuyerMyProfileViewModel(repository = repository as BuyerMyProfileRepository) as T
            modelClass.isAssignableFrom(AddCarStepOneViewModel::class.java) -> AddCarStepOneViewModel(repository = repository as AddCarStepOneRepository) as T
            modelClass.isAssignableFrom(AddCarStepThreeViewModel::class.java) -> AddCarStepThreeViewModel(repository = repository as AddCarStepThreeRepository) as T


            else -> {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }


}