package com.sekwah.sekcphysics;

import com.sekwah.sekcphysics.generic.CommonProxy;
import com.sekwah.sekcphysics.network.UsageReport;
import com.sekwah.sekcphysics.ragdoll.Ragdolls;
import com.sekwah.sekcphysics.ragdoll.ragdolls.vanilla.VanillaRagdolls;
import com.sekwah.sekcphysics.settings.ModSettings;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * Created by sekwah on 31/07/2015.
 * Atm the mod will just be client side which will stop the server which will probably be quite slow doing all the calculations
 *  also the players have complete controll over them, after all at least the ragdolls are supposed to only be for visual
 *   effects :3
 *
 *   Dont need to use proxy but do just in case you start adding server side too stuff for non visual things.
 *   Also can add something to add warnings to the server console(itll also stop crashes on a server if someone doesnt pay
 *   attention)
 *
 *   http://wiki.lwjgl.org/wiki/OpenCL_in_LWJGL.html
 *
 *   lwjgl has opencl built in, take a look for future reference :D
 *
 *   Also take a look at maybe making the ragdolls not based on entities and soley their own thing to stop strange interactions.
 *  (For now use entities)
 *
 *   Also potentially add physics to the cloak, even if its as a giant square, but maybe split to small blocks and do it like
 *   real cloth
 */
@Mod(modid = SekCPhysics.modid, name = "SekC Physics", version = SekCPhysics.version)
public class SekCPhysics {

    public static final String modid = "sekcphysics";
    public static Logger logger;

    public static final String version = "0.0.1";

    public static final boolean isDeObf = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

    /**
     * Start using interfaces more
     */
    @Mod.Instance
	public static SekCPhysics instance;

    public static UsageReport usageReport;

    public static Ragdolls ragdolls = new Ragdolls();

    @SidedProxy(clientSide = "com.sekwah.sekcphysics.client.ClientProxy", serverSide = "com.sekwah.sekcphysics.generic.CommonProxy")
    public static CommonProxy proxy;

    public static File configFolder;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        if(FMLCommonHandler.instance().getSide().isServer()) {
            logger.error("The mod so far contains only visual features, there is no point having it installed on anything other " +
                    "than a client for now.");
        }

        //SekCPhysics.usageReport = new UsageReport(proxy.isClient());
        //SekCPhysics.usageReport.startUsageReport();

        proxy.init();

        VanillaRagdolls.register();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        proxy.postInit();

    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        this.logger = event.getModLog();
        configFolder = event.getModConfigurationDirectory();
        ModSettings.preInit(event);

        // Add ProgressManager data for generating and other steps.

        proxy.preInit();

    }

    public void preInit() {
    }



}
