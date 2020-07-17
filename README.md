## APOD-KMP

Apod-KMP is a POC application & server made with kotlin and compiled using kotlin multiplatform!

**This is still in progress and in a very very very early stage!**

### What's apod?
APOD stands for Astronomic Picture Of the Day and it's an api provided by nasa, for which you can find additional information [here](https://api.nasa.gov/)

### Project overview

The base project started with the usage of the [KaMPKit template](https://github.com/touchlab/KaMPKit) which is primarly focused on the mobile environment.
It has been modified by adding the js (react), jvm (server) and macosx targets, and by changing the architectural pattern to flux with [Redux Kotlin](https://reduxkotlin.org/)

The solution is composed by
- **shared** module, which contains the common code and the ios, android, jvm and js targets. It builds the ios, android and jvm common libraries and the js react application
- **app** module, which contains the android app that consumes the shared library
- **ios** folder, which contains the xcode project that consumes the shared library for the ios and macosx app
- **server** module, another kotlin multiplatform module that consumes the shared library and creates a jvm application. The actual *application* gradle script is in another project because it clashes with the *android* plugin and they cannot be put in the same gradle script at the moment

![Architecture](/doc/schema.png)

### Infrastructure
- First layer: mobile and web application asks the jvm server for the data if they don't have it cached locally
- Second layer: the jvm server searches for the data in the local mongoDB database and ask the apod api for it if it doesn't find it

### About Tales
A Tale is a suspended function that takes an action, the app dependency container and returns a list of actions. They are used inside the store middleware to handle action side effects. They work like [Thunks](https://github.com/reduxkotlin/redux-kotlin-thunk) but I don't like the fact that I need to dispatch a whole thunk instead of a plain action, so I made this.

Also, I like the name [Saga](https://redux-saga.js.org/) and [Epic](https://redux-observable.js.org/docs/basics/Epics.html), so I choose a new name for mine and called them Tale

### About Unit Tests
There are unit tests checking the tales of the application. This tales belongs to the shared common package, but since there is no [clean](https://youtrack.jetbrains.com/issue/KT-22228) way to runBlocking a coroutine in the common code (since runBlocking lacks the js implementation and has been removed) I've decided for the moment to put the test in the androidTest package.

This should change nothing because all the dependencies inside the container are mocked anyway, but I'll look for a way to put the tests back in the common code one day

### App MVP
The first draft of the app contains a landing page with the highlight of the apod of the day and a list od the last 10 apods.

When clicking on an APOD, you will be redirected to a detail page where you can see the image explanation and open up a big pinchable image or the youtube video player, depending on the selected APOD.

### CURRENT MVP COMPLETED FEATURES:

| Component | Splash page | Landing Page | Latest Strip | Detail Page | Player | Unit Tests | UI Tests |
| --------- | ----------- | ------------ | ------------ | ----------- | ------ | ---------- | -------- |
| Shared | - | X | X | - | - | X | - |
| Android | X | X | X | X | X | X |  |
| iOS | X | X | X |  |  |  |  |
| macOS |  |  |  |  |  |  |  |
| react |  |  |  |  |  |  |  |


### NEXT MVP STEPS:
- DeepLink to an APOD detail page
- Favourites APODs
- Maybe login to share the favourite apods between the clients
- Use [Jetpack compose](https://developer.android.com/jetpack/compose) for the android UI when it's released (at the moment there is custom ad-hoc DSL to create the ui at runtime, since I don't want to use xml and I don't want to use deprecated [Anko](https://github.com/Kotlin/anko) either
