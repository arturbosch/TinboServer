package io.gitlab.arturbosch.tinboserver.users

import io.gitlab.arturbosch.tinboserver.internal.InvalidCredentialsException
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

/**
 * @author Artur Bosch
 */

fun PipelineContext<ApplicationCall>.withValidCredentials(body: PipelineContext<ApplicationCall>.(ApplicationCall) -> Unit) {
	val credentials = call.request.basicAuthenticationCredentials()
	val principal = authenticate(credentials)
	if (principal != null) {
		body.invoke(this, call)
	} else throw InvalidCredentialsException()
}


fun Routing.checkAuthentication() {
	authentication {
		println("authentication...")
		basicAuthentication("ktor") {
			println("within basic ...")
			authenticate(it)
		}
	}
}

fun authenticate(user: UserPasswordCredential?): Principal? {
	println(user)
	return if (user != null) {
		users[user.name]?.let {
			println(it)
			if (it == user.password)
				UserIdPrincipal(user.name)
			else null
		}
	} else null
}