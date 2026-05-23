package com.example.cryptoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cryptoapp.presentation.coin_detail.DetailScreen
import com.example.cryptoapp.presentation.coin_list.CoinListScreen
import com.example.cryptoapp.presentation.coin_list.CoinListViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cryptoapp.presentation.history.HistoryScreen
import com.example.cryptoapp.ui.theme.CryptoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoAppTheme {
                val navController = rememberNavController()
                val coinListViewModel: CoinListViewModel = hiltViewModel()
                val coinListState by coinListViewModel.state.collectAsState()

                NavHost(
                    navController = navController,
                    startDestination = "coin_list"
                ) {
                    composable("coin_list") {
                        CoinListScreen(
                            viewModel = coinListViewModel,
                            onCoinClick = { coin ->
                                navController.navigate("coin_detail/${coin.id}")
                            },
                            onHistoryClick = {
                                navController.navigate("history")
                            }
                        )
                    }

                    composable("history") {
                        HistoryScreen(
                            onHomeClick = {
                                navController.navigate("coin_list") {
                                    popUpTo("coin_list") { inclusive = true }
                                }
                            }
                        )
                    }

                    composable(
                        route = "coin_detail/{coinId}",
                        arguments = listOf(navArgument("coinId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val coinId = backStackEntry.arguments?.getString("coinId")
                        // Ищем монету в полном списке для перехода
                        val coin = coinListState.allCoins.find { it.id == coinId }
                        
                        if (coin != null) {
                            DetailScreen(
                                coin = coin,
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
