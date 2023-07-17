package ru.simplykel.kelutils.server.motd;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import ru.simplykel.kelutils.common.Utils;
import ru.simplykel.kelutils.server.Main;
import ru.simplykel.kelutils.server.config.MOTDConfig;

public class MOTD {

    public static void setMetaData() {
        // Загрузка конфигурации
        MOTDConfig.load();
        // Инициализация описания
        String descreption = MOTDConfig.line1;

        // Подсчет времени. Если мир - null, ставит -1
        ServerWorld world = Main.server.getWorld(World.OVERWORLD);
        long dayTime = world != null ? world.getLunarTime() % 24000L : -1;

        String time = dayTime < 0 ? "" : dayTime < 6000 ? MOTDConfig.morning : dayTime < 12000 ?
                MOTDConfig.day : dayTime < 16500 ? MOTDConfig.evening : MOTDConfig.night;

        // Добавление типа времени, если время > 0
        if (time.length() != 0) {
            int countEnable = MOTDConfig.lineCount - Utils.clearFormatCodes(MOTDConfig.line1).length() -
                    Utils.clearFormatCodes(time).length();
            descreption = countEnable <= 0 ? MOTDConfig.line1 :
                    MOTDConfig.line1 + " ".repeat(countEnable) + time;
        }
        // В случае, если НЕ рандомная строка на 2 линии, происходит добавление 2 строки. Иначе: выбор рандомной строки.
        double random = Math.floor(Math.random() * MOTDConfig.line2.length());
        descreption += !MOTDConfig.useRandomLine2 ? "\n" + MOTDConfig.line2:
                MOTDConfig.randomLine2.length() == 0 ? "\n" + MOTDConfig.line2 :
                        "\n" + MOTDConfig.randomLine2.getString((int) random);
        descreption = Utils.parseRGB(descreption);
        Main.server.setMotd(descreption);
    }
}
