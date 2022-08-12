package info.isaaclee.lolgoitne.adminapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["info.isaaclee.lolgoitne"])
class AdminApiApplication

fun main(args: Array<String>) {
	runApplication<AdminApiApplication>(*args)
}
