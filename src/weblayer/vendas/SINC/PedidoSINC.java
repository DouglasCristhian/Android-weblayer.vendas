package weblayer.vendas.SINC;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalDate;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import weblayer.vendas.DAO.PedidoDAO;
import weblayer.vendas.DAO.PedidoItemDAO;
import weblayer.vendas.DTO.PedidoDTO;
import weblayer.vendas.DTO.PedidoItemDTO;

import android.content.Context;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class PedidoSINC {

	public final static String SOAP_ACTION = "http://www.weblayer.com.br/";

	public final static String WSDL_TARGET_NAMESPACE = "http://www.weblayer.com.br/";

	public PedidoSINC() {
	}

	public static void EnviarPedidos(Context context, String SOAP_ADDRESS, int empresa, String vendedor,
			String dispositivo, ArrayList<weblayer.vendas.DTO.PedidoDTO> Pedidos) throws Exception {

		String OPERATION_NAME = "IncluirPedidos";

		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,
				OPERATION_NAME);

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Gson gson = gsonBuilder.create();
		
		
		String pedidos = (gson.toJson(Pedidos));

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
		pi.setName("json");
		pi.setValue(pedidos);
		pi.setType(String.class);
		request.addProperty(pi);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;

		envelope.setOutputSoapObject(request);

        MarshalDate _MarshalDate = new MarshalDate();
        _MarshalDate.register(envelope);

		HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
		Object response = null;

		try {

			httpTransport.call(SOAP_ACTION + OPERATION_NAME, envelope);
			response = envelope.getResponse();

		} catch (Exception e) {
			if (!weblayer.toolbox.Common.isNetWorkActive(context))
				throw new Exception("Sem conex�o com a internet.");
			else
				throw e;
		}

	}

	public static ArrayList<weblayer.vendas.DTO.PedidoDTO> RetornaLista(
			Context context, String SOAP_ADDRESS, int empresa, String vendedor, String dispositivo, String datasinc) throws Exception {

		String OPERATION_NAME = "RetornaPedidos";

		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,
				OPERATION_NAME);

		PropertyInfo pi = new PropertyInfo();
		pi.setName("empresa");
		pi.setValue(empresa);
		pi.setType(Integer.class);
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
				throw new Exception("Sem conex�o com a internet.");
			else
				throw e;
		}

		if (response == null)
			return null;

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
				.create();
		ArrayList<weblayer.vendas.DTO.PedidoDTO> Objetos;
		try {
			Type collectionType = new TypeToken<ArrayList<weblayer.vendas.DTO.PedidoDTO>>() {
			}.getType();
			Objetos = gson.fromJson(response.toString(), collectionType);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return Objetos;
	}
	
	public static void EnviarPedido(Context context, String SOAP_ADDRESS, int id_empresa, String Vendedor, String Imei, int id_pedido) throws Exception {
		
		
		// Enviar pedidos ***********************************************************
		List<weblayer.vendas.DTO.PedidoDTO> pedidos = weblayer.vendas.DAO.PedidoDAO.fillUpload(id_pedido);
		if (pedidos!=null)
			EnviarPedidos(context, SOAP_ADDRESS, id_empresa, Vendedor, Imei,(ArrayList<weblayer.vendas.DTO.PedidoDTO>) pedidos);
		// ..........................................................................
		
		
	}
	
	public static void Sincronizar(Context context, String SOAP_ADDRESS, int id_empresa, String Vendedor, String Imei, String UltimaSinc) throws Exception {

		// Enviar pedidos
		List<weblayer.vendas.DTO.PedidoDTO> pedidos = weblayer.vendas.DAO.PedidoDAO.fillUpload(0);
		if (pedidos!=null)
			EnviarPedidos(context, SOAP_ADDRESS, id_empresa, Vendedor, Imei,(ArrayList<weblayer.vendas.DTO.PedidoDTO>) pedidos);
		// ..........................................................................
		
		
		// Receber pedido
		// ..........................................................................
		ArrayList<PedidoDTO> itens = RetornaLista(context, SOAP_ADDRESS, id_empresa, Vendedor, Imei, UltimaSinc);
		Long id_pedido;
		
		if (itens == null)
			return;

		for (PedidoDTO pedido : itens) {

			id_pedido = (long) 0;

			PedidoDTO buffer = PedidoDAO.GetWeb(pedido.getid_web());
			if (buffer == null) {

				// Buscar pelo empresa, vendedor, imei, datainclusao
				buffer = PedidoDAO.Get(pedido.getid_empresa(),
						pedido.getid_vendedor(), pedido.getds_imei(),
						pedido.getdt_inclusao_mobile());
				if (buffer != null) 
				{ // caso encontrar pedido inclu�do pelo dispositivo.
					
					// atualizar o c�digo pedido web
					buffer.setid_web(pedido.getid_web());
					// atualizar o status para 4
					buffer.setfl_status(pedido.getfl_status());
					PedidoDAO.update(buffer);

				} else 
				{
					// Pedido novo.......................................
					id_pedido = PedidoDAO.insert(pedido);
					pedido.setid(id_pedido.intValue());
					// Inlcuir os itens ************************************
					for (PedidoItemDTO item : pedido.getItens()) {
						item.setid_pedido(pedido.getid());
						PedidoItemDAO.insert(item);
					}
				}
			} 
			else 
			{
				PedidoDAO.updatebyweb(pedido);
				PedidoItemDAO.deletebypedido(buffer.getid());
				pedido.setid(buffer.getid());
				
				// Inlcuir os itens ************************************
				for (PedidoItemDTO item : pedido.getItens()) {
					item.setid_pedido(buffer.getid());
					PedidoItemDAO.insert(item);
				}

			}

		}

	}

}