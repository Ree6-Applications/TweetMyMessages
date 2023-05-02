package de.ree6.tmm.events;

import de.ree6.tmm.main.Main;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import twitter4j.TwitterException;
import twitter4j.v1.StatusUpdate;

public class MessageReceiveEvent extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        super.onMessageReceived(event);
        if (event.getChannel().getType() == ChannelType.NEWS) {
            Message message = event.getMessage();

            String content = message.getContentDisplay();

            StatusUpdate statusUpdate = StatusUpdate.of(content);

            for (Message.Attachment attachment : message.getAttachments()) {
                if (attachment.isImage() || attachment.isVideo()) {
                    try {
                        statusUpdate = statusUpdate.media(attachment.getFileName(), attachment.getProxy().download().get());
                    } catch (Exception exception) {
                        System.out.println("Failed to upload attachment!");
                        exception.printStackTrace();
                    }
                }
            }

            try {
                Main.twitter.v1().tweets().updateStatus(statusUpdate);
            } catch (TwitterException e) {
                System.out.println("Failed to update attachment!");
                e.printStackTrace();
            }

            message.addReaction(Emoji.fromUnicode("\uD83D\uDC26")).queue();
        }
    }
}
