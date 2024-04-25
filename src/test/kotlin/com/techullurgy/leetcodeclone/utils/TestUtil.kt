package com.techullurgy.leetcodeclone.utils

import org.json.JSONObject
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

class TestUtil {
    companion object {
        fun getTokenByLogin(
            mockMvc: MockMvc,
            username: String = "irsath",
            password: String = "irsath@123"
        ): String {
            val json = "{ \"username\":\"$username\", \"password\":\"$password\" }"

            val builder = MockMvcRequestBuilders.post("/auth/login")
                .servletPath("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)

            val mvcResult = mockMvc.perform(builder)
                .andReturn()

            val content = mvcResult.response.contentAsString
            return JSONObject(content).getString("token")
        }
    }
}