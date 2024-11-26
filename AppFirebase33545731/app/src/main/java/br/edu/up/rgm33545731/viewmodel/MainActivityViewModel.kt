package br.edu.up.rgm33824215.viewmodel

import androidx.lifecycle.ViewModel
import br.edu.up.rgm33824215.Filters

/**
 * ViewModel for [com.google.firebase.example.fireeats.MainActivity].
 */

class MainActivityViewModel : ViewModel() {

    var isSigningIn: Boolean = false
    var filters: Filters = Filters.default
}
