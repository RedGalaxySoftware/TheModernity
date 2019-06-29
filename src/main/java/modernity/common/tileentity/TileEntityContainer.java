/*
 * Copyright (c) 2019 RedGalaxy & co.
 * Licensed under the Apache Licence v2.0.
 * Do not redistribute.
 *
 * By  : RGSW
 * Date: 6 - 29 - 2019
 */

package modernity.common.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;

public abstract class TileEntityContainer extends TileEntityLockable {
    private final NonNullList<ItemStack> stacks = NonNullList.withSize( getSizeInventory(), ItemStack.EMPTY );

    protected TileEntityContainer( TileEntityType<?> type ) {
        super( type );
    }

    @Override
    public boolean isEmpty() {
        for( ItemStack stack : stacks ) {
            if( ! stack.isEmpty() ) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getStackInSlot( int index ) {
        return stacks.get( index );
    }

    @Override
    public ItemStack decrStackSize( int index, int count ) {
        markDirty();
        return ItemStackHelper.getAndSplit( stacks, index, count );
    }

    @Override
    public ItemStack removeStackFromSlot( int index ) {
        markDirty();
        return ItemStackHelper.getAndRemove( stacks, index );
    }

    @Override
    public void setInventorySlotContents( int index, ItemStack stack ) {
        stacks.set( index, stack );
        if( ! stack.isEmpty() && stack.getCount() > getInventoryStackLimit() ) {
            stack.setCount( getInventoryStackLimit() );
        }
        markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer( EntityPlayer player ) {
        return true;
    }

    @Override
    public void openInventory( EntityPlayer player ) {

    }

    @Override
    public void closeInventory( EntityPlayer player ) {

    }

    @Override
    public boolean isItemValidForSlot( int index, ItemStack stack ) {
        return true;
    }

    @Override
    public int getField( int id ) {
        return 0;
    }

    @Override
    public void setField( int id, int value ) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        stacks.clear();
        markDirty();
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Nullable
    @Override
    public ITextComponent getCustomName() {
        return null;
    }

    @Override
    public NBTTagCompound write( NBTTagCompound compound ) {
        super.write( compound );
        NBTTagList list = new NBTTagList();
        for( ItemStack stack : stacks ) {
            list.add( stack.write( new NBTTagCompound() ) );
        }
        compound.put( "items", list );
        return compound;
    }

    @Override
    public void read( NBTTagCompound compound ) {
        super.read( compound );
        NBTTagList list = compound.getList( "items", 10 );
        for( int i = 0; i < list.size(); i++ ) {
            stacks.set( i, ItemStack.read( list.getCompound( i ) ) );
        }
    }
}
