package weblayer.vendas.DAO;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import weblayer.vendas.DTO.PedidoDTO;
import weblayer.vendas.DTO.PedidoItemDTO;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public final class PedidoDAO {

	private static SQLiteDatabase db;
	private static String tableName = "Pedido";

	public static void initialize(Context context) {
		// Abra a base de dados, cria se necessário.
		db = context.openOrCreateDatabase("weblayer.db",
				SQLiteDatabase.CREATE_IF_NECESSARY, null);

		// cria a tabela caso não exista.
		createTable(db);
	}

	private static void createTable(SQLiteDatabase database) {
		try {
			// begin the transaction
			//database.beginTransaction();

			// Create a table
			String tableSql = "CREATE TABLE IF NOT EXISTS " + tableName + " ("
					+ "id INTEGER PRIMARY KEY," + "id_web INTEGER,"
					+ "id_empresa INTEGER," + "id_filial INTEGER,"
					+ "id_cliente INTEGER," + "id_tipo INTEGER,"
					+ "id_filial_saida INTEGER," + "fl_status INTEGER,"
					+ "fl_entrada INTEGER,"

					+ "ds_cliente VARCHAR(200)," + "ds_imei VARCHAR(100),"
					+ "ds_status VARCHAR(256)," + "ds_observacao VARCHAR(256),"
					+ "ds_observacao_nf VARCHAR(256),"
					+ "ds_pedido_cliente VARCHAR(256),"

					+ "dt_inclusao_mobile TEXT," + "dt_inclusao_web TEXT,"
					+ "dt_inclusao_erp TEXT," + "dt_alteracao TEXT,"

					+ "vl_bruto DECIMAL(18,2)," + "vl_liquido DECIMAL(18,2),"
					+ "vl_desconto DECIMAL(18,2)," + "vl_peso DECIMAL(18,2),"
					+ "vl_volume INTEGER, id_vendedor INTEGER"

					+ ");";

			database.execSQL(tableSql);

			//database.setTransactionSuccessful();
		} finally {
			//database.endTransaction();
		}
	}

	private static boolean isTableEmpty(String table) {
		Cursor cursor = null;
		try {
			cursor = db.rawQuery("SELECT count(*) FROM " + table, null);

			int countIndex = cursor.getColumnIndex("count(*)");
			cursor.moveToFirst();
			int rowCount = cursor.getInt(countIndex);
			if (rowCount > 0) {
				return false;
			}

			return true;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	public static long insert(PedidoDTO objeto) {
		
		try {
			
			//db.beginTransaction();

			int id = objeto.getid();
			int id_web = objeto.getid_web();
			int id_empresa = objeto.getid_empresa();
			int id_filial = objeto.getid_filial();
			int id_cliente = objeto.getid_cliente();
			int id_tipo = objeto.getid_tipo();
			int id_filial_saida = objeto.getid_filial_saida();
			int fl_status = objeto.getfl_status();
			int fl_entrada = objeto.getfl_entrada();
			int id_vendedor = objeto.getid_vendedor();
			
			String ds_cliente = objeto.getds_cliente();
			String ds_imei = objeto.getds_imei();
			String ds_status = objeto.getds_status();
			String ds_observacao = objeto.getds_observacao();
			String ds_observacao_nf = objeto.getds_observacao_nf();
			String ds_pedido_cliente = objeto.getds_pedido_cliente();

			String dt_inclusao_mobile = weblayer.toolbox.Common
					.convertDatetoString(objeto.getdt_inclusao_mobile());
			String dt_inclusao_web = weblayer.toolbox.Common
					.convertDatetoString(objeto.getdt_inclusao_web());
			String dt_inclusao_erp = weblayer.toolbox.Common
					.convertDatetoString(objeto.getdt_inclusao_erp());
			String dt_alteracao = weblayer.toolbox.Common
					.convertDatetoString(objeto.getdt_alteracao());

			Double vl_bruto = objeto.getvl_bruto();
			Double vl_liquido = objeto.getvl_liquido();
			Double vl_desconto = objeto.getvl_desconto();
			Double vl_peso = objeto.getvl_peso();
			int vl_volume = objeto.getvl_volume();

			ContentValues initialValues = new ContentValues();
			initialValues.put("id_web", id_web);
			initialValues.put("id_empresa", id_empresa);
			initialValues.put("id_filial", id_filial);
			initialValues.put("id_cliente", id_cliente);
			initialValues.put("id_vendedor", id_vendedor);
			
			initialValues.put("id_tipo", id_tipo);
			initialValues.put("id_filial_saida", id_filial_saida);
			initialValues.put("fl_status", fl_status);
			initialValues.put("fl_entrada", fl_entrada);
			initialValues.put("ds_cliente", ds_cliente);
			initialValues.put("ds_imei", ds_imei);
			initialValues.put("ds_status", ds_status);
			initialValues.put("ds_observacao", ds_observacao);
			initialValues.put("ds_observacao_nf", ds_observacao_nf);
			
			initialValues.put("ds_pedido_cliente", ds_pedido_cliente);
			initialValues.put("dt_inclusao_mobile", dt_inclusao_mobile);
			initialValues.put("dt_inclusao_web", dt_inclusao_web);
			initialValues.put("dt_inclusao_erp", dt_inclusao_erp);
			initialValues.put("dt_alteracao", dt_alteracao);
			
			initialValues.put("vl_bruto", vl_bruto);
			initialValues.put("vl_liquido", vl_liquido);
			initialValues.put("vl_desconto", vl_desconto);
			initialValues.put("vl_peso", vl_peso);
			initialValues.put("vl_volume", vl_volume);
			
			return db.insert(tableName, null, initialValues);
			
		} catch (Exception e) {
			Log.e("Pedido", "insert", e); // log the error
			return 0;
		} finally {
			//db.endTransaction();
		}
	}

	public static void update(PedidoDTO objeto) {

		try {

			// TODO Fazer retornar inteiro

			//db.beginTransaction();

			int id = objeto.getid();
			int id_web = objeto.getid_web();
			int id_empresa = objeto.getid_empresa();
			int id_filial = objeto.getid_filial();
			int id_cliente = objeto.getid_cliente();
			int id_tipo = objeto.getid_tipo();
			int id_filial_saida = objeto.getid_filial_saida();
			int fl_status = objeto.getfl_status();
			int fl_entrada = objeto.getfl_entrada();
			int id_vendedor = objeto.getid_vendedor();
			
			String ds_cliente = objeto.getds_cliente();
			String ds_imei = objeto.getds_imei();
			String ds_status = objeto.getds_status();
			String ds_observacao = objeto.getds_observacao();
			String ds_observacao_nf = objeto.getds_observacao_nf();
			String ds_pedido_cliente = objeto.getds_pedido_cliente();

			String dt_inclusao_mobile = weblayer.toolbox.Common
					.convertDatetoString(objeto.getdt_inclusao_mobile());
			String dt_inclusao_web = weblayer.toolbox.Common
					.convertDatetoString(objeto.getdt_inclusao_web());
			String dt_inclusao_erp = weblayer.toolbox.Common
					.convertDatetoString(objeto.getdt_inclusao_erp());
			String dt_alteracao = weblayer.toolbox.Common
					.convertDatetoString(objeto.getdt_alteracao());

			Double vl_bruto = objeto.getvl_bruto();
			Double vl_liquido = objeto.getvl_liquido();
			Double vl_desconto = objeto.getvl_desconto();
			Double vl_peso = objeto.getvl_peso();
			int vl_volume = objeto.getvl_volume();

			String comandosql = "UPDATE  "
					+ tableName
					+ " SET "
					+ " id_web=?,"
					+ " id_empresa=?, id_filial=?, id_cliente=?, id_vendedor=?, id_tipo=?, id_filial_saida=?, fl_status=?, fl_entrada=?,"
					+ " ds_cliente=?, ds_imei=?, ds_status=?, ds_observacao=?, ds_observacao_nf=?, ds_pedido_cliente=?,"
					+ " dt_inclusao_mobile=?, dt_inclusao_web=?, dt_inclusao_erp=?, dt_alteracao=?,"
					+ " vl_bruto=?, vl_liquido=?, vl_desconto=?, vl_peso=?, vl_volume=?"
					+ " WHERE id=" + id;

			db.execSQL(comandosql, new Object[] { id_web, id_empresa,
					id_filial, id_cliente, id_vendedor, id_tipo, id_filial_saida, fl_status,
					fl_entrada, ds_cliente, ds_imei, ds_status, ds_observacao,
					ds_observacao_nf, ds_pedido_cliente, dt_inclusao_mobile,
					dt_inclusao_web, dt_inclusao_erp, dt_alteracao, vl_bruto,
					vl_liquido, vl_desconto, vl_peso, vl_volume });

			//db.setTransactionSuccessful();
			
		} catch (Exception e) {
			Log.e("Pedido", "update", e); // log the error
		} finally {
			//db.endTransaction();
		}

	}
	
	public static void AtualizaTotais(Context context, PedidoDTO objeto) throws Exception {
		
		double vl_bruto=0;
		double vl_desconto=0;
		double vl_liquido=0;
		double vl_peso=0;
		int vl_volume=0;
		
		weblayer.vendas.DAO.PedidoItemDAO.initialize(context);
		
		for (PedidoItemDTO item : weblayer.vendas.DAO.PedidoItemDAO.fillAll(objeto.getid())) {
			vl_bruto = vl_bruto + (item.getvl_lista() * item.getnr_quantidade());
			vl_desconto = vl_desconto + item.getvl_desconto();
			vl_liquido = vl_liquido + item.getvl_liquido();
			vl_peso=vl_peso + item.getvl_peso();
			vl_volume =vl_volume+  item.getnr_quantidade();
		}
		
		
		objeto.setvl_bruto(vl_bruto);
		objeto.setvl_desconto(vl_desconto);
		objeto.setvl_liquido(vl_liquido);
		objeto.setvl_peso(vl_peso);
		objeto.setvl_volume(vl_volume);
		
		update(objeto);
		
	}
	
	public static void updatebyweb(PedidoDTO objeto) {
		try {
			
			//db.beginTransaction();

			int id = objeto.getid();
			int id_web = objeto.getid_web();
			int id_empresa = objeto.getid_empresa();
			int id_filial = objeto.getid_filial();
			int id_cliente = objeto.getid_cliente();
			int id_tipo = objeto.getid_tipo();
			int id_filial_saida = objeto.getid_filial_saida();
			int fl_status = objeto.getfl_status();
			int fl_entrada = objeto.getfl_entrada();
			int id_vendedor = objeto.getid_vendedor();
			
			String ds_cliente = objeto.getds_cliente();
			String ds_imei = objeto.getds_imei();
			String ds_status = objeto.getds_status();
			String ds_observacao = objeto.getds_observacao();
			String ds_observacao_nf = objeto.getds_observacao_nf();
			String ds_pedido_cliente = objeto.getds_pedido_cliente();

			String dt_inclusao_mobile = weblayer.toolbox.Common
					.convertDatetoString(objeto.getdt_inclusao_mobile());
			String dt_inclusao_web = weblayer.toolbox.Common
					.convertDatetoString(objeto.getdt_inclusao_web());
			String dt_inclusao_erp = weblayer.toolbox.Common
					.convertDatetoString(objeto.getdt_inclusao_erp());
			String dt_alteracao = weblayer.toolbox.Common
					.convertDatetoString(objeto.getdt_alteracao());

			Double vl_bruto = objeto.getvl_bruto();
			Double vl_liquido = objeto.getvl_liquido();
			Double vl_desconto = objeto.getvl_desconto();
			Double vl_peso = objeto.getvl_peso();
			int vl_volume = objeto.getvl_volume();

			String comandosql = "UPDATE  "
					+ tableName
					+ " SET "
					+ " id_web=?,"
					+ " id_empresa=?, id_filial=?, id_cliente=?, id_vendedor=?, id_tipo=?, id_filial_saida=?, fl_status=?, fl_entrada=?,"
					+ " ds_cliente=?, ds_imei=?, ds_status=?, ds_observacao=?, ds_observacao_nf=?, ds_pedido_cliente=?,"
					+ " dt_inclusao_mobile=?, dt_inclusao_web=?, dt_inclusao_erp=?, dt_alteracao=?,"
					+ " vl_bruto=?, vl_liquido=?, vl_desconto=?, vl_peso=?, vl_volume=?"
					+ " WHERE id_web=" + id_web;

			db.execSQL(comandosql, new Object[] { id_web, id_empresa,
					id_filial, id_cliente, id_vendedor, id_tipo, id_filial_saida, fl_status,
					fl_entrada, ds_cliente, ds_imei, ds_status, ds_observacao,
					ds_observacao_nf, ds_pedido_cliente, dt_inclusao_mobile,
					dt_inclusao_web, dt_inclusao_erp, dt_alteracao, vl_bruto,
					vl_liquido, vl_desconto, vl_peso, vl_volume });

			//db.setTransactionSuccessful();

		} catch (Exception e) {
			Log.e("Pedido", "update", e); // log the error
		} finally {
			//db.endTransaction();
		}

	}

	public static void delete(PedidoDTO objeto) {
		try {
			//db.beginTransaction();
			String delete = "DELETE FROM  " + tableName + " WHERE id='"
					+ objeto.getid() + "'";
			db.execSQL(delete);
			
			delete = "DELETE FROM  PedidoItem WHERE id_pedido='"
					+ objeto.getid() + "'";
			db.execSQL(delete);
			
			//db.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e("Pedido", "delete", e);
		} finally {
			//db.endTransaction();
		}
	}

	public static void TruncateTable() {
		try {
			//db.beginTransaction();
			String delete = " DROP TABLE IF EXISTS " + tableName;
			db.execSQL(delete);
			//db.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e("Pedido", "delete", e); // log the error
		} finally {
			//db.endTransaction();
		}
	}

	public static PedidoDTO Get(int id) {

		Cursor cursor = null;

		try {

			cursor = db.rawQuery("SELECT * FROM  " + tableName + " where id="
					+ id, null);

			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				return (cursorToObject(cursor));
			}

			return null;
		} catch (Exception e) {
			Log.e("Pedido", "Get", e); // log the error
			return null;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	public static PedidoDTO Get(int id_empresa, int id_vendedor,String ds_imei, java.util.Date date ) {

		Cursor cursor = null;

		try {
			
			String comando = "SELECT * FROM  " + tableName + " where id_empresa=" + id_empresa + " and id_vendedor=" + id_vendedor 
					+ " and ds_imei='" + ds_imei + "' and dt_inclusao_mobile='" + weblayer.toolbox.Common.convertDatetoString(date) + "'";
			
			cursor = db.rawQuery(comando, null);

			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				return (cursorToObject(cursor));
			}

			return null;
		} catch (Exception e) {
			Log.e("Pedido", "Get", e); // log the error
			return null;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}
	
	public static int GetMobileID() {

		Cursor cursor = null;

		cursor = db.rawQuery("SELECT MAX(id) FROM  " + tableName, null);
		
		int ID=0; 
		
		if (cursor.getCount() > 0) 
		{
			cursor.moveToFirst();
			ID = cursor.getInt(0);
		} 

		if (cursor != null)
			cursor.close();

		return ID;
		
	}

	public static PedidoDTO GetWeb(int id_web) {

		Cursor cursor = null;

		try {

			cursor = db.rawQuery("SELECT * FROM  " + tableName
					+ " where id_web=" + id_web, null);

			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				return (cursorToObject(cursor));
			}

			return null;
		} catch (Exception e) {
			Log.e("Pedido", "Get", e); // log the error
			return null;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	public static List<PedidoDTO> fillAll() throws Exception {
		Cursor cursor = null;

		try {
			List<PedidoDTO> all = new ArrayList<PedidoDTO>();

			cursor = db.rawQuery("SELECT * FROM  " + tableName + " order by dt_inclusao_mobile desc", null);

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				do {
					all.add(cursorToObject(cursor));
					cursor.moveToNext();

				} while (!cursor.isAfterLast());
			}

			return all;

		} 
		
		catch (Exception e) 
		{
			Log.e("Pedido", "fill", e);  // log the error
			return null;
		}
		finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}
	
	public static List<PedidoDTO> fillUpload(int id_pedido) throws Exception {
		Cursor cursor = null;
		
		
		try {
			
			List<PedidoDTO> all = new ArrayList<PedidoDTO>();
			if (id_pedido==0)	
				cursor = db.rawQuery("SELECT * FROM  " + tableName + " where fl_status in (1) " , null);
			else
				cursor = db.rawQuery("SELECT * FROM  " + tableName + " where fl_status in (1) and id = " + id_pedido , null);
				
			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				do {
					
					PedidoDTO pedido = cursorToObject(cursor);
					
					List<weblayer.vendas.DTO.PedidoItemDTO> itens = weblayer.vendas.DAO.PedidoItemDAO.fillAll(pedido.getid());
					
					pedido.setItens((ArrayList<PedidoItemDTO>)itens);
					
					all.add(pedido);
					
					cursor.moveToNext();

				} while (!cursor.isAfterLast());
			}
			
			return all;

		} 
		
		catch (Exception e) 
		{
			Log.e("Pedido", "fill", e);  // log the error
			return null;
		}
		finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}
	
	private static PedidoDTO cursorToObject(Cursor cursor) {

		PedidoDTO objeto = new PedidoDTO();

		objeto.setid(cursor.getInt(cursor.getColumnIndex("id")));
		objeto.setid_web(cursor.getInt(cursor.getColumnIndex("id_web")));
		objeto.setid_empresa(cursor.getInt(cursor.getColumnIndex("id_empresa")));

		objeto.setid_filial(cursor.getInt(cursor.getColumnIndex("id_filial")));
		objeto.setid_cliente(cursor.getInt(cursor.getColumnIndex("id_cliente")));
		
		objeto.setid_vendedor(cursor.getInt(cursor.getColumnIndex("id_vendedor")));
		
		objeto.setds_cliente(cursor.getString(cursor.getColumnIndex("ds_cliente")));
		objeto.setds_imei(cursor.getString(cursor.getColumnIndex("ds_imei")));
		
		objeto.setds_pedido_cliente(cursor.getString(cursor
				.getColumnIndex("ds_pedido_cliente")));
		objeto.setds_status(cursor.getString(cursor.getColumnIndex("ds_status")));
		objeto.setds_observacao(cursor.getString(cursor
				.getColumnIndex("ds_observacao")));
		objeto.setds_observacao_nf(cursor.getString(cursor
				.getColumnIndex("ds_observacao_nf")));

		objeto.setid_tipo(cursor.getInt(cursor.getColumnIndex("id_tipo")));
		objeto.setid_filial_saida(cursor.getInt(cursor
				.getColumnIndex("id_filial_saida")));
		objeto.setfl_status(cursor.getInt(cursor.getColumnIndex("fl_status")));
		objeto.setfl_entrada(cursor.getInt(cursor.getColumnIndex("fl_entrada")));

		objeto.setdt_inclusao_mobile(weblayer.toolbox.Common
				.convertStringtoDate(cursor.getString(cursor
						.getColumnIndex("dt_inclusao_mobile"))));
		objeto.setdt_inclusao_web(weblayer.toolbox.Common
				.convertStringtoDate(cursor.getString(cursor
						.getColumnIndex("dt_inclusao_web"))));
		objeto.setdt_inclusao_erp(weblayer.toolbox.Common
				.convertStringtoDate(cursor.getString(cursor
						.getColumnIndex("dt_inclusao_erp"))));
		objeto.setdt_alteracao(weblayer.toolbox.Common
				.convertStringtoDate(cursor.getString(cursor
						.getColumnIndex("dt_alteracao"))));

		objeto.setvl_bruto(cursor.getDouble(cursor.getColumnIndex("vl_bruto")));
		objeto.setvl_liquido(cursor.getDouble(cursor
				.getColumnIndex("vl_liquido")));
		objeto.setvl_desconto(cursor.getDouble(cursor
				.getColumnIndex("vl_desconto")));
		objeto.setvl_peso(cursor.getDouble(cursor.getColumnIndex("vl_peso")));
		objeto.setvl_volume(cursor.getInt(cursor.getColumnIndex("vl_volume")));

		return objeto;
	}

}
