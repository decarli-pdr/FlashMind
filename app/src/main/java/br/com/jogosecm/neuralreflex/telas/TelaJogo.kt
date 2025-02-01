package br.com.jogosecm.neuralreflex.telas

import android.app.Activity
import android.content.Context
import android.view.WindowManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
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

    val listaDeCores = mapOf(
        1 to Color(red = 0, green = 252, blue = 4, alpha = 255),
        2 to Color(red = 255, green = 11, blue = 11, alpha = 255),
        3 to Color(red = 255, green = 243, blue = 0, alpha = 255),
        4 to Color(red = 0, green = 163, blue = 255)
        //Icons.AutoMirrored.Rounded.ArrowBack
    )

    val listaDeNomes = listOf(
        "Verde",
        "Amarelo",
        "Azul",
        "Vermelho"
    )
    var ultimaCor = remember { 1 }
    var ultimaPalavra = remember { "Azul" }

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


            //Lógica do negócio
            LaunchedEffect(key1 = appUiState.rodadas, key2 = appUiState.tempoMax) {
                preparou = false
                for (contador in 0..500) {
                    delay(10)
                    progresso = contador / 500f
                }
                preparou = true
                repeat(appUiState.rodadas.toInt()) {
                    for (i in appUiState.tempoMax.toInt() downTo 0) {
                        viewModelAtual.updateCountdownValue(i) // Update the UI state with the current countdown value
                        delay(350)
                    }
                    viewModelAtual.mudaMostraCor(true)
                    delay(appUiState.duracaoCor.toLong() * 1000L)
                    viewModelAtual.mudaMostraCor(false)
                }
                viewModelAtual.updateCountdownValue(-1)
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
                } else if (appUiState.countdownValue == -1) {
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
                } else if (appUiState.mostraCor) {
                    //Aqui inicia a parte das setas/cores sorteadas
                    var cor = listaDeCores.keys.random()
                    while (cor == ultimaCor) {
                        cor = listaDeCores.keys.random()
                    }
                    if (!appUiState.palavraColorida) {
                        Surface(
                            modifier.fillMaxSize(),
                            color = listaDeCores[cor]!!
                        ) {}
                    } else {
                        Column(
                            modifier = modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {

                            val configuration = LocalConfiguration.current
                            val screenWidthDp = configuration.screenWidthDp.toFloat()

                            // Calculate a scaling factor based on the screen size
                            // You can adjust the formula to fit your needs
                            val scaleFactor =
                                (screenWidthDp) / 5f // Example formula

                            // Get the density to convert dp to sp
                            val density = LocalDensity.current

                            var palavraSorteada = listaDeNomes.random()
                            while (palavraSorteada == ultimaPalavra) {
                                palavraSorteada = listaDeNomes.random()
                            }

                            Text(
                                modifier = modifier,
                                text = palavraSorteada,
                                //fontSize = 200.sp,
                                style = TextStyle(
                                    fontSize = with(density) { (scaleFactor).sp }, // Calculate font size in sp,
                                    color = listaDeCores[cor]!!,
                                    fontWeight = FontWeight.ExtraBold,
                                    shadow = Shadow(
                                        color = Color.Black,
                                        blurRadius = 15f
                                    )
                                )
                            )
                            ultimaPalavra = palavraSorteada
                        }


                    }
                    ultimaCor = cor
                } else {
                    if (appUiState.countdownValue == 0) {
                        Text(
                            "VAI!",
                            fontSize = 300.sp
                        )
                    } else {
                        Text(
                            appUiState.countdownValue.toString(),
                            fontSize = 300.sp
                        ) // Display the countdown value
                    }


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