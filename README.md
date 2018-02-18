# Item Search App
This project is part of an Android Development course which was delivered by Startech Academy School 
Powered by Sears Israel, in partnership with: Wix, Gett, Chegg, Inneractive and My Heritage.
Main subjects: Android app development, Animations, Gestures and Layouts, Mobile automation and Unit tests.
#### note that this project is still under development, there are a few finishing touches i still have to make.
## Introduction
A java android search application that utilizes ShopYourWay API to search for and display items, with an option to open an item's url 
in a web view. App also handles typing history in shared memory.
The application is designed is MVP pattern and uses Dependency Injection for dependencies.
3rd Party libraries used: OkHttp, Picasso, Gson, Dagger.
## Application Architecture
### Dependency Injection
The app uses dagger's library to inject dependencies. 

First level are global dependencies or singletons which are available throughout the application layers, dagger will instantiate one instance of each global dependency and would provide that instance whenever its requested, e.g `Context`, `INetworkClient`, `ISharedPreferences`, are global dependencies.

Second level are local dependencies, which are available only on certain views\scopes. dagger instantiates local dependencies only when its corresponding view\scope is active, and destroys them when scope is no longer active, e.g All presenters and `IHistoryRepository` are local dependencies.

This diagram illustrates the aforementioned dependencies life-cycle in relation to the application's life-cycle:

<img src="https://github.com/Netanel86/AndroidSearchApp/raw/dev/Diagram/Dagger_Scopes.png" width="900" height="350" />