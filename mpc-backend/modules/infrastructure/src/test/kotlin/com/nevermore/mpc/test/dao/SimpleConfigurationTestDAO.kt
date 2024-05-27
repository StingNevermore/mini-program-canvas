package com.nevermore.mpc.test.dao

import org.springframework.stereotype.Repository

/**
 * @author nevermore
 * @since
 */
@Repository
interface SimpleConfigurationTestDAO {
    fun select(): Int
}
