
# Weather
 This app based on `openweathermap` API to demonstrate latest Android Stack


## Stack

- [Kotlin](https://kotlinlang.org/)
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For all asynchronous work
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream replacing LiveData.
- [Compose](https://developer.android.com/jetpack/compose) - Android’s modern toolkit for building native UI.
- [Navigation](https://developer.android.com/guide/navigation) - Android library to navigate between different compose UI
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture)
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - To survive android lifecycle changes
  - [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager) - To schedule reliable, asynchronous tasks
- [Hilt](https://dagger.dev/hilt/) - Dependency Injection
- [Retrofit](https://square.github.io/retrofit/) - HTTP client
- [GSON](https://github.com/google/gson) - Gson is a Java library that can be used to convert Java Objects into their JSON representation
- [Coil](https://github.com/coil-kt/coil) - An image loading library for Compose.
- [Material](https://github.com/material-components/material-components-android) - Material Design UI components

## Architecture

This project is built on multi module MVVM Architecture. Each feature is in separate module and has 3 sub-modules; 

**Presentation Module**  contains  _UI (Composable functions)_  that are coordinated by  _ViewModels which execute 1 or multiple Use cases._  Presentation Layer depends on Domain and Data Layer.

**Domain Module** is the most INNER part of the onion (no dependencies with other layers) and it contains _Entities, Use cases & Repository Interfaces._ Use cases combine data from 1 or multiple Repository Interfaces.

**Data Module**  contains  _Repository Implementations and 1 or multiple Data Sources._  Repositories are responsible to coordinate data from the different Data Sources. Data Layer depends on Domain Layer.

## Next?
[] Add unit tests <br />
[] Add UI tests<br />
[] Improve app design<br />
[] Improve caching storage (move to Room)<br />
[] Better error handling<br />
[] Refactor Location work to support latest Android changes<br />
