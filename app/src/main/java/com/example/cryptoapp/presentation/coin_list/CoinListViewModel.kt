package com.example.cryptoapp.presentation.coin_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.domain.model.Coin
import com.example.cryptoapp.domain.use_case.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CoinListState(
    val coins: List<Coin> = emptyList(), // Список для отображения (отфильтрованный)
    val allCoins: List<Coin> = emptyList(), // Полный список из API
    val isLoading: Boolean = false,
    val error: String? = null,
    val totalBalance: Double = 0.0,
    val totalMarketCap: Double = 0.0,
    val totalChangePercent: Double = 0.0,
    val totalChangeAmount: Double = 0.0,
    val bestCoin: Coin? = null,
    val bestToken: Coin? = null
)

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase
): ViewModel() {

    private val _rawState = MutableStateFlow(CoinListState())
    
    private val _selectedTab = MutableStateFlow("Coins")
    val selectedTab: StateFlow<String> = _selectedTab.asStateFlow()

    // Поток для поискового запроса
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Основное состояние, объединяющее данные, вкладку и поиск
    val state: StateFlow<CoinListState> = combine(
        _rawState, 
        _selectedTab, 
        _searchQuery
    ) { state, tab, query ->
        val filteredByType = if (tab == "Coins") {
            state.allCoins.filter { it.type == "coin" }
        } else {
            state.allCoins.filter { it.type == "token" }
        }

        val filteredBySearch = if (query.isBlank()) {
            filteredByType
        } else {
            filteredByType.filter { 
                it.name.contains(query, ignoreCase = true) || 
                it.symbol.contains(query, ignoreCase = true) 
            }
        }
        
        state.copy(coins = filteredBySearch)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CoinListState())

    init {
        loadCoins()
    }

    fun onTabSelected(tab: String) {
        _selectedTab.value = tab
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun loadCoins() {
        viewModelScope.launch {
            _rawState.update { it.copy(isLoading = true, error = null) }
            try {
                val allCoins = getCoinsUseCase()
                
                val balance = allCoins.sumOf { it.priceUsd }
                val marketCap = allCoins.sumOf { it.marketCapUsd }
                val avgChange = if (allCoins.isNotEmpty()) allCoins.map { it.changePercent24Hr }.average() else 0.0
                val changeAmt = balance * (avgChange / 100)

                val bestCoin = allCoins.filter { it.type == "coin" }.maxByOrNull { it.changePercent24Hr }
                val bestToken = allCoins.filter { it.type == "token" }.maxByOrNull { it.changePercent24Hr }

                _rawState.update { 
                    it.copy(
                        allCoins = allCoins,
                        isLoading = false,
                        totalBalance = balance,
                        totalMarketCap = marketCap,
                        totalChangePercent = avgChange,
                        totalChangeAmount = changeAmt,
                        bestCoin = bestCoin,
                        bestToken = bestToken
                    ) 
                }
            } catch (e: Exception) {
                Log.e("CoinListViewModel", "Error loading coins", e)
                _rawState.update { 
                    it.copy(
                        isLoading = false, 
                        error = "Ошибка сети: ${e.localizedMessage ?: "Проверьте интернет"}" 
                    ) 
                }
            }
        }
    }
}
