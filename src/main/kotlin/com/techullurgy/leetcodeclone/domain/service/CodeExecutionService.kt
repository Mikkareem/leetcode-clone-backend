package com.techullurgy.leetcodeclone.domain.service

import com.techullurgy.leetcodeclone.domain.db.ProblemTestcase
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit


class Compilers {
    companion object {
        const val FROM_DOCKER_IMAGE_FOR_C_COMPILER = "gcc:9.5.0"
        const val FROM_DOCKER_IMAGE_FOR_JAVA_COMPILER = "amazoncorretto:11"
        const val FROM_DOCKER_IMAGE_FOR_JAVASCRIPT_COMPILER = "node:21"
        const val FROM_DOCKER_IMAGE_FOR_CPP_COMPILER = "gcc:9.5.0"
        const val FROM_DOCKER_IMAGE_FOR_PYTHON3_COMPILER = "gcc:9.5.0"
    }
}

sealed interface CodeExecutionResult {
    data object Temporary : CodeExecutionResult
    data object Success : CodeExecutionResult
    data object Failure : CodeExecutionResult
}


class CodeExecutionService {

    fun compile(userId: String, language: String, fileName: String): CodeExecutionResult {
        val inputFilePath = when (language) {
            "C" -> "temp/c/$userId"
            "CPP" -> "temp/cpp/$userId"
            "Java" -> "temp/java/$userId"
            "Python" -> "temp/python/$userId"
            "Javascript" -> "temp/javascript/$userId"
            else -> throw IllegalArgumentException("Language can't be inferred")
        }

        createDockerFile(language, inputFilePath, fileName)

        val imageName = buildDockerImage(userId, inputFilePath)

        // TODO: Write sample testcase to the input.txt and delete once executed.
        val result: Pair<Boolean, String> = executeDockerImage(imageName, 0)

        var result2: CodeExecutionResult
        if (result.first) {
            result2 = CodeExecutionResult.Success
        } else {
            result2 = CodeExecutionResult.Failure
        }

        deleteDockerImage(imageName)

        deleteDockerFile(inputFilePath)

        return result2
    }

    fun start(
        userId: String,
        language: String,
        fileName: String,
        testcases: List<ProblemTestcase>
    ): Array<CodeExecutionResult> {

        val inputFilePath = when (language) {
            "C" -> "temp/c/$userId"
            "CPP" -> "temp/cpp/$userId"
            "Java" -> "temp/java/$userId"
            "Python" -> "temp/python/$userId"
            "Javascript" -> "temp/javascript/$userId"
            else -> throw IllegalArgumentException("Language can't be inferred")
        }

        createNecessaryTestcaseFiles(inputFilePath, testcases)

        createDockerFile(language, inputFilePath, fileName)

        val imageName = buildDockerImage(userId, inputFilePath)

        val results = executeForResults(imageName, testcases)

        deleteDockerImage(imageName)

        deleteDockerFile(inputFilePath)

        return results
    }

    private fun createNecessaryTestcaseFiles(inputFilePath: String, testcases: List<ProblemTestcase>) {
        testcases.forEachIndexed { index, it ->
            val testcaseFilePath = "$inputFilePath/testcases/input${index + 1}.txt"
            FileService.writeFile(testcaseFilePath, it.input)
        }
    }

    private fun executeForResults(imageName: String, testcases: List<ProblemTestcase>): Array<CodeExecutionResult> {
        val results = Array<CodeExecutionResult>(testcases.size) { CodeExecutionResult.Temporary }

        for (index in testcases.indices) {
            val result = executeDockerImage(imageName, index + 1)

            if (result.first) {
                results[index] = CodeExecutionResult.Success
            } else {
                results[index] = CodeExecutionResult.Failure
                break
            }
        }

        return results
    }

    private fun deleteDockerFile(filePath: String) {
        FileService.deleteFile("$filePath/Dockerfile")
    }

