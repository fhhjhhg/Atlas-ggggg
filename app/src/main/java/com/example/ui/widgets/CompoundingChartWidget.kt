package com.example.ui.widgets

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.CompoundingYearData
import com.example.ui.DashboardUiState
import com.example.ui.theme.CyberBg
import com.example.ui.theme.CyberSurfaceBorder
import com.example.ui.theme.CyberSurfaceVariant
import com.example.ui.theme.NeonAmber
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.NeonViolet
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CompoundingChartWidget(
    state: DashboardUiState,
    onUpdateInputs: (beds: Int?, rent: Double?, cagr: Double?, market: Double?) -> Unit,
    modifier: Modifier = Modifier
) {
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US).apply {
        maximumFractionDigits = 0
    }

    var selectedIndex by remember { mutableStateOf(state.compoundingYears.size - 1) }
    val selectedData = state.compoundingYears.getOrElse(selectedIndex) { state.compoundingYears.last() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("compounding_chart_widget")
    ) {
        // Scrubber Selection Details Card
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            color = CyberBg,
            border = androidx.compose.foundation.BorderStroke(1.dp, NeonCyan.copy(alpha = 0.4f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(NeonCyan)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "YEAR ${selectedData.year} PROJECTION",
                            color = NeonCyan,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Portfolio: ${currencyFormat.format(selectedData.liquidPortfolio)}",
                        color = TextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Net Cash: ${currencyFormat.format(selectedData.netCashFlow)}/yr",
                        color = NeonEmerald,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = "Gross: ${currencyFormat.format(selectedData.grossRevenue)}",
                        color = TextSecondary,
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Custom Canvas Chart
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(CyberBg)
                .border(1.dp, CyberSurfaceBorder, RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Canvas(modifier = Modifier.fillMaxWidth().height(124.dp)) {
                val data = state.compoundingYears
                if (data.isEmpty()) return@Canvas

                val maxVal = data.maxOf { it.liquidPortfolio }
                val minVal = 0.0
                val w = size.width
                val h = size.height

                // Draw background grid horizontal lines
                val gridLines = 3
                for (i in 0..gridLines) {
                    val y = h * (i / gridLines.toFloat())
                    drawLine(
                        color = CyberSurfaceBorder.copy(alpha = 0.5f),
                        start = Offset(0f, y),
                        end = Offset(w, y),
                        strokeWidth = 1f
                    )
                }

                // Compute points
                val points = data.mapIndexed { index, item ->
                    val x = (index.toFloat() / (data.size - 1).coerceAtLeast(1)) * w
                    val normY = ((item.liquidPortfolio - minVal) / (maxVal - minVal).coerceAtLeast(1.0)).toFloat()
                    val y = h - (normY * (h - 16f)) - 8f
                    Offset(x, y)
                }

                // Path for area fill
                val fillPath = Path().apply {
                    moveTo(0f, h)
                    points.forEach { lineTo(it.x, it.y) }
                    lineTo(w, h)
                    close()
                }

                drawPath(
                    path = fillPath,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            NeonCyan.copy(alpha = 0.35f),
                            NeonViolet.copy(alpha = 0.05f),
                            Color.Transparent
                        )
                    )
                )

                // Path for stroke line
                val strokePath = Path().apply {
                    points.forEachIndexed { i, p ->
                        if (i == 0) moveTo(p.x, p.y) else lineTo(p.x, p.y)
                    }
                }

                drawPath(
                    path = strokePath,
                    brush = Brush.horizontalGradient(listOf(NeonCyan, NeonViolet)),
                    style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                )

                // Draw points and highlighted point
                points.forEachIndexed { idx, pt ->
                    val isSelected = idx == selectedIndex
                    drawCircle(
                        color = if (isSelected) NeonAmber else NeonCyan,
                        radius = if (isSelected) 6.dp.toPx() else 3.5.dp.toPx(),
                        center = pt
                    )
                    if (isSelected) {
                        drawCircle(
                            color = Color.White,
                            radius = 2.dp.toPx(),
                            center = pt
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Year Selector Chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            state.compoundingYears.forEachIndexed { idx, item ->
                val isSelected = idx == selectedIndex
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) NeonCyan else CyberSurfaceVariant)
                        .clickable { selectedIndex = idx }
                        .padding(horizontal = 6.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "Yr${item.year}",
                        color = if (isSelected) Color.Black else TextSecondary,
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // System assumptions footnote
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Model: 35 Beds • $830/mo • Fixed Debt $33.5k",
                color = TextMuted,
                fontSize = 9.sp
            )
            Text(
                text = "CAGR: Rent 3.6% • S&P 10.0%",
                color = NeonCyan,
                fontSize = 9.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
