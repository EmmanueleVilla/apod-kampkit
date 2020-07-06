package co.touchlab.kampstarter.apod

import co.touchlab.kampstarter.model.Apod
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.dom.img
import react.dom.p

interface ApodProps : RProps {
    var apod: Apod
}

class BigApodComponent : RComponent<ApodProps, RState>() {
    override fun RBuilder.render() {
        if (props.apod == undefined) {
            return
        }
        div {
            img(src = props.apod.url) {
            }
            p {
                props.apod.explanation
            }
        }
    }
}
