package com.klimaatmobiel.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klimaatmobiel.PusherApplication
import com.klimaatmobiel.domain.Group
import com.klimaatmobiel.domain.KlimaatmobielRepository
import com.klimaatmobiel.domain.enums.KlimaatMobielApiStatus
import kotlinx.coroutines.*
import retrofit2.HttpException
import timber.log.Timber

class MainMenuViewModel(private val repository: KlimaatmobielRepository) : ViewModel() {

    val groupCode = MutableLiveData<String>()

    private val _navigateToWebshop = MutableLiveData<Group>()
    val navigateToWebshop: LiveData<Group> get() = _navigateToWebshop

    private val _status = MutableLiveData<KlimaatMobielApiStatus>()
    val status: LiveData<KlimaatMobielApiStatus> get() = _status


    init {
        // For testing purposes
        groupCode.value = "212345"
    }


    fun onClickNavigateToWebshop(){

        // check for empty groupCode
        viewModelScope.launch {

            //var getGroupDeferred = repository.getFullGroup("212345")
            var getGroupDeferred = repository.getFullGroup(groupCode.value ?: "")
            try {
                _status.value = KlimaatMobielApiStatus.LOADING
                val group = getGroupDeferred.await()

                // Filter list by categoryname
                group.project.products.toMutableList().sortBy { it.category!!.categoryName }

                _navigateToWebshop.value = group

                repository.refreshProject(group.project)

                repository.refreshProducts(group.project.products)

                PusherApplication.huidigProjectId = group.projectId
                _status.value = KlimaatMobielApiStatus.DONE

            }catch (e: HttpException) {
                Timber.i(e.message())
                _status.value = KlimaatMobielApiStatus.ERROR
            }
            catch (e: Exception) {
                Timber.i(e.message)
                _status.value = KlimaatMobielApiStatus.ERROR
            }
        }
    }

    fun onWebshopNavigated() {
        _navigateToWebshop.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}