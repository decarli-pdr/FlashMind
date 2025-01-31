package br.com.jogosecm.deolhonascores.telas

import android.content.Context
import androidx.compose.ui.graphics.Paint
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class JogoUiState(
    val imagem: Paint = Paint(),
    val tempoMax: String = "5",
    val estadoBotao: Boolean = true,
    val palavraColorida: Boolean = false,
    val rodadas: String = "10",
    val countdownValue: Int = 0, // Add a field to hold the countdown value
    val mostraCor: Boolean = false, // Add a field to hold the countdown value
    val duracaoCor: String = "2" // Add a field to hold the countdown value
)

class AppViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(JogoUiState())
    val uiState: StateFlow<JogoUiState> = _uiState.asStateFlow()

    fun defineTempoMax(tempodigitado: String) {
        _uiState.value = _uiState.value.copy(tempoMax = tempodigitado)
    }

    fun mudaBotao(estadoDesejado: Boolean) {
        _uiState.value = _uiState.value.copy(estadoBotao = estadoDesejado)
    }

    fun defineRodadas(rodadadigitada: String) {
        _uiState.value = _uiState.value.copy(rodadas = rodadadigitada)
    }

    fun updateCountdownValue(value: Int) {
        _uiState.value = _uiState.value.copy(countdownValue = value)
    }

    fun mudaMostraCor(value: Boolean) {
        _uiState.value = _uiState.value.copy(mostraCor = value)
    }

    fun mudaSwitchCor(value: Boolean) {
        _uiState.value = _uiState.value.copy(palavraColorida = value)
    }

    fun mudaDuracaoCor(value: String) {
        _uiState.value = _uiState.value.copy(duracaoCor = value)
    }

}

suspend fun ativar(context: Context) {
    context.dataStore.edit { settings ->
        settings[ativado] = true
    }
}