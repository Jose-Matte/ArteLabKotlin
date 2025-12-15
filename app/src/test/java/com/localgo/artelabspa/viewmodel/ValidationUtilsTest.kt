package com.localgo.artelabspa.utils

import org.junit.Assert.*
import org.junit.Test

class ValidationUtilsTest {

    @Test
    fun invalid_emails_return_false () {
        assertFalse(ValidationUtils.isValidEmail("testgmail.com"))
        assertFalse(ValidationUtils.isValidEmail("user@"))
        assertFalse(ValidationUtils.isValidEmail("@example.com"))
        assertFalse(ValidationUtils.isValidEmail("hola"))
        assertFalse(ValidationUtils.isValidEmail(""))
    }
}
