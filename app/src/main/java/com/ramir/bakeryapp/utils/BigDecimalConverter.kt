package com.ramir.bakeryapp.utils

import androidx.room.TypeConverter
import java.math.BigDecimal

class BigDecimalConverter {
    @TypeConverter
    fun fromBigDecimal(price: BigDecimal?): String?{
        return price?.toPlainString()
    }

    @TypeConverter
    fun toBigDecimal(priceString: String?): BigDecimal?{
        return priceString?.let { BigDecimal(it) }
    }
}

/*
*
* class CentConverter {
    // Convert BigDecimal (e.g., 12.34) to Long (1234)
    @TypeConverter
    fun fromBigDecimal(price: BigDecimal?): Long? {
        // Multiply by 100 and convert to long
        return price?.movePointRight(2)?.toLong()
    }

    // Convert Long (1234) back to BigDecimal (12.34)
    @TypeConverter
    fun toBigDecimal(priceCents: Long?): BigDecimal? {
        // Convert to BigDecimal and move point left by 2
        return priceCents?.let { BigDecimal.valueOf(it, 2) }
    }
}*/