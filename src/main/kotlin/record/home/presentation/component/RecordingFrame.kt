package record.home.presentation.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState

@Composable
fun RecordingFrame(
    modifier: Modifier = Modifier,
    onWindowPlacement: (x: Int, y: Int, height: Int, width: Int) -> Unit
) {
    val windowState = rememberWindowState()
    val density = LocalDensity.current
    LaunchedEffect(windowState.size, windowState.position) {
        onWindowPlacement(
            windowState.position.x.value.toInt(),
            windowState.position.y.value.toInt(),
            windowState.size.height.value.toInt(),
            windowState.size.width.value.toInt(),
        )
    }

    Window(
        onCloseRequest = { },
        state = windowState,
        title = "Kapture",
        transparent = true,
        undecorated = true,
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()

        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .background(MaterialTheme.colorScheme.onPrimaryContainer)
                    .pointerInput(Unit) {
                        detectDragGestures { _, dragAmount ->
                            val dragAmountDp = with(density) {
                                DpOffset(dragAmount.x.toDp(), dragAmount.y.toDp())
                            }

                            if (windowState.position is WindowPosition.Absolute) {
                                val currentPosition = windowState.position as WindowPosition.Absolute
                                val newX = currentPosition.x + dragAmountDp.x
                                val newY = currentPosition.y + dragAmountDp.y

                                windowState.position = WindowPosition.Absolute(newX, newY)
                            }
                        }
                    }

            )
            val color = MaterialTheme.colorScheme.onPrimaryContainer
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = 0.5.dp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
            ) {
                val cornerSize = 20.dp.toPx() // Adjust the size of the corner as needed
                val strokeWidth = 4.dp.toPx() // Adjust the thickness of the corner lines as needed

                // Top-left corner
                drawLine(
                    color = color,
                    start = Offset(0f, cornerSize),
                    end = Offset(0f, 0f),
                    strokeWidth = strokeWidth,
                )
                drawLine(
                    start = Offset(0f, 0f),
                    end = Offset(cornerSize, 0f),
                    color = color,
                    strokeWidth = strokeWidth,
                )

                // Top-right corner
                drawLine(
                    start = Offset(size.width, 0f),
                    end = Offset(size.width - cornerSize, 0f),
                    color = color,
                    strokeWidth = strokeWidth,
                )
                drawLine(
                    start = Offset(size.width, 0f),
                    end = Offset(size.width, cornerSize),
                    color = color,
                    strokeWidth = strokeWidth,
                )

                // Bottom-left corner
                drawLine(
                    start = Offset(0f, size.height),
                    end = Offset(0f, size.height - cornerSize),
                    color = color,
                    strokeWidth = strokeWidth,
                )
                drawLine(
                    start = Offset(0f, size.height),
                    end = Offset(cornerSize, size.height),
                    color = color,
                    strokeWidth = strokeWidth,
                )

                // Bottom-right corner
                drawLine(
                    start = Offset(size.width, size.height),
                    end = Offset(size.width - cornerSize, size.height),
                    color = color,
                    strokeWidth = strokeWidth,
                )
                drawLine(
                    start = Offset(size.width, size.height),
                    end = Offset(size.width, size.height - cornerSize),
                    color = color,
                    strokeWidth = strokeWidth,
                )
            }
        }
    }
}

@Composable
fun drawResizableWindow() {

}

fun Modifier.dashedBorder(width: Dp, radius: Dp, color: Color) =
    drawBehind {
        drawIntoCanvas {
            val paint = Paint()
                .apply {
                    strokeWidth = width.toPx()
                    this.color = color
                    style = PaintingStyle.Stroke
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                }
            it.drawRoundRect(
                width.toPx(),
                width.toPx(),
                size.width - width.toPx(),
                size.height - width.toPx(),
                radius.toPx(),
                radius.toPx(),
                paint
            )
        }
    }