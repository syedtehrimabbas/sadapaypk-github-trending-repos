package pk.sadapay.trendingrepos.utils

sealed class UIState {
    object Loading : UIState()
    data class Error(val error: String? = null) : UIState()
}