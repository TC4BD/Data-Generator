package com.tc4bd.datagenerator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DataGeneratorApplication

fun main(args: Array<String>) {
    runApplication<DataGeneratorApplication>(*args)
}
