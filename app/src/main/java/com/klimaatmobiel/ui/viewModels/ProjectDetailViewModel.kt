package com.klimaatmobiel.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klimaatmobiel.domain.KlimaatmobielRepository
import com.klimaatmobiel.domain.Project
import kotlinx.coroutines.launch
import timber.log.Timber

class ProjectDetailViewModel(private val repository: KlimaatmobielRepository, private val projectId: Long) : ViewModel() {

    private var _project = MutableLiveData<Project>()

    val project: LiveData<Project> get() = _project

    init {
        loadProject()
    }

    private fun loadProject() {
        viewModelScope.launch {
            _project.value = repository.getProject(projectId)
            Timber.i("Ok lets start")
            Timber.i("de id is: ")
            Timber.i(_project.value!!.applicationDomainId.toString())
            Timber.i("de naam is: ")
            Timber.i(_project.value!!.applicationDomain.applicationDomainName)
        }
    }
}