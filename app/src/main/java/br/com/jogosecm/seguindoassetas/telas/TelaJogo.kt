package br.com.jogosecm.seguindoassetas.telas

import android.app.Activity
import android.content.Context
import android.view.WindowManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.com.jogosecm.seguindoassetas.R
import kotlinx.coroutines.delay


/**
 * TelaJogo é uma função Composable.
 *
 * Esta função Composable exibe a tela do jogo, incluindo o cronômetro de contagem regressiva,
 * o indicador de direção da seta e a mensagem de fim de jogo. Ela gerencia o fluxo do jogo,
 * atualizando a interface do usuário com base no estado do jogo fornecido pelo `AppViewModel`.
 *
 * @param modifier O modificador a ser aplicado ao layout.
 * @param viewModelAtual A instância de `AppViewModel` que contém o estado e a lógica do jogo.
 * @param navHostController O `NavHostController` para navegação dentro do aplicativo.
 * @param contexto O `Context` do Android, tipicamente o contexto da Activity.
 *
 * Funcionalidades:
 * - **Mantém a tela ligada:** Impede que a tela desligue durante a jogatina.
 * - **Exibe a Contagem Regressiva:** Mostra um cronômetro de contagem regressiva baseado em `appUiState.countdownValue`.
 * - **Exibe a Seta:** Exibe aleatoriamente uma seta da `listaDeSeta` por uma curta duração.
 * - **Gerencia o Fim de Jogo:** Exibe "Fim de jogo" quando a contagem regressiva atinge 0, com um botão para retornar.
 * - **Gerencia Rodadas e Tempo de Jogo:** Itera pelas rodadas do jogo e pelo tempo especificado em `appUiState`.
 * - **Atualiza o Estado do Jogo:** Atualiza o estado do jogo no `AppViewModel`, influenciando a UI.
 */
@Composable
fun TelaJogo(
    modifier: Modifier,
    viewModelAtual: AppViewModel,
//    mudouOTempoMax: (String) -> Unit,
//    mudaramRodadas: (String) -> Unit,
    navHostController: NavHostController,
    contexto: Context,
//    mudouTextoValido: (Boolean) -> Unit
) {
    val appUiState by viewModelAtual.uiState.collectAsState()

    val listaDeSeta = mapOf(
        true to R.drawable.direita,
        false to R.drawable.esquerda,
        //Icons.AutoMirrored.Rounded.ArrowBack
    )
    var contagemSeta = remember { 1 }
    var ultimaSeta = remember { true }

    DisposableEffect(Unit) {
        val activity = contexto as? Activity
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        onDispose {
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }
    var preparou by remember { mutableStateOf(false) }
    var progresso by remember { mutableFloatStateOf(0f) }

    Surface(modifier = modifier.fillMaxSize()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center, content = {
            //val coroutineScope = rememberCoroutineScope()

            LaunchedEffect(key1 = appUiState.rodadas, key2 = appUiState.tempoMax) {
                preparou = false
                for (contador in 0..500) {
                    delay(10)
                    progresso = contador / 500f
                }
                preparou = true
                repeat(appUiState.rodadas.toInt()) {
                    for (i in appUiState.tempoMax.toInt() downTo 1) {
                        viewModelAtual.updateCountdownValue(i) // Update the UI state with the current countdown value
                        delay(350)
                    }
                    viewModelAtual.mudaMostraSeta(true)
                    delay(1700)
                    viewModelAtual.mudaMostraSeta(false)
                }
                viewModelAtual.updateCountdownValue(0)
            }
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center, content = {
                if (!preparou) {
                    Column(
                        modifier = modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("Prepare-se", fontSize = 80.sp, modifier = modifier.padding(20.dp))
                        LinearProgressIndicator(
                            progress = { progresso }, modifier = modifier
                        )
                    }
                } else if (appUiState.countdownValue == 0) {
                    Column(
                        modifier = modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("Fim de jogo", fontSize = 70.sp)
                        Button(
                            modifier = modifier.padding(10.dp),
                            onClick = { navHostController.navigateUp() }) {
                            Text("Voltar", fontSize = 35.sp)
                        }
                    }
                } else if (appUiState.mostraSeta) {
                    val seta = listaDeSeta.keys.random()
                    if (seta == ultimaSeta) {
                        contagemSeta++
                    } else {
                        contagemSeta = 1
                    }
                    if (contagemSeta == 4) {
                        Icon(
                            painter = painterResource(id = listaDeSeta[!seta]!!),
                            null,
                            modifier.size(500.dp)
                        )
                        ultimaSeta = !seta
                    } else {
                        Icon(
                            painter = painterResource(id = listaDeSeta[seta]!!),
                            null,
                            modifier.size(500.dp)
                        )
                        ultimaSeta = seta
                    }

                } else {

                    Text(
                        appUiState.countdownValue.toString(),
                        fontSize = 300.sp
                    ) // Display the countdown value
                }
            })
        })

    }


}


@Preview(
    showSystemUi = true, showBackground = true, device = "spec:parent=pixel_5,orientation=landscape"
)
@Composable
fun TelaJogoPreview() {
    TelaJogo(
        modifier = Modifier,
        viewModelAtual = AppViewModel(),
        navHostController = NavHostController(LocalContext.current),
        contexto = LocalContext.current
    )
}