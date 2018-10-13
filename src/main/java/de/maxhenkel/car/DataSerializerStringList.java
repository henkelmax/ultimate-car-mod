package de.maxhenkel.car;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializer;
import java.io.IOException;
import java.util.Arrays;

public class DataSerializerStringList {

    public static final DataSerializer<String[]> STRING_LIST = new DataSerializer<String[]>() {

        @Override
        public void write(PacketBuffer packetBuffer, String[] strings) {
            packetBuffer.writeInt(strings.length);

            for(int i=0; i<strings.length; i++){
                packetBuffer.writeString(strings[i]);
            }
        }

        public String[] read(PacketBuffer buf) throws IOException {
            int length=buf.readInt();

            String[] list=new String[length];

            for(int i=0; i<length; i++){
                list[i]=buf.readString(128);
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
