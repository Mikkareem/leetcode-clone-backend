package com.techullurgy.leetcodeclone

import com.techullurgy.leetcodeclone.utils.TestUtil
import org.json.JSONObject
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class AlgorithmsControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Value("\${app.jwt.expirationTime}")
    private lateinit var expirationTime: String

    @Test
    fun runCodeTest() {
        val code = """
            public class Hello {
                public static void main(String[] args) {
                    System.out.println("Guruvayoorappa");
                }
            }
        """.trimIndent()

        val codeRequest = JSONObject()
        codeRequest.put("code", code)
        codeRequest.put("problemId", "123hje3")

        val loggedInToken = TestUtil.getTokenByLogin(mockMvc)

        val builder = MockMvcRequestBuilders.post("/code/run")
            .servletPath("/code/run")
            .contentType(MediaType.APPLICATION_JSON)
            .content(codeRequest.toString())
            .header("Authorization", "Bearer $loggedInToken")

        mockMvc.perform(builder)
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().string("File Created"))
    }

    @Test
    fun runCodeTestWithExpiredToken() {

        val code = """
            public class Hello {
                public static void main(String[] args) {
                    System.out.println("Guruvayoorappa");
                }
            }
        """.trimIndent()

        val codeRequest = JSONObject()
        codeRequest.put("code", code)
        codeRequest.put("problemId", "123hje3")

        val loggedInToken = TestUtil.getTokenByLogin(mockMvc)

        val afterExpirationIn = expirationTime.toLong() + 500
        // After expiration time
        Thread.sleep(afterExpirationIn)

        val builder = MockMvcRequestBuilders.post("/code/run")
            .servletPath("/code/run")
            .contentType(MediaType.APPLICATION_JSON)
            .content(codeRequest.toString())
            .header("Authorization", "Bearer $loggedInToken")

        mockMvc.perform(builder)
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }

    @Test
    fun runCodeTestWithOutAuthorization() {

        val code = """
            public class Hello {
                public static void main(String[] args) {
                    System.out.println("Guruvayoorappa");
                }
            }
        """.trimIndent()

        val codeRequest = JSONObject()
        codeRequest.put("code", code)
        codeRequest.put("problemId", "123hje3")

        val builder = MockMvcRequestBuilders.post("/code/run")
            .servletPath("/code/run")
            .contentType(MediaType.APPLICATION_JSON)
            .content(codeRequest.toString())

        mockMvc.perform(builder)
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }

    @Test
    fun runCodeTestWithInvalidAuthorization() {

        val code = """
            public class Hello {
                public static void main(String[] args) {
                    System.out.println("Guruvayoorappa");
                }
            }
        """.trimIndent()

        val codeRequest = JSONObject()
        codeRequest.put("code", code)
        codeRequest.put("problemId", "123hje3")

        val loggedInToken = TestUtil.getTokenByLogin(mockMvc)

        val builder = MockMvcRequestBuilders.post("/code/run")
            .servletPath("/code/run")
            .contentType(MediaType.APPLICATION_JSON)
            .content(codeRequest.toString())
            .header("Authorization", "Bearer ${loggedInToken.removeRange(1, 4)}")

        mockMvc.perform(builder)
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }
}