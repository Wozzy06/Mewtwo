package meew0.mewtwo.thread;

import meew0.mewtwo.MewtwoMain;
import meew0.mewtwo.irc.ConsoleUser;
import org.pircbotx.Channel;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by meew0 on 31.08.14.
 */
public class BotWrapperThread implements Runnable {
    private final Configuration.Builder<PircBotX> configuration;
    private final String threadName;

    public BotWrapperThread(Configuration.Builder<PircBotX> configuration, String nick, String serverHostName, int serverPort, String threadName) {
        this.configuration = configuration;

        this.configuration.setName(nick);
        this.configuration.setServer(serverHostName, serverPort);

        this.threadName = threadName;
    }

    @Override
    public void run() {
        final PircBotX mewtwo = new PircBotX(configuration.buildConfiguration());

        MewtwoMain.mewtwoLogger.info("Bot created, starting up...");

        MewtwoMain.mewtwoLogger.info("Starting up processing thread...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    if(InputWatchThread.hasNextFor(threadName)) {
                        MewtwoMain.mewtwoLogger.info("Hey! There's something for me to do!");
                        InputWatchThread.InputEntry entry = InputWatchThread.getNext();
                        Channel channel = mewtwo.getUserChannelDao().getChannel(entry.getChannel());
                        User user = new ConsoleUser(mewtwo);
                        MessageEvent<PircBotX> event = new MessageEvent<PircBotX>(mewtwo, channel, user, entry.getMessage());
                        try {
                            MewtwoMain.listener.onMessage(event);
                        } catch(Throwable t) {
                            MewtwoMain.mewtwoLogger.error("Error while triggering console event!", t);
                        }
                    }
                }
            }
        }, threadName + "-InputProcessingThread").start();
        MewtwoMain.mewtwoLogger.info("Successfully started input processing thread");

        try {
            mewtwo.startBot();
            MewtwoMain.mewtwoLogger.info("Bot started, listening now");
        } catch(Exception e) {
            e.printStackTrace();
        }
        MewtwoMain.mewtwoLogger.info("Bot started without problems! Yay! Now to spend the rest of my " +
                "lifetime processing input items just so you can use the console interactively.");
    }
}
