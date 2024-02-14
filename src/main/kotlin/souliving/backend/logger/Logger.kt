package souliving.backend.logger

import org.slf4j.Logger
import org.slf4j.LoggerFactory

val log: Logger = LoggerFactory.getLogger("Coroutines")

fun log(msg: Any?) {
    log.info(msg.toString())
}

fun logResponse(
    req: String,
    response: String,
) {
    log.info("Request $req: answer $response")
}

fun logResponse(req: String) {
    log.info("Request $req")
}

fun logError(req: String) {
    log.error(req)
}
