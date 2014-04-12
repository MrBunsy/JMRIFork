/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jmri.jmrix.SimpleDCC;

import java.nio.ByteBuffer;

/**
 *
 * @author Luke
 */
public class MakePacket {
    
    //TODO integrate with PiDCC
    public static int MESSAGE_SIZE = (8+4), MAX_PRIORITY=255, CUSTOM_PACKET = 4;

    
    public static ByteBuffer createByteBuffer(byte[] packet, int repeats){
         ByteBuffer bb = ByteBuffer.allocate(MESSAGE_SIZE);
        //sync bytes
        bb.put((byte)0xff);
        bb.put((byte)0xcc);
        bb.put((byte)0xcc);
        bb.put((byte)0xff);
        //message type byte
        bb.put((byte)(CUSTOM_PACKET & 0xff));
        //priority byte (repeats atm)
        bb.put((byte) (repeats & 0xff));
        
        //insert the bytes
        for(byte b : packet){
            bb.put(b);
        }
        
        //fill in the rest of the message with zeroes
        while(bb.remaining() > 0){
            //System.out.println("pad");
            bb.put((byte)0x00);
        }
        bb.flip();
        
        return bb;
    }
}
