package de.maxhenkel.car;

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializer;
import java.io.IOException;
import java.util.Arrays;

public class DataSerializerItemList {

    public static final DataSerializer<ItemStack[]> ITEM_LIST = new DataSerializer<ItemStack[]>() {

        @Override
        public void write(PacketBuffer packetBuffer, ItemStack[] itemStacks) {

            if(itemStacks==null){
                packetBuffer.writeInt(-1);
                return;
            }

            packetBuffer.writeInt(itemStacks.length);

            for(int i=0; i<itemStacks.length; i++){
                packetBuffer.writeItemStack(itemStacks[i]);
            }
        }

        public ItemStack[] read(PacketBuffer buf) throws IOException {
            int length=buf.readInt();

            if(length<0){
                return null;
            }

            ItemStack[] list=new ItemStack[length];

            for(int i=0; i<length; i++){
                list[i]=buf.readItemStack();
            }

            return list;
        }

        public DataParameter<ItemStack[]> createKey(int id) {
            return new DataParameter(id, this);
        }

        @Override
        public ItemStack[] copyValue(ItemStack[] itemStacks) {
            if(itemStacks==null){
                return null;
            }
            return Arrays.copyOf(itemStacks, itemStacks.length);
        }
    };
}