    private fun createDockerFile(language: String, inputFilePath: String, fileName: String) {
        val compiler = when (language) {
            "C" -> Compilers.FROM_DOCKER_IMAGE_FOR_C_COMPILER
            "CPP" -> Compilers.FROM_DOCKER_IMAGE_FOR_CPP_COMPILER
            "Java" -> Compilers.FROM_DOCKER_IMAGE_FOR_JAVA_COMPILER
            "Python" -> Compilers.FROM_DOCKER_IMAGE_FOR_PYTHON3_COMPILER
            "Javascript" -> Compilers.FROM_DOCKER_IMAGE_FOR_JAVASCRIPT_COMPILER
            else -> throw IllegalArgumentException("Language can't be inferred")
        }

        val dockerFileContent = """
            FROM $compiler
            ADD ./$fileName /tmp/${language.lowercase()}/$fileName
            COPY ./testcases /tmp/${language.lowercase()}/testcases
            ENV testcase_no=0
            WORKDIR /tmp/${language.lowercase()}/
            ${getNecessaryCommands(language, fileName)}
        """.trimIndent()

        FileService.writeFile(filePath = "$inputFilePath/Dockerfile", value = dockerFileContent)
    }

    private fun getNecessaryCommands(language: String, fileName: String): String {
        return when (language) {
            "C" -> """
                RUN gcc -o DockerWorld $fileName
                CMD ./DockerWorld $${"testcase_no"}
            """.trimIndent()

            "CPP" -> """
                RUN gcc -o DockerWorld -lstdc++ $fileName
                CMD ./DockerWorld $${"testcase_no"}
            """.trimIndent()

            "Java" -> """
                RUN javac $fileName
                CMD java ${fileName.substringBefore('.')} $${"testcase_no"}
            """.trimIndent()

            "Python" -> """
                CMD python3 $fileName $${"testcase_no"}
            """.trimIndent()

            "Javascript" -> """
                CMD node $fileName $${"testcase_no"}
            """.trimIndent()

            else -> throw IllegalArgumentException("Language can't be inferred")
        }
    }

    private fun buildDockerImage(userId: String, filePath: String): String {
        val imageName = "my-image-${userId}"
        val builder = ProcessBuilder("docker", "build", "-t", imageName, filePath)
        val process = builder.start()
        val isNotAborted = process.waitFor(10, TimeUnit.SECONDS)

        if (isNotAborted) {
            if (process.exitValue() == 0) {
                println("my-image Docker image created successfully")
            } else {
                process.debug()
            }
        } else {
            process.debug()
        }

        return imageName
    }

    private fun deleteDockerImage(imageName: String) {
        val builder = ProcessBuilder("docker", "rmi", "$imageName:latest")
        val process = builder.start();
        val isNotAborted = process.waitFor(10, TimeUnit.SECONDS)

        if (isNotAborted) {
            if (process.exitValue() == 0) {
                println("$imageName successfully deleted from docker registry")
            } else {
                process.debug()
            }
        }
    }

    private fun forceStopAndDeleteContainer(imageName: String) {
        val builder = ProcessBuilder("docker", "stop", "$imageName-container")
        val process = builder.start()
        process.waitFor()
    }

    private fun executeDockerImage(imageName: String, testcaseNo: Int): Pair<Boolean, String> {
        val builder = ProcessBuilder(
            "docker",
            "run",
            "--rm",
            "--name",
            "$imageName-container",
            "-e",
            "testcase_no=$testcaseNo",
            imageName
        )
        val process = builder.start()
        val isNotAborted = process.waitFor(5, TimeUnit.SECONDS)

        if (isNotAborted) {
            if (process.exitValue() == 0) {
                val output = process.getOutputData()
                if (output.contains("Wrong Answer:", true)) {
                    return Pair(false, output)
                }
                return Pair(true, output)
            } else {
                val error = process.getErrorData()
                return Pair(false, error)
            }
        } else {
            forceStopAndDeleteContainer(imageName)
            process.destroy()
            process.waitFor()
        }

        return Pair(false, "Time Limit Exceeded")
    }

    private fun Process.getOutputData(): String {
        val oStream = BufferedReader(InputStreamReader(inputStream))
        val oBuilder = StringBuilder()
        var line: String?
        while (oStream.readLine().also { line = it } != null) {
            oBuilder.append(line)
            oBuilder.append(System.getProperty("line.separator"))
        }
        return oBuilder.toString()
    }

    private fun Process.getErrorData(): String {
        val eStream = BufferedReader(InputStreamReader(errorStream))
        val eBuilder = StringBuilder()
        var line: String?
        while (eStream.readLine().also { line = it } != null) {
            eBuilder.append(line)
            eBuilder.append(System.getProperty("line.separator"))
        }
        return eBuilder.toString()
    }

    private fun Process.debug() {
        val output = getOutputData()
        println("Output: \n$output")

        val error = getErrorData()
        println("Error: \n$error")
    }
}