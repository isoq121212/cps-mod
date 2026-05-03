package com.cpsmod;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public class CpsHudRenderer {

    public static void register() {
        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
            MinecraftClient client = MinecraftClient.getInstance();

            // Не показываем HUD в меню и если игрок не загружен
            if (client.player == null || client.currentScreen != null) return;

            int leftCps = CpsTracker.getLeftCps();
            int rightCps = CpsTracker.getRightCps();

            // Текст для отображения
            String leftText  = "LMB: " + leftCps  + " CPS";
            String rightText = "RMB: " + rightCps + " CPS";

            // Цвета: зелёный если хороший КПС, жёлтый средний, красный низкий
            int leftColor  = getCpsColor(leftCps);
            int rightColor = getCpsColor(rightCps);

            // Позиция: левый нижний угол экрана
            int screenHeight = client.getWindow().getScaledHeight();
            int x = 5;
            int yLeft  = screenHeight - 30;
            int yRight = screenHeight - 20;

            // Рисуем полупрозрачный фон
            int textWidth = Math.max(
                client.textRenderer.getWidth(leftText),
                client.textRenderer.getWidth(rightText)
            );
            DrawableHelper.fill(matrixStack,
                x - 2, yLeft - 2,
                x + textWidth + 2, yRight + client.textRenderer.fontHeight,
                0x88000000
            );

            // Рисуем текст
            client.textRenderer.drawWithShadow(matrixStack, leftText,  x, yLeft,  leftColor);
            client.textRenderer.drawWithShadow(matrixStack, rightText, x, yRight, rightColor);
        });
    }

    /**
     * Возвращает цвет в зависимости от КПС:
     * 10+ = зелёный, 6-9 = жёлтый, 0-5 = красный
     */
    private static int getCpsColor(int cps) {
        if (cps >= 10) return 0x55FF55; // ярко-зелёный
        if (cps >= 6)  return 0xFFFF55; // жёлтый
        return 0xFF5555;                 // красный
    }
}
