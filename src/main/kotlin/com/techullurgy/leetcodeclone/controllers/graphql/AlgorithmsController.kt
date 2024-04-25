package com.techullurgy.leetcodeclone.controllers.graphql

import com.techullurgy.leetcodeclone.domain.db.CodeLanguage
import com.techullurgy.leetcodeclone.domain.db.CodeSnippet
import com.techullurgy.leetcodeclone.domain.db.Problem
import com.techullurgy.leetcodeclone.domain.network.PagedProblems
import com.techullurgy.leetcodeclone.service.ProblemsService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class AlgorithmsController(
    private val problemsService: ProblemsService
) {
    @QueryMapping
    fun problems(
        @Argument page: Int,
        @Argument limit: Int
    ): PagedProblems {
        return problemsService.getProblems(page, limit)
    }

    @QueryMapping
    fun problem(@Argument id: String): Problem? {
        return problemsService.getProblemWithId(id)
    }

    @QueryMapping
    fun snippet(@Argument id: String, @Argument language: String): CodeSnippet? {
        val problem = problemsService.getProblemWithId(id)
        val snippets = problem?.snippets

        return when (language) {
            "Java" -> snippets?.firstOrNull { it.language == CodeLanguage.JAVA }

            "C" -> snippets?.firstOrNull { it.language == CodeLanguage.C }

            "CPP" -> snippets?.firstOrNull { it.language == CodeLanguage.CPP }

            "Python" -> snippets?.firstOrNull { it.language == CodeLanguage.PYTHON }

            "Javascript" -> snippets?.firstOrNull { it.language == CodeLanguage.JAVASCRIPT }

            else -> null
        }
    }
}