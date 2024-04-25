package com.techullurgy.leetcodeclone

import com.techullurgy.leetcodeclone.utils.TestUtil
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
class AuthenticationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun canLoginSuccess() {

        val json = "{ \"username\":\"irsath\", \"password\":\"irsath@123\" }"

        val builder = MockMvcRequestBuilders.post("/auth/login")
            .servletPath("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json)
            .accept(MediaType.APPLICATION_JSON)

        mockMvc.perform(builder)
            .andExpect(MockMvcResultMatchers.jsonPath("$.token").isNotEmpty)
            .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(null))
            .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
    }

    @Test
    fun canLoginFailedWithInvalidCredentials() {
        val json = "{ \"username\":\"irsath\", \"password\":\"irsath@1233\" }"

        val builder = MockMvcRequestBuilders.post("/auth/login")
            .servletPath("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json)

        mockMvc.perform(builder)
            .andExpect(MockMvcResultMatchers.status().isForbidden)
    }

    @Test
    fun canAccessAuthorizedEndpointWithoutTokenFailed() {
        val builder = MockMvcRequestBuilders.get("/code/dd")
            .servletPath("/code/dd")

        mockMvc.perform(builder)
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }

    @Test
    fun canAccessAuthorizedEndpointWithValidTokenSuccess() {
        val loggedInToken = TestUtil.getTokenByLogin(mockMvc)

        val builder = MockMvcRequestBuilders.get("/code/dd")
            .servletPath("/code/dd")
            .header("Authorization", "Bearer $loggedInToken")

        mockMvc.perform(builder)
            .andExpect(MockMvcResultMatchers.status().isOk)
    }
}