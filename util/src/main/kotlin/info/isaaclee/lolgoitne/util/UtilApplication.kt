package info.isaaclee.lolgoitne.util

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["info.isaaclee.lolgoitne"])
class UtilApplication

fun main(args: Array<String>) {
	runApplication<UtilApplication>(*args)
}
