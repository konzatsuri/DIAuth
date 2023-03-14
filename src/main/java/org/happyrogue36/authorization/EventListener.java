package org.happyrogue36.authorization;

import net.dv8tion.jda.api.JDA;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        User user = Authorization.data.getUser(player);
        if (!user.abilityToConnect) {
            Authorization.data.CreateCodeForPlayer(player);
            String mes = "";
            if (user.getUserId() != null) {
                mes = "Володя отправил форму для входа, пожалуйста авторизуйтесь";
                discordListener.sendMessageToConnect(user.getUserId());
            } else if (!user.getTryToConnect()) {
                mes = "§eОтправьте код - §6" + user.code + " §eботу Володя";
                user.setTryToConnect(true);
            } else {
                mes = "§eПодтвердите вход через дискорд\n(код: §6" + user.code + "§e)";
            }
            event.getPlayer().kickPlayer(mes);
        }
    }
}
