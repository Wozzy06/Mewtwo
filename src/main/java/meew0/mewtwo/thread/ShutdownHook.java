package meew0.mewtwo.thread;

import meew0.mewtwo.MewtwoLogger;

/**
 * Created by meew0 on 02.04.15.
 */
public class ShutdownHook extends Thread {
    @Override
    public void run() {
        MewtwoLogger.info("ShutdownHook registered JVM shutdown, making all IRCBots shut down");
        IRCBot.shouldShutDown = true;
    }
}
