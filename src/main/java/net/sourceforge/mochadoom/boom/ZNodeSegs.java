package net.sourceforge.mochadoom.boom;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import net.sourceforge.mochadoom.utils.C2JUtils;
import net.sourceforge.mochadoom.wad.CacheableDoomObject;

public class ZNodeSegs implements CacheableDoomObject{

    private static final byte[] DeepBSPHeader={
            'x','N','d','4',0,0,0,0
    };

    byte[] header;
    MapSegZnod[] nodes;
    int numnodes;

    public boolean formatOK(){
        return Arrays.equals(header, DeepBSPHeader);
    }

    public MapSegZnod[] getNodes(){
        return nodes;
    }

    @Override
    public void unpack(ByteBuffer buf) throws IOException {
        int length = buf.capacity();

        // Too short, not even header.
        if (length < 8) return;

        numnodes = (length - 8) / MapNodeV4.sizeOf();

        if (length < 1) return;

        buf.get(header); // read header

        nodes = C2JUtils.createArrayOfObjects(MapSegZnod.class, length);

        for (int i = 0; i < length; i++) {
            nodes[i].unpack(buf);
        }
    }
}