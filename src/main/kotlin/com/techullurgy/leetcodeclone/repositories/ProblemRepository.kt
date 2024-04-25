package com.techullurgy.leetcodeclone.repositories

import com.techullurgy.leetcodeclone.domain.db.Problem
import org.springframework.data.mongodb.core.aggregation.AggregationResults
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProblemRepository : MongoRepository<Problem, String> {

    @Query("{ 'problemNo': { \$gte: ?0, \$lt: ?1 } }")
    fun findProblemsPaginated(start: Int, end: Int): List<Problem>

    @Aggregation(
        pipeline = [
            "{ \$group: { _id: null, max: {\$max: '\$problemNo'} } }"
        ]
    )
    fun getMaxProblemNo(): AggregationResults<String>
}