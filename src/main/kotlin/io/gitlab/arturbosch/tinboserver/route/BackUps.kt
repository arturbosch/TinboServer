package io.gitlab.arturbosch.tinboserver.route

import io.gitlab.arturbosch.tinboserver.HomeFolder
import io.gitlab.arturbosch.tinboserver.users.withValidCredentials
import org.jetbrains.ktor.application.call
import org.jetbrains.ktor.application.respondWrite
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.http.MultiPartData
import org.jetbrains.ktor.http.PartData
import org.jetbrains.ktor.http.contentType
import org.jetbrains.ktor.http.isMultipart
import org.jetbrains.ktor.routing.Routing
import org.jetbrains.ktor.routing.post

/**
 * @author Artur Bosch
 */
fun Routing.backup() {
	post("/backup") {
		withValidCredentials {
			val multipart = call.request.content.get<MultiPartData>()

			call.response.contentType(ContentType.Text.Plain)
			call.respondWrite {
				if (!call.request.isMultipart()) {
					appendln("Not a multipart request")
				} else {
					multipart.parts.forEach { part -> handlePart(part) }
				}
			}
		}
	}
}

private fun handlePart(part: PartData) {
	when (part) {
		is PartData.FormItem -> {
			println("Form field: ${part.partName} = ${part.value}")
		}
		is PartData.FileItem -> {
			println("File field: ${part.partName} -> ${part.originalFileName} of ${part.contentType}")
			HomeFolder.newFile(part.originalFileName ?: "backup.zip", part.streamProvider.invoke())
		}
	}
	part.dispose()
}
