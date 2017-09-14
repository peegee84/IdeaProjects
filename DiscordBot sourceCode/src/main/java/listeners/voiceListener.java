package listeners;

import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class voiceListener extends ListenerAdapter {

    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {

        event.getGuild().getTextChannelsByName("voicelog", true).get(0).sendMessage(
                "Member" + event.getVoiceState() + " joined voice Channel" +event.getChannelJoined() + "."
        ).queue();

    }

}
