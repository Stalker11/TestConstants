package com.olegel.professor.constants;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ParserXml {
    private static Context cont;
    private final static String TAG = ParserXml.class.getSimpleName();
    private final String ns = null;
    private List<ElectrostaticPermeability> parseObject;
    private InputStream in;
    private ElectrostaticPermeability parse;
    public static void setContext(Context context) {
        cont = context;
    }

    public List parseTable() {
        try {
            Log.d(TAG, "onCreate: " + cont.getAssets().open("electrostatic_permeability.xml"));
            in = cont.getAssets().open("electrostatic_permeability.xml");
            parseObject = new ArrayList<>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            parse = new ElectrostaticPermeability();
            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                Log.d(TAG, "onCreate: " + parser.getName());
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }

                String name = parser.getName();
                if (name.equals("substance")) {
                    String substance = readLatitude(parser);
                    Log.d(TAG, "onCreate: lat " + substance);
                   parse.setSubstance(substance);
                }  Log.d(TAG, "onCreate: next step ");
                if (name.equals("value1")) {
                    String value = readLongtitude(parser);
                    Log.d(TAG, "onCreate: lon " + value);
                    if(parse.getSubstance() != null) {
                        parse.setValue(value);
                        parseObject.add(parse);
                        parse = new ElectrostaticPermeability();
                    }
                   /* if (name.equals("sub")){
                        String sub = readSub(parser);
                        Log.d(TAG, "parseTable: "+sub);
                    }*/
                } /*else if (name.equals("time")) {
                    String time = readTime(parser);
                    Log.d(TAG, "onCreate: time" + time);
                }*/ else {
                    //   skip(parser);

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return parseObject;
    }

    private String readLatitude(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "substance");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "substance");
        return title;
    }

    private String readLongtitude(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "value1");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "value1");
        return title;
    }

    private String readTime(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "time");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "time");
        return title;
    }

    private String readSub(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "sub");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "sub");
        return title;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        StringBuilder build = new StringBuilder();
        if (parser.next() == XmlPullParser.TEXT) {
            build.append(parser.getText());
            parser.nextTag();
            /*if(parser.getName().equals("sub")){
             parser.require(XmlPullParser.START_TAG, ns, "sub");
                if (parser.next() == XmlPullParser.TEXT) {

                    build.append("<sub>")
                            .append(parser.getText())
                            .append("</sub>");
                    Log.d(TAG, "readText: "+Html.fromHtml("<sub>52</sub>"));
                    parser.nextTag();
                }
                parser.require(XmlPullParser.END_TAG, ns, "sub");
                parser.nextTag();
            }*/

        }
        return build.toString();
    }
}
