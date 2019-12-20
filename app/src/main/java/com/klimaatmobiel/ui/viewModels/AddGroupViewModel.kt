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
import java.net.ConnectException

class AddGroupViewModel(group: Group, private val repository: KlimaatmobielRepository) : ViewModel() {

    private var _group = MutableLiveData<Group>()
    val group: LiveData<Group> get() = _group

    private var _navigateToWebshop = MutableLiveData<Group>()
    val navigateToWebshop: LiveData<Group> get() = _navigateToWebshop

    private val _status = MutableLiveData<KlimaatMobielApiStatus>()
    val status: LiveData<KlimaatMobielApiStatus> get() = _status

    val groupName = MutableLiveData<String>()

    var customErrorMessage = ""

    init {
        _group.value = group
        groupName.value = group.groupName
    }

    fun onClickedAddPupil(pupilFirstName: String, pupilName: String) {
        try {
            if(pupilFirstName == "" || pupilName == "") {
                throw NullPointerException()
            }

            _group.value!!.addPupil(pupilFirstName, pupilName)
        }catch(e: java.lang.NullPointerException)
        {
            customErrorMessage = "Naam moet ingevuld zijn"
            _status.value = KlimaatMobielApiStatus.ERROR
        }


    }

    fun onclickedNext() {
        viewModelScope.launch {
            val changePupils = repository.changePupils(_group.value!!)
            try {
                _status.value = KlimaatMobielApiStatus.LOADING
                changePupils.await()


                _navigateToWebshop.value = group.value

                _status.value = KlimaatMobielApiStatus.DONE

            } catch (e: HttpException) {
                if (e.code() == 404)
                    customErrorMessage = "Kon leerling niet toevoegen"
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