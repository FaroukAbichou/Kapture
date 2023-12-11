package recor.home.presentation.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import recor.home.presentation.event.RecordingFrameEvent

@Composable
fun RecordingFrame(
    modifier: Modifier = Modifier,
    onEvent: (RecordingFrameEvent) -> Unit = {}
) {
    val windowColor = Color(0xFF000000)
    val windowOutlineColor = Color(0xFF525252)
    val windowOutlineWidth = 2.dp
    val windowState = rememberWindowState()
    val density = LocalDensity.current

    LaunchedEffect(windowState.size, windowState.position) {
        onEvent(
            RecordingFrameEvent.UpdateWindowPlacement(
                x = windowState.position.x.value.toInt(),
                y = windowState.position.y.value.toInt(),
                height = windowState.size.height.value.toInt(),
                width = windowState.size.width.value.toInt(),
            )
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
            modifier = Modifier
                .fillMaxSize()

        ){
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(30.dp)
//                    .background(Color.Green)
//                    .pointerInput(Unit) {
//                        detectDragGestures { change, dragAmount ->
//                            val dragAmountDp = with(density) {
//                                DpOffset(dragAmount.x.toDp(), dragAmount.y.toDp())
//                            }
//
//                            if (windowState.position is WindowPosition.Absolute) {
//                                val currentPosition = windowState.position as WindowPosition.Absolute
//                                val newX = currentPosition.x + dragAmountDp.x
//                                val newY = currentPosition.y + dragAmountDp.y
//
//                                windowState.position = WindowPosition.Absolute(newX, newY)
//                            }
//                        }
//                    }
//
//            )
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