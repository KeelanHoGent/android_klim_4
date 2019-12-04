package com.klimaatmobiel.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klimaatmobiel.domain.KlimaatmobielRepository
import com.klimaatmobiel.domain.Project
import kotlinx.coroutines.launch

class ProjectDetailViewModel(private val repository: KlimaatmobielRepository, private val projectId: Long) : ViewModel() {

    private var _project = MutableLiveData<Project>()

    val product: LiveData<Project> get() = _project

    init {
        loadProject()
    }

    private fun loadProject() {
        viewModelScope.launch {
            _project.value = repository.getProject(projectId)
        }
    }
}