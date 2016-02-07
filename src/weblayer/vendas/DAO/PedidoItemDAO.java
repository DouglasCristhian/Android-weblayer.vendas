package weblayer.vendas.DAO;

import java.util.ArrayList;
import java.util.List;

import weblayer.vendas.DTO.PedidoItemDTO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public final class PedidoItemDAO {

	private static SQLiteDatabase db;
	private static String tableName="PedidoItem";
	
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
					+ "id INTEGER PRIMARY KEY," 
					+ "id_pedido INTEGER,"
					+ "id_empresa INTEGER,"
					+ "id_filial INTEGER,"
					+ "id_produto INTEGER," 
					
					+ "nr_quantidade INTEGER,"
					+ "fl_cancelado INTEGER," 
					+ "nr_dias_condpagto INTEGER,"
					+ "id_filial_saida INTEGER,"
					+ "id_condpagto INTEGER,"
					
					+ "id_produto_retaguarda VARCHAR(20)," 
					+ "ds_produto_nome_completo VARCHAR(400),"
					+ "ds_produto_nome_curto VARCHAR(200)," 
					+ "ds_numnf VARCHAR(10),"
					+ "ds_serienf VARCHAR(3),"
					+ "id_pedido_retaguarda VARCHAR(20),"
					+ "id_item_retaguarda VARCHAR(10),"
					+ "ds_observacao VARCHAR(256),"

					+ "dt_faturamento TEXT," 
					+ "dt_entrega TEXT,"
					+ "dt_prevfaturamento TEXT,"
					+ "dt_preventrega TEXT,"

					+ "vl_lista DECIMAL(18,2)," 
					+ "vl_unitario DECIMAL(18,2),"
					+ "vl_desconto DECIMAL(18,2)," 
					+ "vl_liquido DECIMAL(18,2),"
					+ "vl_peso DECIMAL(18,2),"
					+ "vl_desc_perc DECIMAL(18,2)"
					
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

	public static void insert(PedidoItemDTO objeto) {
		try {
			
			//db.beginTransaction();

			int id = objeto.getid();
			
			int id_pedido = objeto.getid_pedido();
			int id_empresa = objeto.getid_empresa();
			int id_filial = objeto.getid_filial();
			int id_produto= objeto.getid_produto();
			int nr_quantidade= objeto.getnr_quantidade();
			int fl_cancelado= objeto.getfl_cancelado();
			int nr_dias_condpagto= objeto.getnr_dias_condpagto();
			int id_filial_saida= objeto.getid_filial_saida();
			int id_condpagto= objeto.getid_condpagto();
			
			String id_produto_retaguarda = objeto.getid_produto_retaguarda();
			String ds_produto_nome_completo = objeto.getds_produto_nome_completo();
			String ds_produto_nome_curto = objeto.getds_produto_nome_curto();
			String ds_numnf = objeto.getds_numnf();
			String ds_serienf = objeto.getds_serienf();
			String id_pedido_retaguarda = objeto.getid_pedido_retaguarda();
			String id_item_retaguarda = objeto.getid_item_retaguarda();
			String ds_observacao = objeto.getds_observacao();
			
			String dt_faturamento=weblayer.toolbox.Common.convertDatetoString(objeto.getdt_faturamento());
			String dt_entrega =weblayer.toolbox.Common.convertDatetoString(objeto.getdt_entrega());
			String dt_prevfaturamento=weblayer.toolbox.Common.convertDatetoString(objeto.getdt_prevfaturamento());
			String dt_preventrega=weblayer.toolbox.Common.convertDatetoString(objeto.getdt_preventrega());
			
			Double vl_lista = objeto.getvl_lista();
			Double vl_unitario = objeto.getvl_unitario();
			Double vl_desconto = objeto.getvl_desconto();
			Double vl_liquido = objeto.getvl_liquido();
			Double vl_peso = objeto.getvl_peso();
			Double vl_desc_perc = objeto.getvl_desc_perc();
			
			String comandosql = "INSERT INTO  " + tableName + " "
					+ " (" 
					+ " id_pedido, id_empresa, id_filial, id_produto, nr_quantidade, fl_cancelado, nr_dias_condpagto, id_filial_saida, id_condpagto, " 
					+ " id_produto_retaguarda, ds_produto_nome_completo, ds_produto_nome_curto, ds_numnf, ds_serienf, id_pedido_retaguarda, id_item_retaguarda, ds_observacao, "
					+ " dt_faturamento, dt_entrega, dt_prevfaturamento, dt_preventrega, "
					+ " vl_lista, vl_unitario, vl_desconto, vl_liquido, vl_peso, vl_desc_perc "
					+ " ) VALUES "
					+ " (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

			db.execSQL(comandosql, new Object[] { id_pedido, id_empresa, id_filial, id_produto, nr_quantidade, fl_cancelado, nr_dias_condpagto, id_filial_saida, id_condpagto,
					id_produto_retaguarda, ds_produto_nome_completo, ds_produto_nome_curto,  ds_numnf, ds_serienf, id_pedido_retaguarda, id_item_retaguarda, ds_observacao, 
					dt_faturamento, dt_entrega, dt_prevfaturamento, dt_preventrega,
					vl_lista, vl_unitario, vl_desconto, vl_liquido, vl_peso, vl_desc_perc});

			//db.setTransactionSuccessful();
			
		} catch (Exception e) {
			Log.e("Pedido", "insert", e); // log the error
		} finally {
			//db.endTransaction();
		}
	}

	public static void update(PedidoItemDTO objeto) {
		try {
			
			//db.beginTransaction();
			
			int id = objeto.getid();
			
			int id_pedido = objeto.getid_pedido();
			int id_empresa = objeto.getid_empresa();
			int id_filial = objeto.getid_filial();
			int id_produto= objeto.getid_produto();
			int nr_quantidade= objeto.getnr_quantidade();
			int fl_cancelado= objeto.getfl_cancelado();
			int nr_dias_condpagto= objeto.getnr_dias_condpagto();
			int id_filial_saida= objeto.getid_filial_saida();
			int id_condpagto= objeto.getid_condpagto();
			
			String id_produto_retaguarda = objeto.getid_produto_retaguarda();
			String ds_produto_nome_completo = objeto.getds_produto_nome_completo();
			String ds_produto_nome_curto = objeto.getds_produto_nome_curto();
			String ds_numnf = objeto.getds_numnf();
			String ds_serienf = objeto.getds_serienf();
			String id_pedido_retaguarda = objeto.getid_pedido_retaguarda();
			String id_item_retaguarda = objeto.getid_item_retaguarda();
			String ds_observacao = objeto.getds_observacao();
			
			String dt_faturamento=weblayer.toolbox.Common.convertDatetoString(objeto.getdt_faturamento());
			String dt_entrega =weblayer.toolbox.Common.convertDatetoString(objeto.getdt_entrega());
			String dt_prevfaturamento=weblayer.toolbox.Common.convertDatetoString(objeto.getdt_prevfaturamento());
			String dt_preventrega=weblayer.toolbox.Common.convertDatetoString(objeto.getdt_preventrega());
			
			Double vl_lista = objeto.getvl_lista();
			Double vl_unitario = objeto.getvl_unitario();
			Double vl_desconto = objeto.getvl_desconto();
			Double vl_liquido = objeto.getvl_liquido();
			Double vl_peso = objeto.getvl_peso();
			Double vl_desc_perc = objeto.getvl_desc_perc();
			
			String comandosql = "UPDATE  " + tableName + " SET " 
					+ " id_pedido=?, id_empresa=?, id_filial=?, id_produto=?, nr_quantidade=?, fl_cancelado=?, nr_dias_condpagto=?, id_filial_saida=?, id_condpagto=?, "
					+ " id_produto_retaguarda=?, ds_produto_nome_completo=?, ds_produto_nome_curto=?, ds_numnf=?, ds_serienf=?, id_pedido_retaguarda=?, id_item_retaguarda=?, ds_observacao=?, "
					+ " dt_faturamento=?, dt_entrega=?, dt_prevfaturamento=?, dt_preventrega=?,"
					+ " vl_lista=?, vl_unitario=?, vl_desconto=?, vl_liquido=?, vl_peso=?, vl_desc_perc=?"
					+ " WHERE id=" + id; 
					
			db.execSQL(comandosql, new Object[] { id_pedido, id_empresa, id_filial, id_produto, nr_quantidade, fl_cancelado, nr_dias_condpagto, id_filial_saida, id_condpagto,
					id_produto_retaguarda, ds_produto_nome_completo, ds_produto_nome_curto,  ds_numnf, ds_serienf, id_pedido_retaguarda, id_item_retaguarda, ds_observacao, 
					dt_faturamento, dt_entrega, dt_prevfaturamento, dt_preventrega,
					vl_lista, vl_unitario, vl_desconto, vl_liquido, vl_peso, vl_desc_perc});

			//db.setTransactionSuccessful();
			
		} catch (Exception e) {
			Log.e("Pedido", "update", e); // log the error
		} finally {
			//db.endTransaction();
		}

	}
		
	public static void deletebypedido(int id_pedido) {
		try {
			//db.beginTransaction();
			String delete = "DELETE FROM  " + tableName + " WHERE id_pedido='" + id_pedido
					+ "'";
			db.execSQL(delete);
			//db.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e("Pedido", "delete", e);
		} finally {
			//db.endTransaction();
		}
	}
	
	public static void delete(PedidoItemDTO objeto) {
		try {
			//db.beginTransaction();
			String delete = "DELETE FROM  " + tableName + " WHERE id='" + objeto.getid()
					+ "'";
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
	
	public static PedidoItemDTO Get(int id) {

		Cursor cursor = null;

		try {

			cursor = db.rawQuery("SELECT * FROM  " + tableName + " where id=" + id, null);

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

	public static PedidoItemDTO Get(int id_pedido, int id_produto) {

		Cursor cursor = null;

		try {

			cursor = db.rawQuery("SELECT * FROM  " + tableName + " where id_pedido=" + id_pedido + " and id_produto=" + id_produto, null);

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
	
	
	
	public static double GetPesoTotal(int id_pedido, int id_item) {

		Cursor cursor = null;

		try {

			cursor = db.rawQuery("SELECT sum(vl_peso) as peso FROM " + tableName + " where id_pedido=" + id_pedido + " and id <>" + id_item, null);

			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				return (cursor.getDouble(cursor.getColumnIndex("peso")));
			}

			return 0;
		} catch (Exception e) {
			Log.e("Pedido", "GetPesoTotal", e); // log the error
			return 0;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}
	
	
	public static PedidoItemDTO GetWeb(int id_web) {

		Cursor cursor = null;

		try {

			cursor = db.rawQuery("SELECT * FROM  " + tableName + " where id_web=" + id_web, null);

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
	
	
	public static List<PedidoItemDTO> fillAll(int id_pedido) throws Exception {
		Cursor cursor = null;
		
		try {
			List<PedidoItemDTO> all = new ArrayList<PedidoItemDTO>();

			cursor = db.rawQuery("SELECT * FROM  " + tableName + " where id_pedido =" + id_pedido, null);

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
			Log.e("PedidoItem", "fill", e);  // log the error
			return null;
		}
		finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	private static PedidoItemDTO cursorToObject(Cursor cursor) {

		PedidoItemDTO objeto = new PedidoItemDTO();

		objeto.setid(cursor.getInt(cursor.getColumnIndex("id")));
		objeto.setid_pedido(cursor.getInt(cursor.getColumnIndex("id_pedido")));
		objeto.setid_empresa(cursor.getInt(cursor.getColumnIndex("id_empresa")));
		objeto.setid_filial(cursor.getInt(cursor.getColumnIndex("id_filial")));
		objeto.setid_produto(cursor.getInt(cursor.getColumnIndex("id_produto")));
		objeto.setnr_quantidade(cursor.getInt(cursor.getColumnIndex("nr_quantidade")));
		objeto.setfl_cancelado(cursor.getInt(cursor.getColumnIndex("fl_cancelado")));
		objeto.setnr_dias_condpagto(cursor.getInt(cursor.getColumnIndex("nr_dias_condpagto")));
		objeto.setid_filial_saida(cursor.getInt(cursor.getColumnIndex("id_filial_saida")));
		objeto.setid_condpagto(cursor.getInt(cursor.getColumnIndex("id_condpagto")));
		
		
		objeto.setid_produto_retaguarda(cursor.getString(cursor.getColumnIndex("id_produto_retaguarda")));
		objeto.setds_produto_nome_completo(cursor.getString(cursor.getColumnIndex("ds_produto_nome_completo")));
		objeto.setds_produto_nome_curto(cursor.getString(cursor.getColumnIndex("ds_produto_nome_curto")));
		objeto.setds_numnf(cursor.getString(cursor.getColumnIndex("ds_numnf")));
		objeto.setds_serienf(cursor.getString(cursor.getColumnIndex("ds_serienf")));
		objeto.setid_pedido_retaguarda(cursor.getString(cursor.getColumnIndex("id_pedido_retaguarda")));
		objeto.setid_item_retaguarda(cursor.getString(cursor.getColumnIndex("id_item_retaguarda")));
		objeto.setds_observacao(cursor.getString(cursor.getColumnIndex("ds_observacao")));
		
		
		objeto.setdt_faturamento(weblayer.toolbox.Common.convertStringtoDate(cursor.getString(cursor.getColumnIndex("dt_faturamento"))));
		objeto.setdt_entrega(weblayer.toolbox.Common.convertStringtoDate(cursor.getString(cursor.getColumnIndex("dt_entrega"))));
		objeto.setdt_prevfaturamento(weblayer.toolbox.Common.convertStringtoDate(cursor.getString(cursor.getColumnIndex("dt_prevfaturamento"))));
		objeto.setdt_preventrega(weblayer.toolbox.Common.convertStringtoDate(cursor.getString(cursor.getColumnIndex("dt_preventrega"))));
		
		objeto.setvl_lista(cursor.getDouble(cursor.getColumnIndex("vl_lista")));
		objeto.setvl_unitario(cursor.getDouble(cursor.getColumnIndex("vl_unitario")));
		objeto.setvl_desconto(cursor.getDouble(cursor.getColumnIndex("vl_desconto")));
		objeto.setvl_liquido(cursor.getDouble(cursor.getColumnIndex("vl_liquido")));
		objeto.setvl_peso(cursor.getDouble(cursor.getColumnIndex("vl_peso")));
		objeto.setvl_desc_perc(cursor.getDouble(cursor.getColumnIndex("vl_desc_perc")));
		
		return objeto;
	}
	
	
}
