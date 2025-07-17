package com.jar.app.ui.goldpricetracker

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jar.app.model.GoldPriceResponse
import com.jar.app.repository.JarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class GoldPriceUiState{
    object Loading : GoldPriceUiState()
    object Empty : GoldPriceUiState()
    data class GoldPriceData(val price : GoldPriceResponse) : GoldPriceUiState()
}


@HiltViewModel
class GoldPriceViewModel @Inject constructor(
    private val repository: JarRepository
) : ViewModel(){

    private val _goldPrice = mutableStateOf<GoldPriceUiState?>(GoldPriceUiState.Empty)
    val goldPrice : State<GoldPriceUiState?> = _goldPrice

    init {
        fetchGoldPrice()
    }

    fun fetchGoldPrice(){
        _goldPrice.value = GoldPriceUiState.Loading

        viewModelScope.launch {
            try {
                val response = repository.getGoldPrice()
                _goldPrice.value = GoldPriceUiState.GoldPriceData(response)
            } catch (e :Exception){
                e.printStackTrace()
            }
        }

    }
}