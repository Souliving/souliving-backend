package souliving.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SoulivingApplication

fun main(args: Array<String>) {
    runApplication<SoulivingApplication>(*args)
}
