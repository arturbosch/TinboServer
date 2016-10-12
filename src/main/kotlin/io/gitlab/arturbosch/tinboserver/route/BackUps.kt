package io.gitlab.arturbosch.tinboserver.route

import io.gitlab.arturbosch.tinboserver.HomeFolder
import io.gitlab.arturbosch.tinboserver.users.withValidCredentials
import org.jetbrains.ktor.application.call
import org.jetbrains.ktor.application.respondWrite
import org.jetbrains.ktor.auth.basicAuthenticationCredentials
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.request.MultiPartData
import org.jetbrains.ktor.request.PartData
import org.jetbrains.ktor.request.isMultipart
import org.jetbrains.ktor.response.contentType
import org.jetbrains.ktor.routing.Routing
import org.jetbrains.ktor.routing.post

/**
 * @author Artur Bosch
 */
fun Routing.backup() {
	post("/backup") {
		withValidCredentials {
			val multipart = call.request.content.get<MultiPartData>()
			val credentials = call.request.basicAuthenticationCredentials()!!
			call.response.contentType(ContentType.Text.Plain)
			call.respondWrite {
				if (!call.request.isMultipart()) {
					appendln("Not a multipart request")
				} else {
					multipart.parts.forEach { handlePart(it, credentials.name) }
				}
			}
		}
	}
}

private fun handlePart(part: PartData, username: String) {
	when (part) {
		is PartData.FormItem -> {
			println("Form field: ${part.partName} = ${part.value}")
		}
		is PartData.FileItem -> {
			println("File field: ${part.partName} -> ${part.originalFileName} of ${part.contentType}")
			HomeFolder.getDirectory(username)
			HomeFolder.newFile("$username/${part.originalFileName ?: "backup.zip"}", part.streamProvider.invoke())
		}
	}
	part.dispose()
}
