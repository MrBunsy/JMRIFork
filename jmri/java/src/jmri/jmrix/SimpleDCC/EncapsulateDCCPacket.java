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
public class EncapsulateDCCPacket {
    
    //TODO integrate with PiDCC
    public static int SYNC_BYTES = 4, MESSAGE_SIZE = (10 + 4), MAX_PRIORITY=255, CUSTOM_PACKET = 4,
            MAX_DATA_BYTES = 6;//note one more than on AVR because this includes address

    
    public static ByteBuffer createByteBuffer(byte[] packet, int repeats){
        
        /**
         * 
         * Message format (in bytes):
         * sync 1 0xff
         * sync 2 0xcc
         * sync 3 0xcc
         * sync 4 0xff
         * command type (4 = custom packet)
         * priority (unused atm)
         * packet - 6 bytes
         * databytes - number of valid bytes in packet
         * repeat (also unused atm)
         */
        
         ByteBuffer bb = ByteBuffer.allocate(MESSAGE_SIZE);
        //sync bytes
        bb.put((byte)0xff);
        bb.put((byte)0xcc);
        bb.put((byte)0xcc);
        bb.put((byte)0xff);
        //message type byte
        bb.put((byte)(CUSTOM_PACKET & 0xff));
        //priority byte (repeats atm)
        bb.put((byte) (1 & 0xff));
        
        //insert the bytes
        for(byte b : packet){
            bb.put(b);
        }
        
        //fill rest of data with zeros
        for(int i=0;i<MAX_DATA_BYTES-packet.length;i++){
            bb.put((byte)0);
        }
        //data bytes
        bb.put((byte)(packet.length & 0xff));
        
        //repeat
        bb.put((byte)(repeats & 0xff));
        
        //fill in the rest of the message with zeroes
//        while(bb.remaining() > 0){
//            //System.out.println("pad");
//            bb.put((byte)0x00);
//        }
        bb.flip();
        
        return bb;
    }
}
