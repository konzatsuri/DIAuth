//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.happyrogue36.authorization;

import java.awt.Color;
import java.util.Objects;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.jetbrains.annotations.NotNull;

public class discordListener extends ListenerAdapter {
    static ShardManager shardManager;

    public discordListener(ShardManager shardManager) {
        discordListener.shardManager = shardManager;
    }

    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event == null) {
        }

        if (!event.getAuthor().isBot()) {
            Channel channel = event.getChannel();
            if (channel instanceof PrivateChannel) {
                String message = event.getMessage().getContentRaw().trim();
                User user = Authorization.data.getUserByCode(message);
                EmbedBuilder embed;
                if (user == null) {
                    embed = (new EmbedBuilder()).setTitle("Ошибка").setDescription("Пользователя с таким кодом не найдено!").setColor(16711680);
                    ((PrivateChannel)channel).sendMessageEmbeds(embed.build(), new MessageEmbed[0]).queue();
                } else if (user.getUserId() != null) {
                    sendMessageToConnect(user.getUserId());
                } else {
                    embed = (new EmbedBuilder()).setTitle("Регистрация прошла успешно").setDescription("Вы успешно зарегистрировались!").setColor(65280);
                    ((PrivateChannel)channel).sendMessageEmbeds(embed.build(), new MessageEmbed[0]).queue();
                    user.setUserId(event.getAuthor().getId());
                    user.setChannel(channel.getId());
                    sendMessageToConnect(event.getAuthor().getId());
                }
            }
        }

    }

    public void onGenericMessageReaction(@NotNull GenericMessageReactionEvent event) {
        if (event == null) {
        }

        Channel channel = event.getChannel();
        if (channel instanceof PrivateChannel) {
            if (((net.dv8tion.jda.api.entities.User)Objects.requireNonNull(event.getUser())).isBot()) {
                return;
            }

            Emoji emoji = event.getReaction().getEmoji();
            if (emoji.equals(Emoji.fromUnicode("✅"))) {
                User user = Authorization.data.getUserByChannel(event.getChannel().getId());
                EmbedBuilder embed;
                if (user == null) {
                    embed = (new EmbedBuilder()).setTitle("Ошибка").setDescription("Вы еще не заходили на сервер!").setColor(16711680);
                    event.getChannel().sendMessageEmbeds(embed.build(), new MessageEmbed[0]).queue();
                } else {
                    user.setAbilityToConnect(true);
                    user.setTime(Authorization.data.time);
                    embed = (new EmbedBuilder()).setTitle("Верификация прошла успешно").setDescription("Вы успешно верифицировались!\nС радостью играйте на проекте").setColor(65280);
                    event.getChannel().sendMessageEmbeds(embed.build(), new MessageEmbed[0]).queue();
                }
            }
        }

    }

    public static void sendMessageToConnect(String userId) {
        shardManager.retrieveUserById(userId).queue((currentUser) -> {
            currentUser.openPrivateChannel().flatMap((channel) -> {
                EmbedBuilder builder = (new EmbedBuilder()).setTitle("Запрос на верификацию").setDescription("Для подключения к серверу необходимо пройти верификацию, нажав на реакцию ниже.").setColor(Color.GREEN);
                return channel.sendMessageEmbeds(builder.build(), new MessageEmbed[0]);
            }).queue((message) -> {
                message.addReaction(Emoji.fromUnicode("✅")).queue();
            });
        });
    }
}
