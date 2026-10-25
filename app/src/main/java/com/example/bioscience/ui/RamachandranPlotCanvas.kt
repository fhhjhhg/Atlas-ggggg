package com.example.bioscience.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.bioscience.model.RamachandranPoint
import com.example.bioscience.model.RamachandranRegion
import com.example.ui.theme.CyberBg
import com.example.ui.theme.CyberSurfaceBorder
import com.example.ui.theme.NeonAmber
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.NeonRose
import com.example.ui.theme.NeonViolet

@Composable
fun RamachandranPlotCanvas(
    points: List<RamachandranPoint>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(CyberBg)
            .testTag("ramachandran_plot_canvas")
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            fun mapCoord(phiDeg: Float, psiDeg: Float): Offset {
                // phi: [-180, 180] -> [0, width]
                // psi: [-180, 180] -> [height, 0] (since y is inverted in screen coords)
                val x = ((phiDeg + 180f) / 360f) * width
                val y = height - (((psiDeg + 180f) / 360f) * height)
                return Offset(x, y)
            }

            // Draw grid axes (phi = 0 and psi = 0)
            val zeroX = width / 2f
            val zeroY = height / 2f

            drawLine(
                color = CyberSurfaceBorder,
                start = Offset(zeroX, 0f),
                end = Offset(zeroX, height),
                strokeWidth = 1.5f
            )
            drawLine(
                color = CyberSurfaceBorder,
                start = Offset(0f, zeroY),
                end = Offset(width, zeroY),
                strokeWidth = 1.5f
            )

            // Favored Alpha region: phi in [-100, -30], psi in [-80, -10]
            val alphaTopLeft = mapCoord(-100f, -10f)
            val alphaBottomRight = mapCoord(-30f, -80f)
            drawRect(
                color = NeonCyan.copy(alpha = 0.18f),
                topLeft = alphaTopLeft,
                size = Size(alphaBottomRight.x - alphaTopLeft.x, alphaBottomRight.y - alphaTopLeft.y)
            )

            // Favored Beta region: phi in [-180, -60], psi in [80, 180]
            val betaTopLeft = mapCoord(-180f, 180f)
            val betaBottomRight = mapCoord(-60f, 80f)
            drawRect(
                color = NeonAmber.copy(alpha = 0.18f),
                topLeft = betaTopLeft,
                size = Size(betaBottomRight.x - betaTopLeft.x, betaBottomRight.y - betaTopLeft.y)
            )

            // Left-handed Alpha region: phi in [20, 100], psi in [-40, 80]
            val leftAlphaTopLeft = mapCoord(20f, 80f)
            val leftAlphaBottomRight = mapCoord(100f, -40f)
            drawRect(
                color = NeonViolet.copy(alpha = 0.15f),
                topLeft = leftAlphaTopLeft,
                size = Size(leftAlphaBottomRight.x - leftAlphaTopLeft.x, leftAlphaBottomRight.y - leftAlphaTopLeft.y)
            )

            // Border
            drawRect(
                color = CyberSurfaceBorder,
                size = size,
                style = Stroke(width = 2f)
            )

            // Plot residue dihedral angles
            for (pt in points) {
                val offset = mapCoord(pt.phiDeg, pt.psiDeg)
                val dotColor = when (pt.region) {
                    RamachandranRegion.CORE_ALPHA -> NeonCyan
                    RamachandranRegion.CORE_BETA -> NeonAmber
                    RamachandranRegion.ALLOWED -> NeonEmerald
                    RamachandranRegion.DISALLOWED -> NeonRose
                }

                drawCircle(
                    color = dotColor,
                    radius = 4f,
                    center = offset
                )
                drawCircle(
                    color = Color.Black.copy(alpha = 0.5f),
                    radius = 4f,
                    center = offset,
                    style = Stroke(width = 1f)
                )
            }
        }
    }
}
