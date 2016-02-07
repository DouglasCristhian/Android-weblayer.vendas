package weblayer.vendas.SINC;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import weblayer.vendas.DAO.TabelaPrecoDAO;
import weblayer.vendas.DTO.PedidoItemDTO;
import weblayer.vendas.DTO.TabelaPrecoDTO;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class TabelaPrecoSINC {

	public final static String SOAP_ACTION = "http://www.weblayer.com.br/";

	public final static String WSDL_TARGET_NAMESPACE = "http://www.weblayer.com.br/";

	//public final static String SOAP_ADDRESS = "http://weblayercloud.com/weblayerservice/Vendas.asmx";
	//public final static String SOAP_ADDRESS = "http://10.0.2.2:52634/Vendas.asmx";

	public TabelaPrecoSINC() {
	}

	

	public static ArrayList<TabelaPrecoDTO> RetornaLista(Context context, String SOAP_ADDRESS,  int empresa,
			String vendedor, String dispositivo, String datasinc) throws Exception {

		String OPERATION_NAME = "RetornaTabelaPreco";

		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,
				OPERATION_NAME);

		PropertyInfo pi = new PropertyInfo();
		pi.setName("empresa");
		pi.setValue(empresa);
		pi.setType(Integer.class);
		request.addProperty(pi);

		pi = new PropertyInfo();
		pi.setName("dispositivo");
		pi.setValue(dispositivo);
		pi.setType(String.class);
		request.addProperty(pi);

		pi = new PropertyInfo();
		pi.setName("vendedor");
		pi.setValue(vendedor);
		pi.setType(String.class);
		request.addProperty(pi);

		pi = new PropertyInfo();
		pi.setName("datasinc");
		pi.setValue(datasinc);
		pi.setType(String.class);
		request.addProperty(pi);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;

		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
		Object response = null;

		try {

			httpTransport.call(SOAP_ACTION + OPERATION_NAME, envelope);
			response = envelope.getResponse();

		} catch (Exception e) {
			if (!weblayer.toolbox.Common.isNetWorkActive(context))
				throw new Exception("Sem conexão com a internet.");
			else
				throw e;
		}

		if (response == null)
			return null;

		GsonBuilder gsonb = new GsonBuilder();
		Gson gson = gsonb.create();
		ArrayList<weblayer.vendas.DTO.TabelaPrecoDTO> Objetos;

		try {

			Type collectionType = new TypeToken<ArrayList<weblayer.vendas.DTO.TabelaPrecoDTO>>() {
			}.getType();
			Objetos = gson.fromJson(response.toString(), collectionType);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return Objetos;
	}

	public static void Sincronizar(Context context, String SOAP_ADDRESS, int id_empresa, String Vendedor, String Imei,
			String UltimaSinc) throws Exception {

		

		ArrayList<TabelaPrecoDTO> itens = RetornaLista(context, SOAP_ADDRESS, id_empresa, Vendedor, Imei, UltimaSinc);

		if (itens == null)
			return;

		for (TabelaPrecoDTO item : itens) {

			TabelaPrecoDTO buffer = TabelaPrecoDAO.Get(item.getid());
			if (buffer == null)
				TabelaPrecoDAO.insert(item);
			else
				TabelaPrecoDAO.update(item);

		}

	}

	
	
}