package com.a.luxurycar.code_files.view_model

import com.a.luxurycar.code_files.base.BaseViewModel
import com.a.luxurycar.code_files.repository.ContactRepository

class ContactViewModel(val repository:ContactRepository):BaseViewModel(repository) {
}