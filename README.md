## APOD-KMP

Apod-KMP is a POC application & server made with kotlin and compiled using kotlin multiplatform!

**This is still in progress and in a very very very early stage!**

### What's apod?
APOD stands for Astronomic Picture Of the Day and it's an api provided by nasa, for which you can find additional information [here](https://api.nasa.gov/)

### Project overview

The base project started with the usage of the [KaMPKit template](https://github.com/touchlab/KaMPKit) which is primarly focused on the mobile environment.
It has been modified by adding the js (react) and jvm (server) targets and by changing the architectural pattern to flux with [Redux Kotlin](https://reduxkotlin.org/)

The solution is composed by
- **Shared** module, which contains the common code and the ios, android, jvm and js targets. It builds the ios, android and jvm common libraries and the js react application
- **app** module, which contains the android app that consumes the shared library
- **ios** folder, which contains the xcode project that consumes the shared library
- **server** module, another kotlin multiplatform module that consumes the shared library and creates a jvm application. The actual *application* gradle script is in another project because it clashes with the *android* plugin and they cannot be put in the same gradle script at the moment

### Infrastructure
- First layer: mobile and web application asks the jvm server for the data if they don't have it cached locally
- Second layer: the jvm server searches for the data in the local mongoDB database and ask the apod api for it if it doesn't find it
