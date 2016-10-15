package io.gitlab.arturbosch.tinboserver.users

import io.gitlab.arturbosch.tinboserver.config.HomeFolder
import io.gitlab.arturbosch.tinboserver.internal.InvalidServerConfigException
import java.io.IOException
import java.nio.file.Files

/**
 * @author Artur Bosch
 */

val users: Map<String, String> = initUserMap()

private fun initUserMap(): Map<String, String> {
	val path = HomeFolder.getFile(HomeFolder.USERS)
	return try {
		Files.readAllLines(path)
				.map { it.substringBefore(";") to it.substringAfter(";") }
				.toMap()
	} catch(ioe: IOException) {
		throw InvalidServerConfigException("Error reading user data!", ioe)
	}
}
