package com.github.kimberlyow.fishonautotip.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "fishonautotip")
public class FishOnAutotipConfig implements ConfigData {
    public int reactionTipAmount = 100;
    public int commonChummerTipAmount = 500;
    public int rareChummerTipAmount = 1000;
    public int epicChummerTipAmount = 1500;
    public int legendaryChummerTipAmount = 2000;
    public int mythicalChummerTipAmount = 2500;
}
