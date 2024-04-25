package com.techullurgy.leetcodeclone.controllers.rest

import com.techullurgy.leetcodeclone.domain.db.*
import com.techullurgy.leetcodeclone.service.ProblemsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("admin")
class AdminPlaygroundController(
    private val problemsService: ProblemsService
) {
    @PostMapping("problems/new")
    fun saveProblem(@RequestBody saveProblemRequest: SaveProblemRequest): ResponseEntity<String> {
        val snippets = listOf(
            CodeSnippet.CSnippet(saveProblemRequest.snippets.c),
            CodeSnippet.CPPSnippet(saveProblemRequest.snippets.cpp),
            CodeSnippet.JavaSnippet(saveProblemRequest.snippets.java),
            CodeSnippet.PythonSnippet(saveProblemRequest.snippets.python),
            CodeSnippet.JavascriptSnippet(saveProblemRequest.snippets.javascript)
        )

        val problemNo = problemsService.getNextProblemNo()

        val problem = Problem(
            title = saveProblemRequest.title,
            description = saveProblemRequest.description,
            difficulty = saveProblemRequest.difficulty,
            problemNo = problemNo,
            likes = 0,
            dislikes = 0,
            snippets = snippets,
            examples = saveProblemRequest.examples,
            testcases = saveProblemRequest.testcases,
            constraints = saveProblemRequest.constraints,
            fileContent = saveProblemRequest.fileContent,
            replaceableStr = saveProblemRequest.replaceableStr
        )
        problemsService.store(problem)

        return ResponseEntity.ok("Successfully Problem is created ${problem.id}")
    }

    @PutMapping("/problems/{problemId}")
    fun updateProblem(
        @RequestBody saveProblemRequest: SaveProblemRequest,
        @PathVariable problemId: String
    ): ResponseEntity<String> {
        val problem = problemsService.getProblemWithId(problemId)!!

        val snippets = listOf(
            CodeSnippet.CSnippet(saveProblemRequest.snippets.c),
            CodeSnippet.CPPSnippet(saveProblemRequest.snippets.cpp),
            CodeSnippet.JavaSnippet(saveProblemRequest.snippets.java),
            CodeSnippet.PythonSnippet(saveProblemRequest.snippets.python),
            CodeSnippet.JavascriptSnippet(saveProblemRequest.snippets.javascript)
        )

        val updatedProblem = problem.copy(
            title = saveProblemRequest.title,
            description = saveProblemRequest.description,
            examples = saveProblemRequest.examples,
            testcases = saveProblemRequest.testcases,
            difficulty = saveProblemRequest.difficulty,
            snippets = snippets,
            fileContent = saveProblemRequest.fileContent,
            replaceableStr = saveProblemRequest.replaceableStr
        )

        problemsService.store(updatedProblem)

        return ResponseEntity.ok("Successfully Problem is updated")
    }
}


data class SaveProblemRequest(
    val title: String,
    val description: String,
    val examples: List<ProblemExample>,
    val testcases: List<ProblemTestcase>,
    val difficulty: ProblemDifficulty,
    val constraints: List<String>,
    val snippets: FileContent,
    val fileContent: FileContent,
    val replaceableStr: FileContent
)