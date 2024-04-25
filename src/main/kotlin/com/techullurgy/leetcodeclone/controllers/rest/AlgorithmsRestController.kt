package com.techullurgy.leetcodeclone.controllers.rest

import com.techullurgy.leetcodeclone.domain.service.CodeExecutionService
import com.techullurgy.leetcodeclone.domain.service.FileService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin("http://localhost:5173/")
class AlgorithmsRestController {

    @PostMapping("/code/run")
    fun runCode(@RequestBody code: CodeRequest, auth: Authentication): ResponseEntity<String> {
        val userId = auth.credentials.toString()

        FileService.writeFile("temp/java/$userId/Hello.java", code.code)

        val codeExecutionService = CodeExecutionService()
        codeExecutionService.start(userId, language = "Java", fileName = "Hello.java", emptyList())
        return ResponseEntity.ok("File Created")
    }
}

data class CodeRequest(
    val code: String,
    val problemId: String
)