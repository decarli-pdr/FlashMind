package br.com.jogosecm.deolhonascores.telas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.jogosecm.deolhonascores.R

@Composable
fun AppLogo(modifier: Modifier, cor: Color) {
    Box(
        modifier = modifier
            .background(
                shape = androidx.compose.foundation.shape.RoundedCornerShape(15.dp),
                color = cor
            )
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.marca_sem_fundo),
            contentDescription = "Logomarca do projeto",
            modifier = modifier
        )
    }
}

@Preview
@Composable
fun PreviewAppLogo() {
    AppLogo(Modifier, MaterialTheme.colorScheme.secondary)
}