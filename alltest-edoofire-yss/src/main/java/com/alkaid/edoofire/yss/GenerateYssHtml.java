package com.alkaid.edoofire.yss;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;

public class GenerateYssHtml {

    public static void main(String[] args) throws Exception {

        FileWriter writer = new FileWriter("index.html", true);

        String title = "红星照耀中国";
        String sound = "hongxing";
        String speaker = "一刀烟火";
        String after = "栗子";
        int count = 100;
        writer.append("<!DOCTYPE html>\n");
        writer.append("\t<html>\n");
        writer.append("\t\t<body>\n");
        writer.append("\t\t<h3>" + title + "</h3>\n");
        writer.append("\t\t<h4>主播：" + speaker + "</h4>\n\n");
        writer.append("\t\t<h4>后期制作：" + after + "</h4>\n\n");

        for (int i = 1; i < count + 1; i ++) {
            String filename = String.format("%s-%03d", title, i);
            String soundname = String.format("%s-%03d", sound, i);
            writer.append("\t\t<h5>" + filename + "</h5>\n");
            writer.append("\t\t\t<audio id=\"myAudio1\" controls>\n");
            writer.append("\t\t\t<source src=\""+ soundname + ".mp3\" type=\"audio/mpeg\">\n");
            writer.append("\t\t\tYour browser does not support the audio element.\n");
            writer.append("\t\t</audio>\n\n");
        }

        writer.append("\t\t<body>\n");
        writer.append("\t<html>\n");

        writer.flush();
        writer.close();
    }
}
