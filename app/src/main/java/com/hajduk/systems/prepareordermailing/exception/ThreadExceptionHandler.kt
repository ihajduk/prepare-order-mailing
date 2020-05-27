package com.hajduk.systems.prepareordermailing.exception

import org.slf4j.LoggerFactory

object ThreadExceptionHandler {

    private val logger = LoggerFactory.getLogger(ThreadExceptionHandler::class.java)

    fun configure() {
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable -> //Catch your exception
            logger.error("Caught exception: ", throwable)
        }
    }
}