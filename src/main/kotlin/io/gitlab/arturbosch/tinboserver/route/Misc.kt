package io.gitlab.arturbosch.tinboserver.route

import org.jetbrains.ktor.application.call
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.http.respondText
import org.jetbrains.ktor.routing.Routing
import org.jetbrains.ktor.routing.get

/**
 * @author Artur Bosch
 */
fun Routing.hello() {
	get("/") {
		call.respondText(ContentType.Text.Html, "Hello, Tinbo!")
	}
}