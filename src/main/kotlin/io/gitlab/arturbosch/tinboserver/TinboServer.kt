package io.gitlab.arturbosch.tinboserver

import io.gitlab.arturbosch.tinboserver.route.backup
import io.gitlab.arturbosch.tinboserver.route.hello
import org.jetbrains.ktor.netty.embeddedNettyServer

/**
 * @author Artur Bosch
 */
class TinboServer {

	fun start() {
		embeddedNettyServer(8080) {
			hello()
			backup()
		}.start(wait = true)
	}

}

fun main(args: Array<String>) {
	TinboServer().start()
}