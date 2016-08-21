package io.gitlab.arturbosch.tinboserver.route

import org.jetbrains.ktor.application.call
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.http.respondText
import org.jetbrains.ktor.routing.Routing
import org.jetbrains.ktor.routing.get
import org.json.simple.JSONObject

/**
 * @author Artur Bosch
 */
fun Routing.hello() {
	get("/") {
		call.respondText(ContentType.Text.Html, "Hello, Tinbo!")
	}
	get("/json") {
		onSuccess { println("Success!!") }
		onFinish { println("Finished!!") }
		call.respondText(ContentType.Application.Json, buildJson())
	}
}

private fun buildJson(): String {
	return JSONObject().apply {
		put("name", "Artur")
		put("age", "25")
		put("favorite_language", "Kotlin")
	}.toJSONString()
}