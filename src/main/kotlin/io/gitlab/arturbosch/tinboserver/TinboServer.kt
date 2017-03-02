package io.gitlab.arturbosch.tinboserver

import io.gitlab.arturbosch.tinboserver.html.index
import io.gitlab.arturbosch.tinboserver.route.backup
import io.gitlab.arturbosch.tinboserver.route.json
import org.jetbrains.ktor.application.install
import org.jetbrains.ktor.locations.Locations
import org.jetbrains.ktor.netty.embeddedNettyServer
import org.jetbrains.ktor.routing.Routing

/**
 * @author Artur Bosch
 */
object TinboServer {

	@JvmStatic
	fun main(args: Array<String>) {
		embeddedNettyServer(8088) {
			install(Locations.Feature)
			install(Routing) {
				index()
				json()
				backup()
			}
		}.start(wait = true)
	}

}

