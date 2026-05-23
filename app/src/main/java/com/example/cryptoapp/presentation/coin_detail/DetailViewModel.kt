package com.example.cryptoapp.presentation.coin_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.domain.model.CoinPricePoint
import com.example.cryptoapp.domain.use_case.GetCoinHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DetailState(
    val history: List<CoinPricePoint> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedRange: String = "1D"
)

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getCoinHistoryUseCase: GetCoinHistoryUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state.asStateFlow()

    private val coinId: String? = savedStateHandle["coinId"]

    init {
        coinId?.let {
            loadHistory(it, _state.value.selectedRange)
        }
    }

    fun onRangeSelected(range: String) {
        _state.update { it.copy(selectedRange = range) }
        coinId?.let {
            loadHistory(it, range)
        }
    }

    private fun loadHistory(coinId: String, range: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val history = getCoinHistoryUseCase(coinId, range)
                _state.update { it.copy(history = history, isLoading = false) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.localizedMessage ?: "Failed to load history"
                    )
                }
            }
        }
    }
}
