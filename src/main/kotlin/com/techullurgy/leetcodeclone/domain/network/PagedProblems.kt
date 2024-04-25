package com.techullurgy.leetcodeclone.domain.network

import com.techullurgy.leetcodeclone.domain.db.Problem

data class PagedProblems(
    val problems: List<Problem>,
    val currentPage: Int,
    val totalPages: Int
)
