//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.happyrogue36.authorization;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

public class botApp {
    private ShardManager shardManager;

    public botApp() {
        try {
            DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(Authorization.data.token);
            builder.setStatus(OnlineStatus.ONLINE);
            builder.setActivity(Activity.playing("Отправь мне код"));
            builder.enableIntents(GatewayIntent.GUILD_MESSAGES, new GatewayIntent[]{GatewayIntent.GUILD_MESSAGE_TYPING, GatewayIntent.MESSAGE_CONTENT});
            this.shardManager = builder.build();
            this.shardManager.addEventListener(new Object[]{new discordListener(this.shardManager)});
        } catch (Exception var2) {
            System.out.println(var2);
        }

    }

    public ShardManager getShardManager() {
        return this.shardManager;
    }
}
