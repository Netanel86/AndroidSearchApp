# Android Item Search Application (Java / MVP / Dagger)

## Overview

This project is an Android application that integrates with the ShopYourWay API to search and display products in real time.

Users can search for items, browse product details and images, and open item pages directly inside the app using an embedded WebView.

The application also stores persistent typing history to improve usability and user experience.

The project was designed using MVP architecture and Dependency Injection with Dagger to support clean separation of concerns, lifecycle-safe dependency management, and maintainable code structure.

---

## My Contribution

I designed and developed the full Android application, focusing on architecture, API integration, and dependency management.

My primary implementation included:

* REST API integration with ShopYourWay
* Network communication using OkHttp
* JSON parsing with Gson
* Dependency Injection using Dagger
* Image loading optimization using Picasso
* Persistent search history using shared storage
* Embedded WebView navigation
* MVP architecture and presenter logic

---

## Technical Challenges

### Dependency Management

As the project grew, manually managing dependencies across activities and presenters became difficult and error-prone.

### Lifecycle-Safe Architecture

Dependencies needed to respect Android activity lifecycle boundaries without leaking state or creating unstable object ownership.

### User Experience

Frequent repeated searches required persistent search history and responsive UI behavior.

---

## Solution

I used:

* **OkHttp** for efficient network communication
* **Gson** for JSON serialization and response parsing
* **Dagger** for dependency injection and cleaner architecture
* **Picasso** for optimized image loading and caching
* Local storage for persistent typing history

This improved maintainability, scalability, and overall user experience while keeping the codebase modular and easier to extend.

---

## Application Architecture

### Dependency Injection

The app uses the Dagger library to inject dependencies.

The first layer contains global dependencies (singletons) that are available throughout the application lifecycle. Dagger instantiates one instance of each global dependency and provides that same instance whenever requested.

Examples include:

* `Context`
* `INetworkClient`
* `ISharedPreferences`

The second layer contains local dependencies that are available only within specific views/scopes. Dagger creates these dependencies only while the corresponding activity is active and destroys them when the scope ends.

Examples include:

* Presenters
  n- Routers
* `IHistoryRepository`

This diagram illustrates dependency lifecycle in relation to the application lifecycle:

<p align="center">
<img src="https://github.com/Netanel86/AndroidSearchApp/raw/dev/diagram/depedency_lifecycle.png" width="700" height="352" />
</p>

---

## Dagger Object Graph

The object graph has one main component (`AppComponent`) which contains four modules that provide global dependencies:

* `ActivityInjectionModule` → Responsible for injecting dependencies into Android activities
* `ContextModule` → Provides application context
* `NetworkUtilsModule` → Provides network utilities such as web client and JSON parser
* `DataModule` → Provides global data repositories

Additionally, the graph contains two activity-specific modules attached using `@ContributesAndroidInjector`, each bound to its activity lifecycle:

* `SearchActivityModule` → Provides search activity dependencies
* `ProductActivityModule` → Provides product activity dependencies

These modules act as subcomponents of `AppComponent` and provide local dependencies.

The following diagram illustrates the Dagger object graph:

<p align="center">
<img src="https://github.com/Netanel86/AndroidSearchApp/raw/dev/diagram/dagger_object_graph.png" width="700" height="300" />
</p>

---

## Component Dependencies

The following diagram illustrates the general dependency flow between the main application components:

<p align="center">
<img src="https://github.com/Netanel86/AndroidSearchApp/raw/dev/diagram/component_dependencies.png" width="900" height="500" />
</p>

---

## Technologies

* Java
* Android SDK
* MVP Architecture
* Dagger
* OkHttp
* Gson
* Picasso
* WebView
* SharedPreferences / Local Storage
* REST APIs

---

## Features

* Real-time item search
* Product image display
* Embedded WebView for product pages
* Persistent search history
* Efficient image loading
* Clean dependency injection architecture
* Lifecycle-aware MVP design

---

## What I Learned

* Android application architecture
* Dependency injection with Dagger
* Working with external REST APIs
* Lifecycle-safe dependency management
* Network error handling
* UI responsiveness and user experience improvements

---

## Background

This project was developed as part of an advanced Android Development course delivered by Startech Academy School, powered by Sears Israel, in partnership with companies including Wix, Gett, Chegg, Inneractive, and MyHeritage.

The course focused on Android app development, UI design, animations, gestures, layouts, mobile automation, and unit testing.
