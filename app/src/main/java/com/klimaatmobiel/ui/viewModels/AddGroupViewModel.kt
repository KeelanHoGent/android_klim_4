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
import kotlinx.coroutines.launch

class AddGroupViewModel(group: Group, private val repository: KlimaatmobielRepository) : ViewModel() {

    private var _group = MutableLiveData<Group>()
    val group: LiveData<Group> get() = _group

    val groupName = MutableLiveData<String>()

    init {
        _group.value = group
        groupName.value = group.groupName
    }

    fun onClickedAddPupil(pupilName: String) {
        viewModelScope.launch {
            _group.value!!.addPupil(pupilName)
        }
    }
}