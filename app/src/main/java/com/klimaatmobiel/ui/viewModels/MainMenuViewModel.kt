package com.klimaatmobiel.ui.viewModels

import android.accounts.NetworkErrorException
import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi
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
import java.net.ConnectException

class MainMenuViewModel(private val repository: KlimaatmobielRepository) : ViewModel() {

    val groupCode = MutableLiveData<String>()

    private val _navigateToWebshop = MutableLiveData<Group>()
    val navigateToWebshop: LiveData<Group> get() = _navigateToWebshop

    private val _navigateToAddGroup = MutableLiveData<Group>()
    val navigateToAddGroup: LiveData<Group>
        get() = _navigateToAddGroup

    private val _status = MutableLiveData<KlimaatMobielApiStatus>()
    val status: LiveData<KlimaatMobielApiStatus> get() = _status

    var customErrorMessage = ""


    init {
        // For testing purposes
        groupCode.value = "212345"
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun onClickNavigateToAddGroup(){

        // check for empty groupCode
        if(groupCode.value.isNullOrEmpty()){
            customErrorMessage = "Vul de groepscode in"
            _status.value = KlimaatMobielApiStatus.ERROR
        }
        else {
            viewModelScope.launch {

                //var getGroupDeferred = repository.getFullGroup("212345")
                var getGroupDeferred = repository.getFullGroup(groupCode.value ?: "")
                try {
                    _status.value = KlimaatMobielApiStatus.LOADING
                    val group = getGroupDeferred.await()
                    PusherApplication.huidigProjectId = group.projectId
                    PusherApplication.group = group

                    // Filter list by categoryname
                    group.project.products.toMutableList().sortBy { it.category!!.categoryName }


                    _navigateToAddGroup.value = group

                    repository.refreshProject(group.project)

                    repository.refreshProducts(group.project.products)


                    _status.value = KlimaatMobielApiStatus.DONE

                } catch (e: HttpException) {
                    if (e.code() == 404)
                        customErrorMessage = "Groep niet gevonden!"
                    else
                        customErrorMessage = "Er ging iets fout!"
                    _status.value = KlimaatMobielApiStatus.ERROR
                } catch (e: ConnectException) {
                    customErrorMessage = "Er is geen internet! probeer later opnieuw"
                    _status.value = KlimaatMobielApiStatus.ERROR
                } catch (e: Exception) {
                    customErrorMessage = e.message!!
                    _status.value = KlimaatMobielApiStatus.ERROR
                }
            }
        }
    }

    fun onAddGroupNavigated() {
        _navigateToWebshop.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}