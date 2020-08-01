package ez.galaxymonster424.autol;

import ez.galaxymonster424.autol.commands.AutoLCommand;
import ez.galaxymonster424.autol.config.FileUtils;
import ez.galaxymonster424.autol.events.AutoLEvents;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = AutoL.MODID, version = AutoL.VERSION, acceptedMinecraftVersions="[1.8.9]")
public class AutoL {

    public static final String MODID = "AutoL";
    public static final String VERSION = "0.1";

    @Mod.Instance
    private static AutoL instance;

    private FileUtils fileUtils;

    private boolean isOn = true;
    private int tickDelay = 20;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        this.fileUtils = new FileUtils(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        this.fileUtils.loadConfig();

        ClientCommandHandler.instance.registerCommand(new AutoLCommand());
        MinecraftForge.EVENT_BUS.register(new AutoLEvents());
    }

    public FileUtils getFileUtils() {
        return this.fileUtils;
    }

    public boolean isOn() {
        return this.isOn;
    }

    public void setOn(boolean on) {
        this.isOn = on;
    }

    public int getTickDelay() {
        return this.tickDelay;
    }

    public void setTickDelay(int delay) {
        this.tickDelay = delay;
    }

    public static AutoL getInstance() {
        return instance;
    }
}
