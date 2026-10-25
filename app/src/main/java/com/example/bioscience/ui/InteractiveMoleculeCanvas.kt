package com.example.bioscience.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.bioscience.model.PdbAtom
import com.example.bioscience.model.PdbResidue
import com.example.bioscience.model.SecondaryStructure
import com.example.ui.theme.CyberBg
import com.example.ui.theme.CyberSurfaceBorder
import com.example.ui.theme.NeonAmber
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.NeonRose
import com.example.ui.theme.NeonViolet
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun InteractiveMoleculeCanvas(
    atoms: List<PdbAtom>,
    residues: List<PdbResidue>,
    modifier: Modifier = Modifier
) {
    var rotX by remember { mutableFloatStateOf(25f) }
    var rotY by remember { mutableFloatStateOf(-30f) }
    var zoomScale by remember { mutableFloatStateOf(16f) }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(CyberBg)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    rotY += dragAmount.x * 0.5f
                    rotX -= dragAmount.y * 0.5f
                }
            }
            .pointerInput(Unit) {
                detectTransformGestures { _, _, zoom, _ ->
                    zoomScale = (zoomScale * zoom).coerceIn(8f, 40f)
                }
            }
            .testTag("molecule_3d_canvas")
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            val centerX = width / 2f
            val centerY = height / 2f

            // Calculate center of mass of atoms to center the model
            var sumX = 0f
            var sumY = 0f
            var sumZ = 0f
            val n = atoms.size.coerceAtLeast(1)
            for (atom in atoms) {
                sumX += atom.x
                sumY += atom.y
                sumZ += atom.z
            }
            val comX = sumX / n
            val comY = sumY / n
            val comZ = sumZ / n

            val radX = Math.toRadians(rotX.toDouble())
            val radY = Math.toRadians(rotY.toDouble())
            val cosX = cos(radX).toFloat()
            val sinX = sin(radX).toFloat()
            val cosY = cos(radY).toFloat()
            val sinY = sin(radY).toFloat()

            // 3D rotation around COM
            data class ProjectedAtom(
                val atom: PdbAtom,
                val px: Float,
                val py: Float,
                val depth: Float,
                val color: Color
            )

            val projectedAtoms = atoms.map { atom ->
                val dx = atom.x - comX
                val dy = atom.y - comY
                val dz = atom.z - comZ

                // Y-axis rotation
                val x1 = dx * cosY + dz * sinY
                val z1 = -dx * sinY + dz * cosY

                // X-axis rotation
                val y2 = dy * cosX - z1 * sinX
                val z2 = dy * sinX + z1 * cosX

                val px = centerX + x1 * zoomScale
                val py = centerY + y2 * zoomScale

                val atomColor = when (atom.element.uppercase()) {
                    "N" -> Color(0xFF38BDF8) // Light blue
                    "O" -> Color(0xFFF43F5E) // Red
                    "S" -> Color(0xFFFACC15) // Yellow
                    "P" -> Color(0xFFFB923C) // Orange
                    else -> Color(0xFF10B981) // Carbon: Emerald/Cyan
                }

                ProjectedAtom(atom, px, py, z2, atomColor)
            }

            // Draw backbone ribbon trace through CA (Alpha Carbons)
            val caAtoms = projectedAtoms.filter { it.atom.atomName == "CA" }.sortedBy { it.atom.resSeq }
            if (caAtoms.size > 1) {
                val ribbonPath = Path()
                ribbonPath.moveTo(caAtoms[0].px, caAtoms[0].py)
                for (i in 1 until caAtoms.size) {
                    val prev = caAtoms[i - 1]
                    val curr = caAtoms[i]
                    val midX = (prev.px + curr.px) / 2f
                    val midY = (prev.py + curr.py) / 2f
                    ribbonPath.quadraticTo(prev.px, prev.py, midX, midY)
                }
                drawPath(
                    path = ribbonPath,
                    color = NeonCyan.copy(alpha = 0.55f),
                    style = Stroke(width = 4.5f)
                )
            }

            // Draw covalent bonds between closely spaced atoms (< 2.0 Angstrom)
            val sortedByDepth = projectedAtoms.sortedBy { it.depth }
            for (i in atoms.indices) {
                val a1 = atoms[i]
                val p1 = projectedAtoms[i]
                for (j in i + 1 until minOf(atoms.size, i + 8)) {
                    val a2 = atoms[j]
                    val dx = a1.x - a2.x
                    val dy = a1.y - a2.y
                    val dz = a1.z - a2.z
                    val distSq = dx * dx + dy * dy + dz * dz
                    if (distSq in 0.8f..3.6f) { // ~1.2 - 1.9 Angstrom bond distance
                        val p2 = projectedAtoms[j]
                        drawLine(
                            color = Color(0xFF64748B).copy(alpha = 0.7f),
                            start = Offset(p1.px, p1.py),
                            end = Offset(p2.px, p2.py),
                            strokeWidth = 2.2f
                        )
                    }
                }
            }

            // Draw atoms sorted back-to-front
            for (pa in sortedByDepth) {
                val radius = (4.5f + (pa.depth * 0.08f)).coerceIn(2.5f, 9.5f)
                drawCircle(
                    color = pa.color,
                    radius = radius,
                    center = Offset(pa.px, pa.py)
                )
                // Highlight glint
                drawCircle(
                    color = Color.White.copy(alpha = 0.6f),
                    radius = radius * 0.35f,
                    center = Offset(pa.px - radius * 0.3f, pa.py - radius * 0.3f)
                )
            }
        }
    }
}
