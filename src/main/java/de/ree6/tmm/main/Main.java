package de.ree6.tmm.main;

import de.presti.ree6.bot.BotWorker;
import de.presti.ree6.bot.version.BotVersion;
import de.ree6.tmm.events.MessageReceiveEvent;
import twitter4j.Twitter;

public class Main {

    public static Twitter twitter;

    public static void main(String[] args) {
        BotWorker.createBot(BotVersion.DEVELOPMENT_BUILD);
        twitter = Twitter.newBuilder().oAuthConsumer(args[0], args[1]).oAuthAccessToken(args[0], args[1]).build();
        BotWorker.addEvent(new MessageReceiveEvent());
    }

}