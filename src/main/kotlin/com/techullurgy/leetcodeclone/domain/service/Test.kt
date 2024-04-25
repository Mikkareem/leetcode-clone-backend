package com.techullurgy.leetcodeclone.domain.service

class Test {

    fun test() {
        val user = "2jyd3de4fde"
        val content =
            "public class Solution {\n\tpublic static void main(String[] args){\n\t\tSystem.out.println(\"Hello World\");\n\t}\n}"

        FileService.writeFile("temp/java/$user/Hello.java", content)
        println("Done")
    }
}

fun main() {
    Test().test()
}