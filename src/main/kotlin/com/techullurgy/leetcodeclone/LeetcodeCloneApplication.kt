package com.techullurgy.leetcodeclone

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LeetcodeCloneApplication {

//	@Bean
//	fun commandLineRunner(repository: ProblemRepository): CommandLineRunner {
//		return CommandLineRunner {
//			problems.forEach { problem ->
//				repository.save(problem)
//			}
//			println("Problems Saved")
//		}
//	}
}

fun main(args: Array<String>) {
    runApplication<LeetcodeCloneApplication>(*args)
//	val codeExecutionService = CodeExecutionService()
//	val results = codeExecutionService.start(
//		userId = "1uide",
//		language = "Java",
//		fileName = "Hello.java",
//		testcases = listOf(
//			ProblemTestcase(id = 1, input = "2", output = ""),
//			ProblemTestcase(id = 2, input = "4", output = ""),
//			ProblemTestcase(id = 3, input = "5", output = ""),
//			ProblemTestcase(id = 4, input = "6", output = ""),
//		)
//	)
//
//	println("Results")
//	results.forEach {
//		println(it.toString())
//	}
}


//private val problems = listOf(
//	Problem(
//		title = "Two Sum",
//		problemNo = 1,
//		difficulty = ProblemDifficulty.EASY,
//		likes = 30,
//		dislikes = 19,
//		description = """
//			<p class='mt-3'>
//				Given an array of integers <code>nums</code> and an integer <code>target</code>, return <em>indices of the two numbers such that they add up to</em> <code>target</code>. </br> </br>
//				You may assume that each input would have <strong>exactly one solution</strong>, and you may not use the same element twice. </br> </br>
//				You can return the answer in any order.
//			</p>
//		""".trimIndent(),
//		constraints = listOf("n == str.length", "0 <= n <= 100"),
//		examples = listOf(
//			ProblemExample(input = "[1,2,3,4,5,6]", output = "[23]", explanation = "Ex 1 explanation"),
//			ProblemExample(input = "[1,2,3,4,5,6,7]", output = "[24]", explanation = "Ex 2 explanation"),
//			ProblemExample(input = "[1,2,3,4,5,6,7,8]", output = "[25]", explanation = "Ex 3 explanation"),
//		),
//		testcases = listOf(
//			ProblemTestcase(id = 1, input = "[1,2,3,4,5,6]", output = "[23]"),
//			ProblemTestcase(id = 2, input = "[1,2,3,4,5,6,7]", output = "[24]"),
//		),
//		snippets = listOf(
//			CodeSnippet.CSnippet("void main() {\nprintf(\"Hello World!\");\n}"),
//			CodeSnippet.CPPSnippet("int main() {\nprintf(\"Hello World!\");\n}"),
//			CodeSnippet.JavaSnippet("public class Solution {\n\tpublic static void main(String[] args) {\n\t\tSystem.out.println(\"Hello World\");\t\t\n}\t\n}")
//		),
//	),
//	Problem(
//		title = "Two Sum 2",
//		problemNo = 2,
//		difficulty = ProblemDifficulty.EASY,
//		likes = 30,
//		dislikes = 19,
//		description = "Two sum description",
//		constraints = listOf("n == str.length", "0 <= n <= 100"),
//		examples = listOf(
//			ProblemExample(input = "[1,2,3,4,5,6]", output = "[23]", explanation = "Ex 1 explanation"),
//			ProblemExample(input = "[1,2,3,4,5,6,7]", output = "[24]", explanation = "Ex 2 explanation"),
//			ProblemExample(input = "[1,2,3,4,5,6,7,8]", output = "[25]", explanation = "Ex 3 explanation"),
//		),
//		testcases = listOf(
//			ProblemTestcase(id = 1, input = "[1,2,3,4,5,6]", output = "[23]"),
//			ProblemTestcase(id = 2, input = "[1,2,3,4,5,6,7]", output = "[24]"),
//		),
//		snippets = listOf(
//			CodeSnippet.CSnippet("void main() {\nprintf(\"Hello World!\");\n}"),
//			CodeSnippet.CPPSnippet("int main() {\nprintf(\"Hello World!\");\n}"),
//			CodeSnippet.JavaSnippet("public class Solution {\n\tpublic static void main(String[] args) {\n\t\tSystem.out.println(\"Hello World\");\t\t\n}\t\n}")
//		)
//	),
//	Problem(
//		title = "Two Sum 3",
//		problemNo = 3,
//		difficulty = ProblemDifficulty.EASY,
//		likes = 30,
//		dislikes = 19,
//		description = "Two sum description",
//		constraints = listOf("n == str.length", "0 <= n <= 100"),
//		examples = listOf(
//			ProblemExample(input = "[1,2,3,4,5,6]", output = "[23]", explanation = "Ex 1 explanation"),
//			ProblemExample(input = "[1,2,3,4,5,6,7]", output = "[24]", explanation = "Ex 2 explanation"),
//			ProblemExample(input = "[1,2,3,4,5,6,7,8]", output = "[25]", explanation = "Ex 3 explanation"),
//		),
//		testcases = listOf(
//			ProblemTestcase(id = 1, input = "[1,2,3,4,5,6]", output = "[23]"),
//			ProblemTestcase(id = 2, input = "[1,2,3,4,5,6,7]", output = "[24]"),
//		),
//		snippets = listOf(
//			CodeSnippet.CSnippet("void main() {\nprintf(\"Hello World!\");\n}"),
//			CodeSnippet.CPPSnippet("int main() {\nprintf(\"Hello World!\");\n}"),
//			CodeSnippet.JavaSnippet("public class Solution {\n\tpublic static void main(String[] args) {\n\t\tSystem.out.println(\"Hello World\");\t\t\n}\t\n}")
//		)
//	),
//	Problem(
//		title = "Two Sum 4",
//		problemNo = 4,
//		difficulty = ProblemDifficulty.EASY,
//		likes = 30,
//		dislikes = 19,
//		description = "Two sum description",
//		constraints = listOf("n == str.length", "0 <= n <= 100"),
//		examples = listOf(
//			ProblemExample(input = "[1,2,3,4,5,6]", output = "[23]", explanation = "Ex 1 explanation"),
//			ProblemExample(input = "[1,2,3,4,5,6,7]", output = "[24]", explanation = "Ex 2 explanation"),
//			ProblemExample(input = "[1,2,3,4,5,6,7,8]", output = "[25]", explanation = "Ex 3 explanation"),
//		),
//		testcases = listOf(
//			ProblemTestcase(id = 1, input = "[1,2,3,4,5,6]", output = "[23]"),
//			ProblemTestcase(id = 2, input = "[1,2,3,4,5,6,7]", output = "[24]"),
//		),
//		snippets = listOf(
//			CodeSnippet.CSnippet("void main() {\nprintf(\"Hello World!\");\n}"),
//			CodeSnippet.CPPSnippet("int main() {\nprintf(\"Hello World!\");\n}"),
//			CodeSnippet.JavaSnippet("public class Solution {\n\tpublic static void main(String[] args) {\n\t\tSystem.out.println(\"Hello World\");\t\t\n}\t\n}")
//		)
//	),
//	Problem(
//		title = "Two Sum 5",
//		problemNo = 5,
//		difficulty = ProblemDifficulty.EASY,
//		likes = 30,
//		dislikes = 19,
//		description = "Two sum description",
//		constraints = listOf("n == str.length", "0 <= n <= 100"),
//		examples = listOf(
//			ProblemExample(input = "[1,2,3,4,5,6]", output = "[23]", explanation = "Ex 1 explanation"),
//			ProblemExample(input = "[1,2,3,4,5,6,7]", output = "[24]", explanation = "Ex 2 explanation"),
//			ProblemExample(input = "[1,2,3,4,5,6,7,8]", output = "[25]", explanation = "Ex 3 explanation"),
//		),
//		testcases = listOf(
//			ProblemTestcase(id = 1, input = "[1,2,3,4,5,6]", output = "[23]"),
//			ProblemTestcase(id = 2, input = "[1,2,3,4,5,6,7]", output = "[24]"),
//		),
//		snippets = listOf(
//			CodeSnippet.CSnippet("void main() {\nprintf(\"Hello World!\");\n}"),
//			CodeSnippet.CPPSnippet("int main() {\nprintf(\"Hello World!\");\n}"),
//			CodeSnippet.JavaSnippet("public class Solution {\n\tpublic static void main(String[] args) {\n\t\tSystem.out.println(\"Hello World\");\t\t\n}\t\n}")
//		)
//	),
//	Problem(
//		title = "Two Sum 6",
//		problemNo = 6,
//		difficulty = ProblemDifficulty.EASY,
//		likes = 30,
//		dislikes = 19,
//		description = "Two sum description",
//		constraints = listOf("n == str.length", "0 <= n <= 100"),
//		examples = listOf(
//			ProblemExample(input = "[1,2,3,4,5,6]", output = "[23]", explanation = "Ex 1 explanation"),
//			ProblemExample(input = "[1,2,3,4,5,6,7]", output = "[24]", explanation = "Ex 2 explanation"),
//			ProblemExample(input = "[1,2,3,4,5,6,7,8]", output = "[25]", explanation = "Ex 3 explanation"),
//		),
//		testcases = listOf(
//			ProblemTestcase(id = 1, input = "[1,2,3,4,5,6]", output = "[23]"),
//			ProblemTestcase(id = 2, input = "[1,2,3,4,5,6,7]", output = "[24]"),
//		),
//		snippets = listOf(
//			CodeSnippet.CSnippet("void main() {\nprintf(\"Hello World!\");\n}"),
//			CodeSnippet.CPPSnippet("int main() {\nprintf(\"Hello World!\");\n}"),
//			CodeSnippet.JavaSnippet("public class Solution {\n\tpublic static void main(String[] args) {\n\t\tSystem.out.println(\"Hello World\");\t\t\n}\t\n}")
//		)
//	),
//	Problem(
//		title = "Two Sum 7",
//		problemNo = 7,
//		difficulty = ProblemDifficulty.EASY,
//		likes = 30,
//		dislikes = 19,
//		description = "Two sum description",
//		constraints = listOf("n == str.length", "0 <= n <= 100"),
//		examples = listOf(
//			ProblemExample(input = "[1,2,3,4,5,6]", output = "[23]", explanation = "Ex 1 explanation"),
//			ProblemExample(input = "[1,2,3,4,5,6,7]", output = "[24]", explanation = "Ex 2 explanation"),
//			ProblemExample(input = "[1,2,3,4,5,6,7,8]", output = "[25]", explanation = "Ex 3 explanation"),
//		),
//		testcases = listOf(
//			ProblemTestcase(id = 1, input = "[1,2,3,4,5,6]", output = "[23]"),
//			ProblemTestcase(id = 2, input = "[1,2,3,4,5,6,7]", output = "[24]"),
//		),
//		snippets = listOf(
//			CodeSnippet.CSnippet("void main() {\nprintf(\"Hello World!\");\n}"),
//			CodeSnippet.CPPSnippet("int main() {\nprintf(\"Hello World!\");\n}"),
//			CodeSnippet.JavaSnippet("public class Solution {\n\tpublic static void main(String[] args) {\n\t\tSystem.out.println(\"Hello World\");\t\t\n}\t\n}")
//		)
//	),
//	Problem(
//		title = "Two Sum 8",
//		problemNo = 8,
//		difficulty = ProblemDifficulty.EASY,
//		likes = 30,
//		dislikes = 19,
//		description = "Two sum description",
//		constraints = listOf("n == str.length", "0 <= n <= 100"),
//		examples = listOf(
//			ProblemExample(input = "[1,2,3,4,5,6]", output = "[23]", explanation = "Ex 1 explanation"),
//			ProblemExample(input = "[1,2,3,4,5,6,7]", output = "[24]", explanation = "Ex 2 explanation"),
//			ProblemExample(input = "[1,2,3,4,5,6,7,8]", output = "[25]", explanation = "Ex 3 explanation"),
//		),
//		testcases = listOf(
//			ProblemTestcase(id = 1, input = "[1,2,3,4,5,6]", output = "[23]"),
//			ProblemTestcase(id = 2, input = "[1,2,3,4,5,6,7]", output = "[24]"),
//		),
//		snippets = listOf(
//			CodeSnippet.CSnippet("void main() {\nprintf(\"Hello World!\");\n}"),
//			CodeSnippet.CPPSnippet("int main() {\nprintf(\"Hello World!\");\n}"),
//			CodeSnippet.JavaSnippet("public class Solution {\n\tpublic static void main(String[] args) {\n\t\tSystem.out.println(\"Hello World\");\t\t\n}\t\n}")
//		)
//	)
//)