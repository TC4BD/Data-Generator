package com.tc4bd.datagenerator.topology.service

import com.tc4bd.datagenerator.topology.model.Station
import com.tc4bd.datagenerator.topology.model.StationRepository
import com.tc4bd.datagenerator.topology.model.StationType
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.DeleteMapping
import kotlin.random.Random

@Service
class TopologyGeneratorService(
    stationRepository: StationRepository
) {
    val stationRepository = stationRepository


    fun generateData() {
        val numStations = genNumSubstations(StationType.DISTRIBUTION_TRANSFORMER_STATION)
        generateStations(numStations, StationType.DISTRIBUTION_TRANSFORMER_STATION)
    }

    fun generateData(numStations: Int) {
        generateStations(numStations, StationType.DISTRIBUTION_TRANSFORMER_STATION)
    }

    private fun generateOperatingPower(type: StationType):Int {
        return when (type) {
            StationType.TRANSFORMER_STATION -> listOf(200,220,230).random()
            StationType.DISTRIBUTION_TRANSFORMER_STATION -> listOf(380,440,400,420).random()
            StationType.MEASUREMENT_STATION -> Random.nextInt(50,100)
        }
    }

    private fun generateLossCoefficient(type: StationType):Double {
        return when (type) {
            StationType.DISTRIBUTION_TRANSFORMER_STATION -> 1.0
            StationType.TRANSFORMER_STATION -> Random.nextDouble(0.8, 0.9)
            StationType.MEASUREMENT_STATION -> Random.nextDouble(0.6, 0.8)
        }
    }

    private fun getNextType(stationType: StationType): StationType? {
        if (stationType == StationType.TRANSFORMER_STATION) {
            return StationType.MEASUREMENT_STATION
        }
        if (stationType == StationType.DISTRIBUTION_TRANSFORMER_STATION) {
            return if (Random.nextFloat() < 0.9) StationType.TRANSFORMER_STATION else StationType.MEASUREMENT_STATION
        }
        return null
    }

    private fun genNumSubstations(type: StationType):Int {
        return when (type) {
            StationType.DISTRIBUTION_TRANSFORMER_STATION -> Random.nextInt(1,6)
            StationType.TRANSFORMER_STATION -> Random.nextInt(1,4)
            StationType.MEASUREMENT_STATION -> Random.nextInt(5,15)
        }
    }

    private fun generateStations(numStations: Int, type: StationType?):List<Station> {
        if (type == null) {
            return listOf()
        }

        return (0..numStations).map {
            val stationType = getNextType(type)
            val operatingPower = generateOperatingPower(type)
            val lossCoefficient = generateLossCoefficient(type)
            val numSubStations = genNumSubstations(type)
            val substations = generateStations(numSubStations, stationType)
            val station = Station(
                -1,
                substations,
                type,
                operatingPower,
                lossCoefficient)
            stationRepository.save(station)
        }
    }
    @DeleteMapping
    fun deleteAll() {
        stationRepository.deleteAll()
    }
}