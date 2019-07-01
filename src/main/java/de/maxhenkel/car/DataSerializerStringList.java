package de.maxhenkel.car;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.IDataSerializer;

import java.util.Arrays;

public class DataSerializerStringList {

    public static final IDataSerializer<String[]> STRING_LIST = new IDataSerializer<String[]>() {

        @Override
        public void write(PacketBuffer packetBuffer, String[] strings) {
            packetBuffer.writeInt(strings.length);

            for (int i = 0; i < strings.length; i++) {
                packetBuffer.writeString(strings[i]);
            }
        }

        public String[] read(PacketBuffer buf) {
            int length = buf.readInt();

            String[] list = new String[length];

            for (int i = 0; i < length; i++) {
                list[i] = buf.readString(128);
            }

            return list;
        }

        public DataParameter<String[]> createKey(int id) {
            return new DataParameter(id, this);
        }

        @Override
        public String[] copyValue(String[] strings) {
            return Arrays.copyOf(strings, strings.length);
        }
    };
}
