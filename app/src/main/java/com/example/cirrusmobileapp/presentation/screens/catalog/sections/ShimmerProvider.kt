import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun Modifier.shimmerable(
    shape: Shape = RoundedCornerShape(8.dp),
    color: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
): Modifier {
    if (!LocalShimmerState.current.isLoading) return this

    return this
        .shimmer()
        .background(color = color, shape = shape)
        .drawWithContent {
            drawContent()
        }
}

data class ShimmerState(val isLoading: Boolean)

val LocalShimmerState = compositionLocalOf { ShimmerState(isLoading = false) }

@Composable
fun ShimmerProvider(isLoading: Boolean = true, content: @Composable (Boolean) -> Unit) {
    CompositionLocalProvider(
        value = LocalShimmerState provides ShimmerState(isLoading = isLoading),
        content = { content(isLoading) },
    )
}