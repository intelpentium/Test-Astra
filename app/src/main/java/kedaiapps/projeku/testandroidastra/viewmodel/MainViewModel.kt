package kedaiapps.projeku.testandroidastra.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kedaiapps.projeku.testandroidastra.BuildConfig
import kedaiapps.projeku.testandroidastra.common.ActionLiveData
import kedaiapps.projeku.testandroidastra.common.UiState
import kedaiapps.projeku.testandroidastra.ext.errorMesssage
import kedaiapps.projeku.testandroidastra.services.entity.ResponseHome
import kedaiapps.projeku.testandroidastra.services.entity.ResponseHomeDetail
import kedaiapps.projeku.testandroidastra.services.rest.MainRest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRest: MainRest
) : ViewModel() {

    val loadState = ActionLiveData<UiState>()

    val responseHome = ActionLiveData<List<ResponseHome>>()
    val responseHomeDetail = ActionLiveData<ResponseHomeDetail>()

    fun home() {
        viewModelScope.launch {
            loadState.sendAction(UiState.Loading)
            try {
                val response = mainRest.home()
                responseHome.value = response.results!!
                loadState.sendAction(UiState.Success)
            } catch (e: Exception) {
                loadState.sendAction(UiState.Error(e.errorMesssage))
            }
        }
    }

    fun homeDetail(name: String) {
        viewModelScope.launch {
            loadState.sendAction(UiState.Loading)
            try {
                val response = mainRest.homeDetail(name)
                responseHomeDetail.value = response!!
                loadState.sendAction(UiState.Success)
            } catch (e: Exception) {
                loadState.sendAction(UiState.Error(e.errorMesssage))
            }
        }
    }
}