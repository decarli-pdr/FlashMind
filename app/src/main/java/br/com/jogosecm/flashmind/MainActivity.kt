package br.com.jogosecm.flashmind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.jogosecm.flashmind.telas.TelaInicio
import br.com.jogosecm.flashmind.telas.TelaJogo
import br.com.jogosecm.flashmind.ui.theme.FlashMindTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlashMindTheme {
                TelaDoApp(modifier = Modifier)

            }
        }
    }
}

@Composable
fun TelaDoApp(
    modifier: Modifier,
    model: AppViewModel = viewModel()
) {
    ComposeNavigation(modifier = modifier, model = model)
}

@Composable
fun ComposeNavigation(modifier: Modifier, model: AppViewModel) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Inicio") {
        composable("Inicio") {
            TelaInicio(
                modifier = modifier,
                viewModelAtual = model,
                mudouOTempoMax = { model.defineTempoMax(it) },
                navHostController = navController,
                mudouTextoValido = { model.mudaBotao(it) },
                mudaramRodadas = { model.defineRodadas(it) },
                contexto = LocalContext.current
            )
        }

        composable("Jogo") {
            TelaJogo(
                modifier = modifier,
                viewModelAtual = model,
                navHostController = navController,
                contexto = LocalContext.current
            )
        }
    }

}

@Preview(
    showBackground = true,
    showSystemUi = true, device = "spec:parent=pixel_5",

    )
@Composable
fun GreetingPreview() {
    FlashMindTheme {
        TelaDoApp(modifier = Modifier)
    }
}