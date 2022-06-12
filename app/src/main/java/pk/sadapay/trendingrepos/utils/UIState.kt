package pk.sadapay.trendingrepos.utils

sealed class UIState {
    object Loading : UIState()
    object Content : UIState()
    data class Error(val error: String? = null) : UIState()
}