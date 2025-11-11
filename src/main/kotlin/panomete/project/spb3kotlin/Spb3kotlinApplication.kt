package panomete.project.spb3kotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class Spb3kotlinApplication

fun main(args: Array<String>) {
    runApplication<Spb3kotlinApplication>(*args)
}
