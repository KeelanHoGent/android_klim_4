package com.klimaatmobiel.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.klimaatmobiel.domain.Group
import com.klimaatmobiel.domain.KlimaatmobielRepository

class AddGroupViewModel(group: Group, private val repository: KlimaatmobielRepository) : ViewModel() {

    private var _group = MutableLiveData<Group>()
    val group: LiveData<Group> get() = _group

    val groupName = MutableLiveData<String>()

    init {
        _group.value = group
        groupName.value = group.groupName
    }
}