package io.gitlab.arturbosch.tinboserver

import io.gitlab.arturbosch.tinboserver.html.index
import io.gitlab.arturbosch.tinboserver.route.backup
import io.gitlab.arturbosch.tinboserver.route.json
import org.jetbrains.ktor.features.install
import org.jetbrains.ktor.locations.Locations
import org.jetbrains.ktor.netty.embeddedNettyServer

/**
 * @author Artur Bosch
 */
object TinboServer {

	@JvmStatic
	fun main(args: Array<String>) {
		embeddedNettyServer(8088) {
			application.install(Locations.LocationsFeature)
			index()
			json()
			backup()
		}.start(wait = true)
	}

}

