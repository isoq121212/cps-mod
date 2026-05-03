package com.cpsmod;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;

import java.util.ArrayDeque;
import java.util.Deque;

public class CpsTracker {

    // Очереди для хранения времени кликов (в миллисекундах)
    private static final Deque<Long> leftClicks = new ArrayDeque<>();
    private static final Deque<Long> rightClicks = new ArrayDeque<>();

    // Состояние кнопок мыши
    private static boolean wasLeftPressed = false;
    private static boolean wasRightPressed = false;

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            long now = System.currentTimeMillis();
            long cutoff = now - 1000; // последняя 1 секунда

            // Удаляем старые клики (старше 1 секунды)
            while (!leftClicks.isEmpty() && leftClicks.peekFirst() < cutoff) {
                leftClicks.pollFirst();
            }
            while (!rightClicks.isEmpty() && rightClicks.peekFirst() < cutoff) {
                rightClicks.pollFirst();
            }

            // Отслеживаем левую кнопку мыши
            boolean leftNow = client.options.keyAttack.isPressed();
            if (leftNow && !wasLeftPressed) {
                leftClicks.addLast(now);
            }
            wasLeftPressed = leftNow;

            // Отслеживаем правую кнопку мыши
            boolean rightNow = client.options.keyUse.isPressed();
            if (rightNow && !wasRightPressed) {
                rightClicks.addLast(now);
            }
            wasRightPressed = rightNow;
        });
    }

    public static int getLeftCps() {
        return leftClicks.size();
    }

    public static int getRightCps() {
        return rightClicks.size();
    }
}
