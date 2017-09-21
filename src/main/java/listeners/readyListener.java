package listeners;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class readyListener extends ListenerAdapter {

    public void onReady(ReadyEvent event) {

        String out = "\nThis Bot is running on folowing Servers: \n";

        for (Guild g : event.getJDA().getGuilds()) {
            out += g.getName();

        }

        System.out.println(out);

        for (Guild g : event.getJDA().getGuilds()) {
            g.getTextChannels().get(0).sendMessage(
                    "Bot reporting in for duty"
            ).queue();

        }

    }
}
