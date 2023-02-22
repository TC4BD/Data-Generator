package com.tc4bd.datagenerator.topology.model

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Property
import org.springframework.data.neo4j.core.schema.Relationship
import org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.stereotype.Repository


@Node
data class Station(
    @Id @GeneratedValue val id:Long,
    @Relationship(type = "POWERS", direction = OUTGOING)
    val substations: List<Station>,
    @Property
    val type: StationType?,
    @Property
    val operatingPower: Int,
    @Property
    val efficiencyFactor: Double,
)

@Repository
interface StationRepository : Neo4jRepository<Station, Long> {
    override fun deleteAll()
}