package io.gitlab.arturbosch.tinboserver.html

import kotlinx.html.BODY
import kotlinx.html.DIV
import kotlinx.html.ScriptType
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.html
import kotlinx.html.nav
import kotlinx.html.script
import kotlinx.html.stream.appendHTML
import kotlinx.html.style
import kotlinx.html.styleLink
import kotlinx.html.title
import org.jetbrains.ktor.application.ApplicationCall
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.response.respondText

/**
 * @author Artur Bosch
 */

fun ApplicationCall.respondHtml(block: DIV.() -> Unit): Nothing {
	val html = homePage {
		block()
	}
	respondText(ContentType.Text.Html, html)
}

private fun homePage(title: String = "Welcome to TinboServer", block: DIV.() -> Unit): String {
	return StringBuilder().appendln("<!DOCTYPE html>").appendHTML(prettyPrint = false)
			.html {
				head {
					style { +HomeCss.get() }
					title { +title }
					styleLink("https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css")
					script(ScriptType.textJavaScript, "https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js")
					script(ScriptType.textJavaScript, "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js")
				}
				body {
					defaultNavigation()
					div("page-header") {
						h1("text-center") { +"Welcome to TinboServer" }
					}
					div("container") {
						block()
					}
				}
			}.toString()
}


private fun BODY.defaultNavigation() {
	nav {
		classes = setOf("navbar", "navbar-default", "navbar-fixed-top")
		role = "navigation"
		div("container") {
			div("navbar-header") {
			}
			div("collapse navbar-collapse") {
			}
		}
	}
}