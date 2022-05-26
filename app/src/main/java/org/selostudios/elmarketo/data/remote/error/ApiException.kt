package org.selostudios.elmarketo.data.remote.error

class ApiException: Exception {
    var error: ApiError? = null
        private set

    constructor(error: ApiError?) {
        this.error = error
    }

    constructor(cause: Throwable) : super(cause)
    constructor(message: String?, cause: Throwable?) : super(message, cause)

    override val message: String
        get() = error?.toString() ?: super.message ?: "No message"
}