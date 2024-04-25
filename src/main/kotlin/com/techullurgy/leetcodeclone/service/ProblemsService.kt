package com.techullurgy.leetcodeclone.service

import com.techullurgy.leetcodeclone.domain.db.Problem
import com.techullurgy.leetcodeclone.domain.network.PagedProblems
import com.techullurgy.leetcodeclone.repositories.ProblemRepository
import org.json.JSONObject
import org.springframework.stereotype.Service
import kotlin.math.ceil

@Service
class ProblemsService(
    private val problemsRepository: ProblemRepository
) {
    fun getProblems(page: Int, limit: Int): PagedProblems {
        val totalProblemsCount = problemsRepository.count()

        val startProblemNo = ((page - 1) * limit) + 1
        val endProblemNo = startProblemNo + limit

        val totalPages = ceil(totalProblemsCount.toDouble() / limit).toInt();

        if (startProblemNo > totalProblemsCount) {
            return PagedProblems(
                problems = emptyList(),
                currentPage = page,
                totalPages = totalPages
            )
        }

        val problems = problemsRepository.findProblemsPaginated(start = startProblemNo, end = endProblemNo)

        return PagedProblems(
            problems = problems,
            currentPage = page,
            totalPages = totalPages
        )
    }

    fun getProblemWithId(id: String): Problem? {
        return problemsRepository.findById(id).orElse(null)
    }

    fun store(problem: Problem) {
        problemsRepository.save(problem)
    }

    fun getNextProblemNo(): Int {
        val json = problemsRepository.getMaxProblemNo().mappedResults.first()
        val maxProblemNo = JSONObject(json).getInt("max")
        return maxProblemNo + 1
    }
}