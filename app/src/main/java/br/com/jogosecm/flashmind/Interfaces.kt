package br.com.jogosecm.flashmind

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

interface ObjetoMostravel {
    @Composable
    fun Mostrar()
}

data class Cor(val cor: Color) : ObjetoMostravel {
    @Composable
    override fun Mostrar() {
        Surface(modifier = Modifier.fillMaxSize(),
            color = cor){}
    }

}

data class Caractere(val letra: String): ObjetoMostravel{
    @Composable
    override fun Mostrar() {
        Text(
            text = letra,
            fontSize = 370.sp,
            fontFamily = FontFamily(
                Font(R.font.collegiateheavyoutline_medium, FontWeight.Normal)

           /* style = TextStyle(

                fontWeight = FontWeight.ExtraBold,
             //   textDecoration = TextDecoration.Underline,
                shadow = Shadow(Color.Gray,
                    blurRadius = 90f)
            )*/))
    }

}