package co.touchlab.kampstarter

import co.touchlab.kampstarter.apod.BigApodComponent
import co.touchlab.kampstarter.model.Apod
import co.touchlab.kampstarter.splash.SplashInteractor
import react.*
import react.dom.h1

interface AppState: RState {
    var interactor : SplashInteractor
    var apod: Apod
}

class App : RComponent<RProps, AppState>() {

    override fun componentDidMount() {
        state.apod = Apod()
        state.interactor = SplashInteractor()
        state.interactor.subscribe {
            setState {
                apod = it.splashState.apod
            }
        }
        state.interactor.init()
    }

    override fun RBuilder.render() {
        h1 {
            +"Hello, React+Kotlin/JS!"
        }
        child(BigApodComponent::class) {
            attrs.apod = state.apod
        }
    }
    /*
    browserRouter {
            div {
                ul {
                    li {
                        routeLink("/") { +"Home" }
                    }
                    li {
                        routeLink("/test") { +"test" }
                    }
                }
                switch {
                    route("/") {
                        child(BigApodComponent::class) {
                            attrs.apod = state.apod
                        }
                    }
                    route("/test") {
                        h1 {
                            +"Test path"
                        }
                    }
                }
            }
        }
     */
}