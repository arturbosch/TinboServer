package io.gitlab.arturbosch.tinboserver.data

import org.junit.Assert
import org.junit.Test

/**
 * @author Artur Bosch
 */
class TinboConnectorTest {

	@Test
	fun retrieveTasks() {
		try {
			TinboConnector.retrieveTasks()
		} catch (error: Exception) {
			Assert.fail("An error occurred!")
		}
	}

}