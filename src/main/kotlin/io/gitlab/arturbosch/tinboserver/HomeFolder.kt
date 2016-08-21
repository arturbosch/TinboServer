package io.gitlab.arturbosch.tinboserver

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

	private fun checkAndCreate(path: Path, createFile: (Path) -> Path): Path {
		if (Files.notExists(path))
			createFile.invoke(path)
		return path
	}

	fun getDirectory(subPathInTinboDir: String): Path {
		val newDir = get().resolve(subPathInTinboDir)
		return checkAndCreate(newDir, { newDir -> Files.createDirectories(newDir) })
	}

}
