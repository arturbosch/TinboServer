package io.gitlab.arturbosch.tinboserver.config

import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

/**
 * @author Artur Bosch
 */
object HomeFolder {

	const val USERS = "users"

	private val mainDir = "TinboServer"
	private val homeDir = System.getProperty("user.home") + File.separator + mainDir
	private val homePath = Paths.get(homeDir)

	fun get(): Path {
		if (Files.notExists(homePath))
			Files.createDirectory(homePath)
		return homePath
	}

	fun newFile(subPathInTinboDir: String, content: InputStream) {
		val newFile = get().resolve(subPathInTinboDir)
		Files.copy(content, newFile, StandardCopyOption.REPLACE_EXISTING)
	}

	private fun checkAndCreate(subPath: String, createFile: (Path) -> Path): Path {
		val newFile = get().resolve(subPath)
		if (Files.notExists(newFile))
			createFile.invoke(newFile)
		return newFile
	}

	fun getDirectory(subPath: String): Path {
		return checkAndCreate(subPath, { newDir -> Files.createDirectories(newDir) })
	}

	fun getFile(subPath: String): Path {
		return checkAndCreate(subPath, { newFile -> Files.createFile(newFile) })
	}

}
