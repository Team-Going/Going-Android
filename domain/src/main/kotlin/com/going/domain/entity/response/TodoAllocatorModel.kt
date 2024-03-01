package com.going.domain.entity.response

data class TodoAllocatorModel(
    val participantId: Long,
    val name: String,
    var isAllocated: Boolean,
    val isOwner: Boolean
)