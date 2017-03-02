package io.gitlab.arturbosch.tinboserver.data

import io.gitlab.arturbosch.tinboserver.config.HomeFolder
import org.slf4j.LoggerFactory
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.streams.toList

/**
 * @author Artur Bosch
 */
object TinboConnector {

	private val logger = LoggerFactory.getLogger(TinboConnector.javaClass.simpleName)

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
			logger.error("Error while reading tinbo sub folders!")
			listOf(Entry("No content available"))
		}
	}

}

data class Entry(val content: String)