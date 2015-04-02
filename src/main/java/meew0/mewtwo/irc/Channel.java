package meew0.mewtwo.irc;

/**
 * Created by meew0 on 02.04.15.
 */
public class Channel implements IMessageTarget {
    private final String name;
    private final IRCBot bot;

    public Channel(String name, IRCBot bot) {
        this.name = name;
        this.bot = bot;
    }

    public String getName() {
        return name;
    }

    public IRCBot getBot() {
        return bot;
    }

    public String getUnprefixedName() {
        return name.substring(1);
    }

    @Override
    public void sendMessage(String message) {
        bot.writePrivmsg(name, message);
    }
}
