package com.nevermore.mpc.components.models

/**
 * @author nevermore
 * @since
 */
data class Tenant(
    val tenantId: String,
    val tenantName: String,
    val locked: Int,
)
