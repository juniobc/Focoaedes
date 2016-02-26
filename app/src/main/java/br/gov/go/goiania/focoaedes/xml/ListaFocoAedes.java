package br.gov.go.goiania.focoaedes.xml;


import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import br.gov.go.goiania.focoaedes.entidades.FocoAedes;

public class ListaFocoAedes extends TrataXml {

    private static final String TAG = "ListaFocoAedes";

    private static final String ns = null;

    public List<FocoAedes> executa(InputStream in) throws IOException, XmlPullParserException {

        return leXml(parseInputStream(in));
    }


    @Override
    protected List<FocoAedes> leXml(XmlPullParser parser) throws XmlPullParserException, IOException {

        if (verificaErro(parser))
            return null;

        List<FocoAedes> retorno = null;

        parser.require(XmlPullParser.START_TAG, ns, "dt");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("solicitacao")) {
                retorno.add(leFoco(parser, "solicitacao"));
            } else {
                skip(parser);
            }
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


