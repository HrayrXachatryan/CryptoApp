# 🪙 CryptoApp — Premium Crypto Portfolio Tracker

**A high-performance, visually stunning Android application for real-time cryptocurrency tracking and professional portfolio management.**

![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-blue.svg?style=for-the-badge&logo=kotlin)
![Android SDK](https://img.shields.io/badge/SDK-36-green.svg?style=for-the-badge&logo=android)
![Compose](https://img.shields.io/badge/Jetpack_Compose-2024.09.00-4285F4.svg?style=for-the-badge&logo=jetpackcompose)
![Hilt](https://img.shields.io/badge/Dagger_Hilt-2.55-orange.svg?style=for-the-badge&logo=dagger)
![Vico](https://img.shields.io/badge/Charts-Vico_2.0.1-9966FF.svg?style=for-the-badge)
![Lottie](https://img.shields.io/badge/Animations-Lottie-00D2B5.svg?style=for-the-badge)

---

## 🚀 Key Features

*   **📊 Dynamic Dashboard**: Live portfolio balance calculation with real-time 24h volume tracking and asset summaries.
*   **👁️ Privacy Mode**: Secure your data with a tap—toggle balance visibility directly on the dashboard to mask sensitive information.
*   **📈 Advanced Cartesian Charts**: Deep-dive into asset performance with historical price visualization for multiple timeframes (**1D, 1W, 1M, 1Y**), powered by the modern **Vico 2.0 API**.
*   **🏷️ Intelligent Filtering**: Seamlessly toggle between **Coins** and **Tokens** with a custom-built, animated sliding tab system.
*   **✨ Smooth Lottie Animations**: Integrated **Lottie** animations for premium visual feedback on the history screen and empty states.
*   **🏆 Market Leaders**: Automatic identification of the "Best Coin" and "Best Token" based on 24-hour market performance.
*   **📜 Styled Transaction History**: A comprehensive ledger of all your trades with dynamic status indicators (**Completed**, **Pending**, **Failed**).
*   **🌑 OLED Optimized Dark Theme**: A high-contrast, premium UI designed for modern displays and maximum energy efficiency.

---

## 📸 Screen Previews

|                           Home Dashboards                           |                        Market Analysis                        |                         Trade History                         |
|:------------------------------------------------------------------:|:-------------------------------------------------------------:|:-------------------------------------------------------------:|
|<img width="389" height="800" alt="dashboard1" src="https://github.com/user-attachments/assets/02a2b984-3307-4e7d-b180-604312949c66" /> <img width="622" height="1280" alt="dashboard2" src="https://github.com/user-attachments/assets/83ea5a50-f9e2-46c5-9aac-037f4b8fe150" /> | <img width="622" height="1280" alt="details" src="https://github.com/user-attachments/assets/564a6cf0-5266-4728-9e47-793d4f70d6e0" /> | <img width="622" height="1280" alt="history" src="https://github.com/user-attachments/assets/d4c34728-81c9-4cc3-ae41-af90bdc83538" /> |



---

## 🛠 Modern Android Tech Stack

This project is built using the latest industry standards and **Clean Architecture** principles:

*   **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) for a fully declarative and reactive UI.
*   **Architecture**: MVVM (Model-View-ViewModel) + Use Cases for robust business logic separation.
*   **DI**: [Dagger Hilt](https://dagger.dev/hilt/) for effortless and scalable dependency management.
*   **Networking**: [Retrofit 2](https://square.github.io/retrofit/) & [OkHttp](https://square.github.io/okhttp/) for reliable API communication.
*   **Serialization**: [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) for lightning-fast and type-safe JSON parsing.
*   **Charts**: [Vico 2.0](https://github.com/patrykandpatrick/vico) — the most advanced charting engine for Compose.
*   **Animations**: [Lottie Compose](https://github.com/airbnb/lottie-android) for high-quality, vector-based UI animations.
*   **Data Source**: Real-time market data provided by the [Coinpaprika API](https://api.coinpaprika.com/).

---

## 🚀 Quick Start

### Prerequisites
- **Android Studio Ladybug** (2024.2.1) or newer.
- **Android SDK 36** (Android 15) support.
- **Gradle 8.5+**

### Installation
1.  **Clone the repository**:
    ```bash
    git clone https://github.com/HrayrXachatryan/CryptoApp.git
    ```
2.  **Open in Android Studio**.
3.  **Sync Gradle** to download all dependencies.
4.  **Rebuild Project** (`Build > Rebuild Project`) to trigger Hilt's annotation processing.
5.  **Run** the app on your physical device or emulator (API 24+).

---

## 📂 Project Structure

```text
com.example.cryptoapp
├── data
│   └── remote          # API Definitions, Retrofit Modules, and DTOs
├── domain
│   ├── model           # Domain Entities (Coin, Transaction, etc.)
│   └── use_case        # Business Logic / Interactors
└── presentation
    ├── coin_list       # Dashboard & Main Market Listing
    ├── coin_detail     # Detailed View & Vico Chart Integration
    ├── history         # Transaction History UI with Lottie
    └── theme           # Design System: Color, Shape, and Typography
```

---

