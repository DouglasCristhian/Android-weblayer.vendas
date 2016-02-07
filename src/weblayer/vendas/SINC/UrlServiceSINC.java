package weblayer.vendas.SINC;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import weblayer.vendas.DAO.Categ1DAO;
import weblayer.vendas.DAO.ParametroDAO;
import weblayer.vendas.DAO.ProdutoDAO;
import weblayer.vendas.DTO.Categ1DTO;
import weblayer.vendas.DTO.ParametroDTO;
import weblayer.vendas.DTO.ProdutoDTO;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class UrlServiceSINC {
	
	public final static String SOAP_ACTION = "http://www.weblayer.com.br/";

	public final static String WSDL_TARGET_NAMESPACE = "http://www.weblayer.com.br/";

	//Server de produção..
	public final static String SOAP_ADDRESS = "http://server.weblayer.com.br/WeblayerService/UrlService.asmx";
 
	
	public UrlServiceSINC() {
	}

	public static weblayer.vendas.DTO.UrlServiceDTO RetornaLista(Context context, String codigo) throws Exception {
		
		String OPERATION_NAME="RetornaWebServerURIVendas";
		
		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);
		    
		PropertyInfo pi = new PropertyInfo();
		pi.setName("codigo");
		pi.setValue(codigo);
		pi.setType(String.class);
		request.addProperty(pi);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
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
		
		if (response==null)
			return null;
		
		GsonBuilder gsonb = new GsonBuilder();
		Gson gson = gsonb.create();
		weblayer.vendas.DTO.UrlServiceDTO Objetos;
		
		try
		{
		    
			Type collectionType = new TypeToken<weblayer.vendas.DTO.UrlServiceDTO>(){}.getType();
			Objetos = gson.fromJson(response.toString(), collectionType);
			
		}
		catch(Exception e)
		{
		    e.printStackTrace();
		    return null;
		}
		
		return Objetos;
	}
	
	public static void Sincronizar(Context context, String conta) throws Exception
	{
		
		weblayer.vendas.DTO.UrlServiceDTO itens = RetornaLista(context, conta);
		
		if (itens==null)
			return;
		
		ParametroDTO parametro=null; 
		
		//Atualizar os parametros do códig da empresa e endereço do webservice.
		parametro = new ParametroDTO();
    	parametro.setid_empresa(0);
    	parametro.setds_chave("ID_EMPRESA");
    	parametro.setds_valor(String.valueOf(itens.getid_empresa()));
    	ParametroDAO.insertorupdate(parametro);
		
    	parametro = new ParametroDTO();
    	parametro.setid_empresa(0);
    	parametro.setds_chave("WEBSERVICE");
    	parametro.setds_valor(itens.getds_webservice());
    	ParametroDAO.insertorupdate(parametro);
    	
    	parametro = new ParametroDTO();
    	parametro.setid_empresa(0);
    	parametro.setds_chave("EMPRESA");
    	parametro.setds_valor(itens.getds_empresa());
    	ParametroDAO.insertorupdate(parametro);
    	
		
	}
	
}