package nl.ordina.robotics.server

import io.github.oshai.kotlinlogging.KotlinLogging
import io.opentelemetry.sdk.autoconfigure.AutoConfiguredOpenTelemetrySdk
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import io.vertx.tracing.opentelemetry.OpenTelemetryOptions
import nl.ordina.robotics.server.robot.RobotDeploymentVerticle
import nl.ordina.robotics.server.web.WebVerticle
import java.util.Properties

private val logger = KotlinLogging.logger {}

fun main() {
    logger.info { "Starting server" }
    loadProperties()

    val globalOtel = AutoConfiguredOpenTelemetrySdk
        .builder()
        .addSpanExporterCustomizer { t, u ->
            println("Foobar $t, $u")
            t
        }
        .setResultAsGlobal()
        .build()

    val options = VertxOptions()
        .setTracingOptions(
            OpenTelemetryOptions(globalOtel.openTelemetrySdk),
        )

    Vertx.vertx(options).apply {
        deployVerticle(WebVerticle())
        deployVerticle(RobotDeploymentVerticle())
    }
}

private fun loadProperties(filename: String = "application.properties") = object {}
    .javaClass
    .classLoader
    .getResourceAsStream(filename)
    .use { inputStream ->
        Properties().apply {
            load(inputStream)
        }
    }
    .map { (key, value) ->
        System.setProperty(key.toString(), value.toString())
    }
