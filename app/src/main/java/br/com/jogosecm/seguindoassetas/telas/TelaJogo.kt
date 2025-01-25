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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

    val listaDeSeta = listOf(
        R.drawable.direita,
        R.drawable.esquerda,
        //Icons.AutoMirrored.Rounded.ArrowBack
    )
    DisposableEffect(Unit) {
        val activity = contexto as? Activity
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        onDispose {
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    Surface(modifier = modifier.fillMaxSize()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center, content = {
            //val coroutineScope = rememberCoroutineScope()

            LaunchedEffect(key1 = appUiState.rodadas, key2 = appUiState.tempoMax) {
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
                if (appUiState.countdownValue == 0) {
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
                    Icon(
                        painter = painterResource(id = listaDeSeta.random()),
                        null,
                        modifier.size(500.dp)
                    )
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