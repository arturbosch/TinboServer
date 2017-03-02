package io.gitlab.arturbosch.tinboserver.route

import org.jetbrains.ktor.application.call
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.Routing
import org.jetbrains.ktor.routing.get
import org.json.simple.JSONObject

/**
 * @author Artur Bosch
 */
fun Routing.json() {
	get("/json") {
		call.respondText(buildJson(), ContentType.Application.Json)
	}
}

private fun buildJson(): String {
	return JSONObject().apply {
		put("name", "Artur")
		put("age", "25")
		put("favorite_language", "Kotlin")
	}.toJSONString()
}