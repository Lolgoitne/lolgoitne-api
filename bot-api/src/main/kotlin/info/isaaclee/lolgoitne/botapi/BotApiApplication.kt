package info.isaaclee.lolgoitne.botapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["info.isaaclee.lolgoitne"])
class BotApiApplication

fun main(args: Array<String>) {
	runApplication<BotApiApplication>(*args)
}
