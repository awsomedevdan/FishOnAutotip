package com.github.kimberlyow.fishonautotip.client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.Minecraft;
import com.github.kimberlyow.fishonautotip.config.FishOnAutotipConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

public class FishOnAutotipClient implements ClientModInitializer {
    private static final Pattern REACTION = Pattern.compile("REACTIONS » ([A-Za-z0-9_]{2,16}) has typed");
    private static final Pattern COMMON = Pattern.compile("CHUMMER » ([A-Za-z0-9_]{2,16}) activated a \uF033 Chummer");
    private static final Pattern RARE = Pattern.compile("CHUMMER » ([A-Za-z0-9_]{2,16}) activated a \uF034 Chummer");
    private static final Pattern EPIC = Pattern.compile("CHUMMER » ([A-Za-z0-9_]{2,16}) activated a \uF035 Chummer");
    private static final Pattern LEGENDARY = Pattern.compile("CHUMMER » ([A-Za-z0-9_]{2,16}) activated a \uF036 Chummer");
    private static final Pattern MYTHICAL = Pattern.compile("CHUMMER » ([A-Za-z0-9_]{2,16}) activated a \uF037 Chummer");
    private long lastPayTime;

    @Override
    public void onInitializeClient() {
        AutoConfig.register(FishOnAutotipConfig.class, GsonConfigSerializer::new);
        ClientReceiveMessageEvents.GAME.register((message, overlay) -> {
            String text = message.getString();
            if (!text.contains("»")) return;
            FishOnAutotipConfig cfg = AutoConfig.getConfigHolder(FishOnAutotipConfig.class).getConfig();
            if (text.startsWith("REACTIONS »")) {
                match(text, REACTION, cfg.reactionTipAmount);
            } else if (text.startsWith("CHUMMER »")) {
                if (match(text, COMMON, cfg.commonChummerTipAmount)) return;
                if (match(text, RARE, cfg.rareChummerTipAmount)) return;
                if (match(text, EPIC, cfg.epicChummerTipAmount)) return;
                if (match(text, LEGENDARY, cfg.legendaryChummerTipAmount)) return;
                match(text, MYTHICAL, cfg.mythicalChummerTipAmount);
            }
        });
    }

    private boolean match(String text, Pattern pattern, int amount) {
        if (amount < 100) return false;
        Matcher matcher = pattern.matcher(text);
        if (!matcher.find()) return false;
        String target = matcher.group(1);
        Minecraft client = Minecraft.getInstance();
        client.execute(() -> payPlayer(client, target, amount));
        return true;
    }

    private void payPlayer(Minecraft client, String target, int amount) {
        if (client.player == null || client.getConnection() == null) return;
        if (client.player.getName().getString().equals(target)) return;
        if (System.currentTimeMillis() - lastPayTime < 3000) return;
        lastPayTime = System.currentTimeMillis();
        client.getConnection().sendCommand("pay " + target + " " + amount);
    }
}
