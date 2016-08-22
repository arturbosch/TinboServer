package io.gitlab.arturbosch.tinboserver.internal

import java.io.IOException

/**
 * @author Artur Bosch
 */
class InvalidServerConfigException(message: String, ioe: IOException) : RuntimeException(message, ioe)

class InvalidCredentialsException(message: String = "Provided credentials do not meet an user or are empty!") : RuntimeException(message)