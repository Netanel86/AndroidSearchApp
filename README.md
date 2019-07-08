# Item Search App
This project is part of an Android Development course which was delivered by Startech Academy School 
Powered by Sears Israel, in partnership with: Wix, Gett, Chegg, Inneractive and My Heritage.
Main subjects: Android app development, Animations, Gestures and Layouts, Mobile automation and Unit tests.

## Introduction
<p>A java android search application that utilizes ShopYourWay API to search for and display items, with an option to open an item's url 
in a web view. App also handles typing history in shared memory.
The application is designed in MVP pattern and uses Dependency Injection for dependencies. 
3rd Party libraries used: OkHttp, Picasso, Gson, Dagger.</p>

## Application Architecture
### Dependency Injection
The app uses dagger library to inject dependencies. 

First layer are global dependencies or singletons which are available throughout the application layers, dagger will instantiate one instance of each global dependency and would provide that instance whenever its requested, e.g `Context`, `INetworkClient`, `ISharedPreferences`, are global dependencies.

Second layer are local dependencies, which are available only on certain views\scopes. dagger instantiates local dependencies only when its corresponding view\scope is active, and destroys them when scope is no longer active, e.g All presenters, routers and `IHistoryRepository` are local dependencies.

This diagram illustrates the aforementioned dependencies life-cycle in relation to the application's life-cycle:
<p align="center">
<img src="https://github.com/Netanel86/AndroidSearchApp/raw/dev/diagram/depedency_lifecycle.png" width="700" height="352" />
</p>

The object graph has one main component (`AppComponent`) which contains four modules who provide global dependencies: 

* `ActivityInjectionModule` is part of dagger infrastructure and is responsible for injecting dependencies into android activities.
* `ContextModule` provides application context.
* `NetworkUtilsModule` provides network utilities e.g. web client and json parser.
* `DataModule` provides global data repositories.

Additionally the graph holds two more modules, one for each activity, which are attached in `ActivityInjectionModule` class using `@ContributesAndroidInjector`, and are bound to there activity's life-cycle:

* `SearchActivityModule` provides search activity dependencies.
* `ProductActivityModule` provides product activity dependences.

these modules act as subcomponents of `AppComponent` class and provide local dependencies.

The below diagram illustrates the dagger object graph:
<p align="center">
<img src="https://github.com/Netanel86/AndroidSearchApp/raw/dev/diagram/dagger_object_graph.png" width="700" height="300" />
</p>

The last diagram illustrates the general scheme of component dependencies:
<p align="center">
<img src="https://github.com/Netanel86/AndroidSearchApp/raw/dev/diagram/component_dependencies.png" width="900" height="500" />
</p>
