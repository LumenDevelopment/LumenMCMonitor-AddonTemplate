package cloud.lumenvm.template;

import cloud.lumenvm.api.AddonContext;
import cloud.lumenvm.api.MonitorAPI;
import cloud.lumenvm.api.MonitorAddon;
import cloud.lumenvm.monitor.Embed;
import cloud.lumenvm.monitor.Monitor;
import cloud.lumenvm.monitor.Webhook;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

/**Template addon*/
public class TemplateAddon implements MonitorAddon, Listener {

    /**{@link MonitorAPI} can be used for getting the main LumenMC Monitor plugin, sending content and embeds to discord and more.*/
    private MonitorAPI api;

    /**{@link Monitor LumenMC Monitor plugin}*/
    private Monitor plugin;

    /**@return name of the addon*/
    @Override
    public String getName() {
        return "Template";
    }

    /**
     * Triggers when the addon loads. You can set api, plugin here.
     * You also <strong>must register a Bukkit event</strong> for the addon to work.
     * @param monitorAPI api from the main plugin
     * @param context context from the main plugin
     */
    @Override
    public void onLoad(MonitorAPI monitorAPI, AddonContext context) {
        this.api = monitorAPI;
        this.plugin = api.getPlugin();

        // Initialize new embed that is not a part of the main plugin. A JSON must be present in the embeds folder
        api.getEmbeds().put("custom", new Embed("custom"));

        // Register a Bukkit event
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /**Triggers when the addon unloads.*/
    @Override
    public void onUnload() {
        plugin.getServer().getConsoleSender().sendMessage("Disabling LumenMC Monitor Addon: " + getName());
    }

    /**Define any event here. You can also import any Minecraft plugin to get events from them.*/
    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onServerLoad(ServerLoadEvent event) {
        for (Webhook webhook : plugin.webhooks.values()) {
            api.fireContent("Server loaded with LumenMC Monitor addon: " + getName(), webhook);
        }
    }
}
