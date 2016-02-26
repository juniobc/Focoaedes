package br.gov.go.goiania.focoaedes.xml;


import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

public class RetornoCadFoco extends TrataXml {

    private static final String ns = null;

    public RetornoCadFoco(){}

    public String executa(String xml) throws IOException, XmlPullParserException {

        if(leXml(parse(xml)) != null)
            return leXml(parse(xml)).get(0).toString();
        else
            return null;
    }

    @Override
    public List<String> leXml(XmlPullParser parser) throws XmlPullParserException, IOException{

        List<String> retorno = null;

        parser.require(XmlPullParser.START_TAG, ns, "dt");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("nr")) {
                retorno.set(0,le(parser,"nr"));
            } else {
                skip(parser);
            }
        }

        return retorno;

    }

}
