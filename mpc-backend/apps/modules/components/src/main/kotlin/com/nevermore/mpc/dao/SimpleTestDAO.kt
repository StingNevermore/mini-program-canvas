package com.nevermore.mpc.dao

import org.springframework.stereotype.Repository

/**
 * @author nevermore
 * @since
 */
@Repository
interface SimpleTestDAO {
    fun select(): Int
}
