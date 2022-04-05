package com.albertomagalhaes.lordoftheringsinfo.data.remote.dto

open class ResponseDTO<T>(
    val total: Int,
    val limit: Int,
    val offset: Int,
    val page: Int,
    val pages: Int,
    val docs: List<T>
)