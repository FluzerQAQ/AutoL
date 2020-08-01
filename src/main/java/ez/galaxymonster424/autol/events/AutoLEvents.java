package ez.galaxymonster424.autol.events;

import ez.galaxymonster424.autol.AutoL;
import ez.galaxymonster424.autol.utils.ChatColor;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class AutoLEvents {

    private Pattern chatPattern = Pattern.compile("(?<rank>\\.get(.+) )?(?<player>\\S{1,16}): (?<message>.*)");
    private Pattern teamPattern = Pattern.compile("\\.get(TEAM) (?<rank>\\.get(.+) )?(?<player>\\S{1,16}): (?<message>.*)");
    private Pattern guildPattern = Pattern.compile("Guild > (?<rank>\\.get(.+) )?(?<player>\\S{1,16}): (?<message>.*)");
    private Pattern partyPattern = Pattern.compile("Party > (?<rank>\\.get(.+) )?(?<player>\\S{1,16}): (?<message>.*)");
    private Pattern shoutPattern = Pattern.compile("\\.get(SHOUT) (?<rank>\\.get(.+) )?(?<player>\\S{1,16}): (?<message>.*)");
    private Pattern spectatorPattern = Pattern.compile("\\.get(SPECTATOR) (?<rank>\\.get(.+) )?(?<player>\\S{1,16}): (?<message>.*)");

    private final List<String> endingStrings = Arrays.asList(
            "1st Killer - ",
            "1st Place - ",
            "Winner: ",
            " - Damage Dealt - ",
            "Winning Team - ",
            "1st - ",
            "Winners: ",
            "Winner: ",
            "Winning Team: ",
            " won the game!",
            "Top Seeker: ",
            "1st Place: ",
            "Last team standing!",
            "Winner #1 (",
            "Top Survivors",
            "击杀数第一名",
			"击杀数第二名",
			"击杀数第三名",
            "第一名 - ",
            "胜者：",
            "获胜队伍 - ",
            "赢得了比赛！",
            "战到最后的队伍！",
            "胜利者 #1 (",
            "幸存者排行",
            "胜利者 -");

    private int tick = -1;

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onGameEndEvent(ClientChatReceivedEvent event) {
        if (event.isCanceled()) {
            return;
        }

        String message = ChatColor.stripColor(event.message.getUnformattedText());

        if (message.isEmpty()) {
            return;
        }

        try {
            if (!AutoL.getInstance().isOn() || isNormalMessage(message)) {
                return;
            }

            if (isEndOfGame(message)) {
                this.tick = AutoL.getInstance().getTickDelay();
            }
        } catch (Exception ex) {
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onGameTick(TickEvent.ClientTickEvent event) {
        if (this.tick == 0) {
            if (AutoL.getInstance().isOn())  {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/achat LLLLLLL");
            }
            this.tick = -1;
        } else {
            if (this.tick > 0) {
                this.tick--;
            }
        }
    }

    private boolean isNormalMessage(String message) {
        return this.chatPattern.matcher(message).matches() ||
                this.teamPattern.matcher(message).matches() ||
                this.guildPattern.matcher(message).matches() ||
                this.partyPattern.matcher(message).matches() ||
                this.shoutPattern.matcher(message).matches() ||
                this.spectatorPattern.matcher(message).matches();
    }

    private boolean isEndOfGame(String message) {
        for (String endingString : this.endingStrings) {
            if (message.contains(endingString)) {
                return true;
            }
        }
        return false;
    }
}
