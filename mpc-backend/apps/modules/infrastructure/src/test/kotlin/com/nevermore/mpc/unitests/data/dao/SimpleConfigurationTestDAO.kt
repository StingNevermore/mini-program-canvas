package com.nevermore.mpc.unitests.data.dao

import org.springframework.stereotype.Repository

/**
 * @author nevermore
 * @since
 */
@Repository
interface SimpleConfigurationTestDAO {

    fun insert()

    fun select(): Int
}
