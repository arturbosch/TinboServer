package io.gitlab.arturbosch.tinboserver.html

import io.gitlab.arturbosch.tinboserver.data.TinboConnector
import kotlinx.html.UL
import kotlinx.html.a
import kotlinx.html.li
import kotlinx.html.ul
import org.jetbrains.ktor.application.Application
import org.jetbrains.ktor.application.call
import org.jetbrains.ktor.features.feature
import org.jetbrains.ktor.locations.Locations
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
class Home(val tab: Content = Content.Notes)

enum class Content {
	Notes, Tasks, Timers
}

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
			call.respondHtml {
				val value = Content.valueOf(call.parameters[Home::tab.name] ?: Content.Notes.name)
				ul("nav nav-tabs") {
					tabLink(application, value, Content.Notes)
					tabLink(application, value, Content.Tasks)
					tabLink(application, value, Content.Timers)
				}

				val entries = when (value) {
					Content.Notes -> TinboConnector.retrieveNotes()
					Content.Tasks -> TinboConnector.retrieveTasks()
					Content.Timers -> TinboConnector.retrieveTimers()
				}

				ul("list-group") { entries.forEach { li("list-group-item") { +it.content } } }
			}
		}
	}
}

private fun UL.tabLink(application: Application, value: Content, content: Content) {
	val same = value == content
	li(if (same) "active" else "") {
		role = "presentation"
		a("#") {
			if (!same) {
				href = application.feature(Locations).href(Home(content))
			}
			+content.name
		}
	}

}
