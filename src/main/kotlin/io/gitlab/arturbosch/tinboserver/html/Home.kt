package io.gitlab.arturbosch.tinboserver.html

import io.gitlab.arturbosch.tinboserver.data.TinboConnector
import kotlinx.html.li
import kotlinx.html.p
import kotlinx.html.ul
import org.jetbrains.ktor.application.call
import org.jetbrains.ktor.locations.location
import org.jetbrains.ktor.routing.Routing
import org.jetbrains.ktor.routing.get
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors

/**
 * @author Artur Bosch
 */

@location("/")
class Home

object HomeCss {
	private val css = lazy {
		try {
			Files.lines(Paths.get(HomeCss::class.java.getResource("/styles/home.css").path)).
					map { it.trim() }.collect(Collectors.joining())
		} catch (error: IOException) {
			""
		}
	}

	fun get(): String = css.value
}

fun Routing.index() {
	location<Home> {

		get {
			val notes = TinboConnector.retrieveNotes()
			val tasks = TinboConnector.retrieveTasks()
			call.respondHtml {
				p { +"Notes:" }
				ul("list-group") { notes.forEach { li("list-group-item") { +it.content } } }
				p { +"Tasks:" }
				ul("list-group") { tasks.forEach { li("list-group-item") { +it.content } } }
			}
		}
	}
}
