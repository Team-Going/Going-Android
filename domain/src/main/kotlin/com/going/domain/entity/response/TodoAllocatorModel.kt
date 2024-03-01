package com.going.domain.entity.response

data class TodoAllocatorModel(
    val participantId: Long,
    val name: String,
    val isAllocated: Boolean,
    val isOwner: Boolean
)