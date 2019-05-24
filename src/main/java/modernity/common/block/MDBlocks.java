package modernity.common.block;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.IForgeRegistry;

import modernity.api.block.IColoredBlock;
import modernity.common.block.base.*;
import modernity.common.fluid.MDFluids;
import modernity.common.item.MDItemGroups;

import java.util.ArrayList;

public class MDBlocks {
    private static final ArrayList<Entry> ENTRIES = Lists.newArrayList();
    private static final ArrayList<Entry> ENTRIES_ITEM = Lists.newArrayList();

    public static final BlockBase ROCK = blockItem( new BlockBase( "rock", Block.Properties.create( Material.ROCK, MaterialColor.STONE ).hardnessAndResistance( 1.5F, 6F ).sound( SoundType.STONE ), new Item.Properties().group( MDItemGroups.BLOCKS ) ) );
    public static final BlockNoDrop MODERN_BEDROCK = blockItem( new BlockNoDrop( "modern_bedrock", Block.Properties.create( Material.ROCK, MaterialColor.STONE ).hardnessAndResistance( - 1F, 3600000F ).sound( SoundType.STONE ), new Item.Properties().group( MDItemGroups.BLOCKS ) ) );
    public static final BlockDirt DARK_DIRT = blockItem( new BlockDirt( BlockDirt.TYPE_DIRT, Block.Properties.create( Material.GROUND, MaterialColor.DIRT ).hardnessAndResistance( 0.5F ).sound( SoundType.GROUND ), new Item.Properties().group( MDItemGroups.BLOCKS ) ) );
    public static final BlockDirt DARK_GRASS = blockItem( new BlockDirt.ColoredGrass( BlockDirt.TYPE_GRASS, Block.Properties.create( Material.GROUND, MaterialColor.GRASS ).hardnessAndResistance( 0.6F ).sound( SoundType.PLANT ), new Item.Properties().group( MDItemGroups.BLOCKS ) ) );
    public static final BlockFluid MODERNIZED_WATER = blockOnly( new BlockFluid( "modernized_water", MDFluids.MODERNIZED_WATER, Block.Properties.create( Material.WATER, MaterialColor.WATER ).doesNotBlockMovement().hardnessAndResistance( 100F ) ) );
    public static final BlockSinglePlant DARK_TALLGRASS = blockItem( new BlockSinglePlant.ColoredGrass( "dark_tall_grass", Block.Properties.create( Material.VINE, MaterialColor.GRASS ).doesNotBlockMovement().hardnessAndResistance( 0 ).sound( SoundType.PLANT ), new Item.Properties().group( MDItemGroups.BLOCKS ) ) );

    // Darkwood tree
    public static final BlockBranch STRIPPED_DARKWOOD_BRANCH = blockItem( new BlockBranch( "stripped_darkwood_branch", 0.25, Block.Properties.create( Material.WOOD, MaterialColor.WOOD ).variableOpacity().hardnessAndResistance( 1 ).sound( SoundType.WOOD ), new Item.Properties().group( MDItemGroups.BLOCKS ) ) );
    public static final BlockStripableBranch DARKWOOD_BRANCH = blockItem( new BlockStripableBranch( "darkwood_branch", 0.25, STRIPPED_DARKWOOD_BRANCH, Block.Properties.create( Material.WOOD, MaterialColor.WOOD ).variableOpacity().hardnessAndResistance( 1 ).sound( SoundType.WOOD ), new Item.Properties().group( MDItemGroups.BLOCKS ) ) );
    public static final BlockLog STRIPPED_DARKWOOD_LOG = blockItem( new BlockLog( "stripped_darkwood_log", Block.Properties.create( Material.WOOD, MaterialColor.WOOD ).hardnessAndResistance( 2 ).sound( SoundType.WOOD ), new Item.Properties().group( MDItemGroups.BLOCKS ) ) );
    public static final BlockLog DARKWOOD_LOG = blockItem( new BlockStripableLog( "darkwood_log", STRIPPED_DARKWOOD_LOG, Block.Properties.create( Material.WOOD, MaterialColor.WOOD ).hardnessAndResistance( 2 ).sound( SoundType.WOOD ), new Item.Properties().group( MDItemGroups.BLOCKS ) ) );
    public static final BlockBase STRIPPED_DARKWOOD_BARK = blockItem( new BlockBase( "stripped_darkwood_bark", Block.Properties.create( Material.WOOD, MaterialColor.WOOD ).hardnessAndResistance( 2 ).sound( SoundType.WOOD ), new Item.Properties().group( MDItemGroups.BLOCKS ) ) );
    public static final BlockBase DARKWOOD_BARK = blockItem( new BlockStripable( "darkwood_bark", STRIPPED_DARKWOOD_BARK, Block.Properties.create( Material.WOOD, MaterialColor.WOOD ).hardnessAndResistance( 2 ).sound( SoundType.WOOD ), new Item.Properties().group( MDItemGroups.BLOCKS ) ) );
    public static final BlockLeaves DARKWOOD_LEAVES = blockItem( new BlockLeaves.ColoredFoliage( "darkwood_leaves", MDBlockTags.DARKWOOD_LOG, Block.Properties.create( Material.LEAVES ).hardnessAndResistance( 0.2F ).needsRandomTick().sound( SoundType.PLANT ), new Item.Properties().group( MDItemGroups.BLOCKS ) ) );

    public static void register( IForgeRegistry<Block> registry ) {
        for( Entry e : ENTRIES ) {
            registry.register( e.getThisBlock() );
        }
    }

    public static void registerItems( IForgeRegistry<Item> registry ) {
        for( Entry e : ENTRIES_ITEM ) {
            registry.register( e.createBlockItem() );
        }
    }

    @OnlyIn( Dist.CLIENT )
    public static void registerClient( BlockColors blockColors, ItemColors itemColors ) {
        for( Entry e : ENTRIES ) {
            if( e.getThisBlock() instanceof IColoredBlock ) {
                blockColors.register( ( (IColoredBlock) e.getThisBlock() )::colorMultiplier, e.getThisBlock() );
            }
        }
        for( Entry e : ENTRIES_ITEM ) {
            if( e.getThisBlock() instanceof IColoredBlock ) {
                itemColors.register( ( (IColoredBlock) e.getThisBlock() )::colorMultiplier, e.getThisBlock() );
            }
        }
    }

    private static <T extends Entry> T blockOnly( T item ) {
        ENTRIES.add( item );
        return item;
    }

    private static <T extends Entry> T blockItem( T item ) {
        ENTRIES.add( item );
        ENTRIES_ITEM.add( item );
        return item;
    }

    public interface Entry {
        Item getBlockItem();
        Item createBlockItem();
        Block getThisBlock();
    }
}
