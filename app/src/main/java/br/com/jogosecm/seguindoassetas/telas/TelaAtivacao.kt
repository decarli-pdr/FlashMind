package br.com.jogosecm.seguindoassetas.telas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun TelaAtivacao(

) {

    val currentDateTime = LocalDateTime.now(ZoneId.systemDefault())
    val formattedTime = currentDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))

    var senha by remember { mutableStateOf(value = "") }

    Surface(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Para ativar o aplicativo, insira a senha",
                modifier = Modifier.padding(20.dp),
                fontSize = 30.sp
            )
            TextField(
                value = senha,
                modifier = Modifier.padding(20.dp),
                singleLine = true,
                onValueChange = { senha = it },
                label = {
                    Text(text = "Senha", textAlign = TextAlign.Center)
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.NumberPassword, imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    if (senha == formattedTime) {
                        println("entrou")
                    }
                    //focusManager.clearFocus()

                }),
                textStyle = TextStyle.Default.copy(
                    textAlign = TextAlign.Center, fontSize = 40.sp
                )
            )
        }
    }
}


@Preview(
    showSystemUi = true, showBackground = true, device = "spec:parent=pixel_5,orientation=landscape"
)
@Composable
fun TelaAtivacaoPreview() {
    TelaAtivacao()
}