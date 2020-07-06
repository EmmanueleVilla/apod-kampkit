package com.shadowings.apodkmp

import com.shadowings.apodkmp.apod.BigApodComponent
import com.shadowings.apodkmp.home.HomeInteractor
import com.shadowings.apodkmp.model.Apod
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.h1
import react.setState

interface AppState : RState {
    var interactor: HomeInteractor
    var apod: Apod
}

class App : RComponent<RProps, AppState>() {

    override fun componentDidMount() {
        state.apod = Apod()
        state.interactor = HomeInteractor()
        state.interactor.subscribe {
            setState {
                apod = it.homeState.apod
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
