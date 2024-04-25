package com.techullurgy.leetcodeclone

import com.techullurgy.leetcodeclone.utils.TestUtil
import org.json.JSONArray
import org.json.JSONObject
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class AdminPlaygroundControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun runTest() {
        val code = """
            public class Hello {
                public static void main(String[] args) {
                    System.out.println("Guruvayoorappa");
                }
            }
        """.trimIndent()

        val fileContent = JSONObject()
        fileContent.put("c", "cc")
        fileContent.put("cpp", "cppcpp")
        fileContent.put("java", "javajava")
        fileContent.put("python", "pythonpython")
        fileContent.put("javascript", "javascriptjavascript")

        val codeRequest = JSONObject()
        codeRequest.put("title", "Replay 1")
        codeRequest.put("description", "2er")
        codeRequest.put("difficulty", "EASY")
        codeRequest.put("testcases", JSONArray())
        codeRequest.put("constraints", JSONArray())
        codeRequest.put("examples", JSONArray())
        codeRequest.put("snippets", fileContent)
        codeRequest.put("fileContent", fileContent)
        codeRequest.put("replaceableStr", fileContent)

        val loggedInToken = TestUtil.getTokenByLogin(mockMvc)

        val builder = MockMvcRequestBuilders.post("/admin/problems/new")
            .servletPath("/admin/problems/new")
            .contentType(MediaType.APPLICATION_JSON)
            .content(codeRequest.toString())
            .header("Authorization", "Bearer $loggedInToken")

        val problemId = mockMvc.perform(builder)
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn().response.contentAsString.split(" ").last()

        codeRequest.put("difficulty", "MEDIUM")

        val secondBuilder = MockMvcRequestBuilders.put("/admin/problems/$problemId")
            .servletPath("/admin/problems/$problemId")
            .contentType(MediaType.APPLICATION_JSON)
            .content(codeRequest.toString())
            .header("Authorization", "Bearer $loggedInToken")

        mockMvc.perform(secondBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().string("Successfully Problem is updated"))
    }
}