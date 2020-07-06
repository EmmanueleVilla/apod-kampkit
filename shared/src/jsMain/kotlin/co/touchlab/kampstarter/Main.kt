package co.touchlab.kampstarter

import kotlin.browser.document
import react.dom.render

fun main() {
    render(document.getElementById("root")) {
        child(App::class) {}
    }
}
