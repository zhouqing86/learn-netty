package com.zq.netty.msgpack;

import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessagePackTest {
    public static void main(String[] args){
        List<String> src = new ArrayList<>();
        src.add("msgpack");
        src.add("kumofs");
        src.add("viver");
        MessagePack messagePack = new MessagePack();
        try {
            byte [] raw = messagePack.write(src);
            List<String> dst1 = messagePack.read(raw, Templates.tList(Templates.TString));
            System.out.println(dst1.get(0));
            System.out.println(dst1.get(1));
            System.out.println(dst1.get(2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
