package home.presentation.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
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
import home.presentation.event.RecordingFrameEvent

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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .background(Color.Green)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
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
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .dashedBorder(
                        width = windowOutlineWidth,
                        radius = 12.dp,
                        color = windowOutlineColor
                    )
            ) {

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