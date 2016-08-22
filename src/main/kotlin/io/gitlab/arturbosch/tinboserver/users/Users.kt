package io.gitlab.arturbosch.tinboserver.users

import io.gitlab.arturbosch.tinboserver.HomeFolder
import io.gitlab.arturbosch.tinboserver.internal.InvalidServerConfigException
import org.jetbrains.ktor.application.ApplicationCall
import org.jetbrains.ktor.application.call
import org.jetbrains.ktor.auth.Principal
import org.jetbrains.ktor.auth.UserIdPrincipal
import org.jetbrains.ktor.auth.UserPasswordCredential
import org.jetbrains.ktor.auth.authentication
import org.jetbrains.ktor.auth.basicAuthentication
import org.jetbrains.ktor.auth.basicAuthenticationCredentials
import org.jetbrains.ktor.pipeline.PipelineContext
import org.jetbrains.ktor.routing.Routing
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
