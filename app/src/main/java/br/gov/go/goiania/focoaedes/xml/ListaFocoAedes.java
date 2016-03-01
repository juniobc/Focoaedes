package br.gov.go.goiania.focoaedes.xml;


import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import br.gov.go.goiania.focoaedes.entidades.FocoAedes;
import br.gov.go.goiania.focoaedes.entidades.Menssagem;

public class ListaFocoAedes extends TrataXml {

    private static final String TAG = "ListaFocoAedes";

    public List<FocoAedes> executa(InputStream in) throws IOException, XmlPullParserException {

        String retorno;

        retorno = verificaErroString(parseInputStream(in));

        if(retorno == null)
            return leXml(parseInputStream(in));
        else{
            return null;
        }

    }

    private static final String ns = null;

    @Override
    protected List<FocoAedes> leXml(XmlPullParser parser) throws XmlPullParserException, IOException {

        List<FocoAedes> retorno = new ArrayList<FocoAedes>();

        int count = 0;

        parser.require(XmlPullParser.START_TAG, ns, "dt");

        while (parser.next() != XmlPullParser.END_TAG) {

            //if(parser.next() != XmlPullParser.END_TAG){
            if(count < 20){
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                Log.d(TAG, "leXml - parser.getName(3): "+parser.getName());
                String name = parser.getName();
                if (name.equals("solicitacao")) {
                    retorno.add(leFoco(parser, "solicitacao"));
                } else {
                    skip(parser);
                }
            }

            //}

            count++;

            Log.d(TAG, "leXml - count: "+count);
        }

        return retorno;
    }

    private FocoAedes leFoco(XmlPullParser parser, String tag) throws XmlPullParserException, IOException{

        FocoAedes fc = new FocoAedes();

        parser.require(XmlPullParser.START_TAG, ns, tag);

        while (parser.next() != XmlPullParser.END_TAG) {

            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            switch(name){

                case "cd_sol":
                    fc.setCdFocoAedes(Integer.parseInt(le(parser, "cd_sol")));
                    break;

                case "dt_abertura":
                    fc.setDtAbertura(le(parser, "dt_abertura"));
                    break;

                case "hr_abertura":
                    fc.setHrAbertura(le(parser, "hr_abertura"));
                    break;

                case "status_atual":
                    fc.setStatus(le(parser, "status_atual"));
                    break;

                case "dt_status":
                    fc.setDtStatus(le(parser, "dt_status"));
                    break;

                case "hr_status":
                    fc.setHrStatus(le(parser, "hr_status"));
                    break;

                case "in_descricao_st":
                    fc.setDsFocoAedes(le(parser, "in_descricao_st"));
                    break;

                default:
                    skip(parser);
            }
        }

        Log.d(TAG, "leFoco getCdFocoAedes: "+fc.getCdFocoAedes());

        return fc;

    }
}


