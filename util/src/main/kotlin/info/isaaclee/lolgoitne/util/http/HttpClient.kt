package info.isaaclee.lolgoitne.util.http

import org.springframework.web.reactive.function.client.WebClient

open class HttpClient(
	private val path: String
) {
	var webClient: WebClient = WebClient.create(path)
}