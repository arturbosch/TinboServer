package io.gitlab.arturbosch.tinboserver.data

import io.gitlab.arturbosch.tinboserver.config.HomeFolder
import kotlinx.support.jdk8.streams.toList
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

/**
 * @author Artur Bosch
 */
object TinboConnector {

	fun retrieveTasks(): List<Entry> {
		return extract("TiNBo/tasks/Tasks")
	}

	fun retrieveNotes(): List<Entry> {
		return extract("TiNBo/notes/Notes")
	}

	fun retrieveTimers(): List<Entry> {
		return extract("TiNBo/timers/Data")
	}

	private fun extract(name: String): List<Entry> {
		val home = HomeFolder.get().toAbsolutePath()
		val userDir = home.subpath(0, home.nameCount - 1)
		val tasksPath = Paths.get("/" + userDir.resolve(name).toString())
		return try {
			Files.lines(tasksPath).map(::Entry).toList()
		} catch (error: IOException) {
			listOf(Entry("No content available"))
		}
	}

}

data class Entry(val content: String)