package com.techullurgy.leetcodeclone.domain.db

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

enum class ProblemDifficulty {
    EASY, MEDIUM, HARD
}

enum class CodeLanguage {
    C, CPP, JAVA, PYTHON, JAVASCRIPT
}

data class ProblemExample(
    val input: String,
    val output: String,
    val explanation: String,
    val imageUrls: List<String>? = null
)

sealed class CodeSnippet(val language: CodeLanguage, val snippet: String) {

    @TypeAlias("c")
    class CSnippet(snippet: String) : CodeSnippet(CodeLanguage.C, snippet)

    @TypeAlias("cpp")
    class CPPSnippet(snippet: String) : CodeSnippet(CodeLanguage.CPP, snippet)

    @TypeAlias("java")
    class JavaSnippet(snippet: String) : CodeSnippet(CodeLanguage.JAVA, snippet)

    @TypeAlias("python")
    class PythonSnippet(snippet: String) : CodeSnippet(CodeLanguage.PYTHON, snippet)

    @TypeAlias("javascript")
    class JavascriptSnippet(snippet: String) : CodeSnippet(CodeLanguage.JAVASCRIPT, snippet)
}

data class ProblemTestcase(
    val id: Int,
    val input: String,
    val output: String,
    val isHidden: Boolean = true
)

data class FileContent(
    val c: String,
    val cpp: String,
    val java: String,
    val python: String,
    val javascript: String
)

@Document("leetcode_problem_details")
data class Problem(
    @Id
    val id: ObjectId = ObjectId(),

    @Indexed(unique = true)
    val title: String,

    @Indexed(unique = true)
    val problemNo: Int,

    val difficulty: ProblemDifficulty,
    val likes: Long,
    val dislikes: Long,
    val description: String,
    val examples: List<ProblemExample>,
    val snippets: List<CodeSnippet>,
    val testcases: List<ProblemTestcase>,
    val constraints: List<String>,

    // For admin Purposes
    val fileContent: FileContent,
    val replaceableStr: FileContent,
)

