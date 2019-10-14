package modernity.client;

import modernity.api.biome.BiomeColoringProfile;
import modernity.client.colormap.ColorMap;
import modernity.client.handler.TextureStitchHandler;
import modernity.client.reloader.BiomeColorProfileReloader;
import modernity.client.render.block.CustomFluidRenderer;
import modernity.common.Modernity;
import modernity.common.block.MDBlocks;
import modernity.common.container.MDContainerTypes;
import modernity.common.entity.MDEntityTypes;
import modernity.common.net.SSeedPacket;
import modernity.common.particle.MDParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.concurrent.ThreadTaskExecutor;
import net.minecraftforge.fml.LogicalSide;

/**
 * The modernity's client proxy. This class is intantiated during bootstrap when we're running on Minecraft Client (the
 * most usual case). This class inherits modernity's default proxy {@link Modernity} and adds extra behaviour and
 * loading for the client side only.
 */
public class ModernityClient extends Modernity {
    /** The {@link Minecraft} instance. */
    public final Minecraft mc = Minecraft.getInstance();

    private BiomeColoringProfile grassColors;
    private BiomeColoringProfile blackwoodColors;
    private BiomeColoringProfile inverColors;
    private BiomeColoringProfile waterColors;

    // Used to give color to humus particles
    private final ColorMap humusColors = new ColorMap( new ResourceLocation( "modernity:textures/block/humus_top.png" ), 0xffffff );

    private final CustomFluidRenderer fluidRenderer = new CustomFluidRenderer();

    // Last world seed, which is sent by server worlds when the player joins.
    private long lastWorldSeed;

    @Override
    public void preInit() {
        super.preInit();
        addFutureReloadListener( new BiomeColorProfileReloader( "modernity:grass", e -> grassColors = e ) );
        addFutureReloadListener( new BiomeColorProfileReloader( "modernity:blackwood", e -> blackwoodColors = e ) );
        addFutureReloadListener( new BiomeColorProfileReloader( "modernity:inver", e -> inverColors = e ) );
        addFutureReloadListener( new BiomeColorProfileReloader( "modernity:water", e -> waterColors = e ) );

        addFutureReloadListener( fluidRenderer );
        addFutureReloadListener( humusColors );
    }

    @Override
    public void init() {
        super.init();
        MDEntityTypes.initEntityRenderers();
        MDContainerTypes.registerScreens();
    }

    @Override
    public void postInit() {
        super.postInit();
        MDBlocks.initBlockColors();
    }

    @Override
    public void registerListeners() {
        super.registerListeners();
        MOD_EVENT_BUS.register( TextureStitchHandler.INSTANCE );
    }

    /**
     * Adds a reload listener to Minecraft's resource manager when it's reloadable
     */
    public void addFutureReloadListener( IFutureReloadListener listener ) {
        IResourceManager manager = mc.getResourceManager();
        if( manager instanceof IReloadableResourceManager ) {
            ( (IReloadableResourceManager) manager ).addReloadListener( listener );
        }
    }

    @Override
    protected ThreadTaskExecutor<Runnable> getClientThreadExecutor() {
        return mc;
    }

    @Override
    public LogicalSide side() {
        return LogicalSide.CLIENT;
    }

    /**
     * Gets the biome color profile for grass colors
     */
    public BiomeColoringProfile getGrassColors() {
        return grassColors;
    }

    /**
     * Gets the biome color profile for blackwood colors
     */
    public BiomeColoringProfile getBlackwoodColors() {
        return blackwoodColors;
    }

    /**
     * Gets the biome color profile for inver colors
     */
    public BiomeColoringProfile getInverColors() {
        return inverColors;
    }

    /**
     * Gets the biome color profile for water colors
     */
    public BiomeColoringProfile getWaterColors() {
        return waterColors;
    }

    /**
     * Gets the Modernity fluid render
     */
    public CustomFluidRenderer getFluidRenderer() {
        return fluidRenderer;
    }

    /**
     * Returns the Humus color map, used to give color to humus particles
     */
    public ColorMap getHumusColors() {
        return humusColors;
    }

    /**
     * Return the seed of the last joined world, or 0 if no world has sent a seed yet.
     */
    public long getLastWorldSeed() {
        return lastWorldSeed;
    }

    /**
     * Sets the seed of the last joined world. Used in {@link SSeedPacket} to set the received seed.
     */
    public void setLastWorldSeed( long lastWorldSeed ) {
        this.lastWorldSeed = lastWorldSeed;
    }

    /**
     * Gets the {@link ModernityClient} instance we're using now, or null if not yet initialized.
     */
    public static ModernityClient get() {
        return (ModernityClient) Modernity.get();
    }
}
