package com.example.jute;

import lombok.Data;
import org.apache.jute.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannel;
import java.util.concurrent.CompletableFuture;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Data
public class GperHeader implements Record {

    private long sessionId;
    private String type;

    public GperHeader() {
    }

    public GperHeader(long sessionId, String type) {
        this.sessionId = sessionId;
        this.type = type;
    }

    @Override
    public void serialize(OutputArchive archive, String tag) throws IOException {
        archive.startRecord(this,tag);
        archive.writeLong(this.sessionId,"sessionId");
        archive.writeString(this.type,"type");
        archive.endRecord(this,tag);
    }

    @Override
    public void deserialize(InputArchive archive, String tag) throws IOException {
        archive.startRecord(tag);
        this.sessionId=archive.readLong("sessionId");
        this.type=archive.readString("type");
        archive.endRecord(tag);
    }

    public static void main(String[] args) throws IOException {
        final  String tag="header";
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        BinaryOutputArchive boa= BinaryOutputArchive.getArchive(baos);
        new GperHeader(123456,"GperCreate").serialize(boa,tag);

        ByteBuffer byteBuffer=ByteBuffer.wrap(baos.toByteArray());
        System.out.println(byteBuffer);

        ByteArrayInputStream bbis=new ByteArrayInputStream(baos.toByteArray());
        BinaryInputArchive bbia=BinaryInputArchive.getArchive(bbis);
        GperHeader header=new GperHeader();
        header.deserialize(bbia,tag);
        System.out.println(header);
        CompletableFuture future;
        /*future.thenAccept(()->{
            ///-------
        }).get(); //同步阻塞*/

//        future.join(); //同步阻塞
    }
}
