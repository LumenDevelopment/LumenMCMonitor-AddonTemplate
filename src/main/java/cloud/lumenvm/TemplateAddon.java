package cloud.lumenvm;

import cloud.lumenvm.api.*;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class TemplateAddon implements MonitorAddon, Listener {

    /**MonitorAPI can be used for getting the main LumenMC Monitor plugin, sending content and embeds to discord and more.*/
    private MonitorAPI api;

    /**This can be used to assign the main LumenMC Monitor plugin.*/
    private JavaPlugin plugin;

    /**@return name of the addon*/
    @Override
    public String getName() {
        return "Template";
    }

    /**
     * This will trigger when the addon loads. You can set api, plugin here.
     * You also <strong>must register a Bukkit event</strong> for the addon to work.
     * @param monitorAPI this is the api from the main plugin
     */
    @Override
    public void onLoad(MonitorAPI monitorAPI) {
        this.api = monitorAPI;
        this.plugin = api.getPlugin();

        // Initialize new embed that is not a part of the main plugin. A JSON must be present in the embeds folder
        api.getEmbeds().put("custom", new Embed("custom"));

        // Register a Bukkit event
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /**This will trigger when the addon unloads.*/
    @Override
    public void onUnload() {
        plugin.getServer().getConsoleSender().sendMessage("Disabling LumenMC Monitor Addon: " + getName());
    }

    /**You can define any event here. You can also import almost any Minecraft plugin to get events from them.*/
    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onServerLoad(ServerLoadEvent event) {
        api.fireContent("Server loaded with LumenMC Monitor addon: " + getName());
    }
}
