package br.gov.go.goiania.focoaedes.xml;


import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.List;

public abstract class TrataXml {

    private static final String TAG = "TrataXml";

    public static final String ns = null;

    public XmlPullParser parseInputStream(InputStream in) throws XmlPullParserException, IOException {

        /*InputStream inLog = in;

        BufferedReader r = new BufferedReader(new InputStreamReader(inLog));
        String x = "";
        x = r.readLine();
        String total = "";

        while(x!= null){
            total += x;
            x = r.readLine();
        }

        Log.d(TAG,"parseInputStream - total: "+total);*/

        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            if (verificaErro(parser))
                return null;
            else
                return parser;
        } finally {
            in.close();
        }

    }

    public XmlPullParser parseString(String xml) throws XmlPullParserException, IOException {

        XmlPullParser parser = Xml.newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(new StringReader(xml));
        parser.nextTag();

        return parser;

    }

    protected abstract List<?> leXml(XmlPullParser parser) throws XmlPullParserException, IOException;

    public boolean verificaErro(XmlPullParser parser) throws IOException{

        try{
            parser.require(XmlPullParser.START_TAG, ns, "msg");

            return true;

        }catch(XmlPullParserException e){

            return false;

        }
    }

    public String verificaErroString(XmlPullParser parser) throws IOException{

        try{

            Log.d(TAG, "verificaErroString");

            String retorno = "Ocorreu um erro no cadastro da solicitação!";

            parser.require(XmlPullParser.START_TAG, ns, "msg");

            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                Log.d(TAG,"verificaErroString - parser.getName()"+parser.getName());
                String name = parser.getName();
                if (name.equals("ds")) {
                    retorno = le(parser,"ds");
                } else {
                    skip(parser);
                }
            }

            return retorno;

        }catch(XmlPullParserException e){

            e.printStackTrace();

            Log.d(TAG, "XmlPullParserException - e: " + e.toString());

            return null;

        }
    }

    protected String le(XmlPullParser parser, String campo) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, campo);
        String campoR = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, campo);
        return campoR;
    }

    protected String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    protected void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

}
