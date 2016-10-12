package io.gitlab.arturbosch.tinboserver

import io.gitlab.arturbosch.tinboserver.route.backup
import io.gitlab.arturbosch.tinboserver.route.hello
import io.gitlab.arturbosch.tinboserver.users.withValidCredentials
import org.jetbrains.ktor.application.call
import org.jetbrains.ktor.netty.embeddedNettyServer
import org.jetbrains.ktor.routing.get

/**
 * @author Artur Bosch
 */
object TinboServer {

	@JvmStatic
	fun main(args: Array<String>) {
		embeddedNettyServer(8088) {
			hello()
			backup()
			get("/test") {
				withValidCredentials {
					call.respond("Hello")
				}
			}
		}.start(wait = true)
	}

}

