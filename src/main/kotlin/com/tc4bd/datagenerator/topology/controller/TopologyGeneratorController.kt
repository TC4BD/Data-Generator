package com.tc4bd.datagenerator.topology.controller

import com.tc4bd.datagenerator.topology.service.TopologyGeneratorService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/topology/generate")
class TopologyGeneratorController(topologyGeneratorService: TopologyGeneratorService) {
    val topologyGeneratorService = topologyGeneratorService
    @PostMapping()
    fun generateGraph(@RequestBody(required = false) numNodes: Int?) {
        if (numNodes == null) {
            topologyGeneratorService.generateData()
        } else {
            topologyGeneratorService.generateData(numNodes)
        }
    }

    @DeleteMapping
    fun deleteTopology() {
        topologyGeneratorService.deleteAll()
    }
}