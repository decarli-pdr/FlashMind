package br.com.jogosecm.flashmind

import android.content.Context
import androidx.compose.ui.graphics.Paint
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import br.com.jogosecm.flashmind.telas.ativado
import br.com.jogosecm.flashmind.telas.dataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class JogoUiState(
    val imagem: Paint = Paint(),
    val tempoMax: String = "5",
    val estadoBotao: Boolean = true,
    val maoDireitaAtivada: Boolean = true,
    val maoEsquerdaAtivada: Boolean = true,
    val rodadas: String = "10",
    val countdownValue: Int = 0, // Add a field to hold the countdown value
    val mostraImagem: Boolean = false, // Add a field to hold the countdown value
    val duracaoImagem: String = "4" // Add a field to hold the countdown value
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

    fun mudaMostraImagem(value: Boolean) {
        _uiState.value = _uiState.value.copy(mostraImagem = value)
    }

    fun mudaMaoEsquerda(value: Boolean) {
        _uiState.value = _uiState.value.copy(maoEsquerdaAtivada = value)
    }

    fun mudaMaoDireita(value: Boolean) {
        _uiState.value = _uiState.value.copy(maoDireitaAtivada = value)
    }

    fun mudaDuracaoCor(value: String) {
        _uiState.value = _uiState.value.copy(duracaoImagem = value)
    }

}

suspend fun ativar(context: Context) {
    context.dataStore.edit { settings ->
        settings[ativado] = true
    }
}