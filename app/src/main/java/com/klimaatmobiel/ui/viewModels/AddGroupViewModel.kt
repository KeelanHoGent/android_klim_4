package com.klimaatmobiel.ui.viewModels

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projecten3android.R
import com.google.android.material.snackbar.Snackbar
import com.klimaatmobiel.domain.Group
import com.klimaatmobiel.domain.KlimaatmobielRepository
import com.klimaatmobiel.domain.enums.KlimaatMobielApiStatus
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber

class AddGroupViewModel(group: Group, private val repository: KlimaatmobielRepository) : ViewModel() {

    private var _group = MutableLiveData<Group>()
    val group: LiveData<Group> get() = _group

    private var _navigateToWebshop = MutableLiveData<Group>()
    val navigateToWebshop: LiveData<Group> get() = _navigateToWebshop

    private val _status = MutableLiveData<KlimaatMobielApiStatus>()
    val status: LiveData<KlimaatMobielApiStatus> get() = _status

    val groupName = MutableLiveData<String>()

    init {
        _group.value = group
        groupName.value = group.groupName
    }

    fun onClickedAddPupil(pupilFirstName: String, pupilName: String) {
        viewModelScope.launch {
            _group.value!!.addPupil(pupilFirstName, pupilName)
        }
    }

    fun onclickedNext() {
        _navigateToWebshop.value = group.value
    }
}