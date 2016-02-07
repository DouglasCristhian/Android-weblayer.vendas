package weblayer.vendas.DAO;

import java.lang.Exception;
import java.util.List;
import java.util.ArrayList;

import weblayer.vendas.DTO.ClienteGrupoProdutoDTO;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public final class ClienteGrupoProdutoDAO {

	private static SQLiteDatabase db;
	private static String tableName="ClienteGrupoProduto";
	
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
					+ "id_empresa INTEGER,"
					+ "id_filial INTEGER," 
					+ "id_cliente INTEGER,"
					+ "id_categ1 INTEGER," 
					+ "id_categ2 INTEGER,"
					+ "id_categ3 INTEGER," 
					+ "id_filial_faturamento INTEGER,"
					+ "fl_trocafilial INTEGER," 
					+ "fl_ativo INTEGER,"
					+ "nr_diasentrega INTEGER" + ");";

			database.execSQL(tableSql);

			//database.setTransactionSuccessful();
		} finally {
			//database.endTransaction();
		}
	}
	
	public static void TruncateTable()
	{
		try
		{
			//db.beginTransaction();
			String delete = " DROP TABLE IF EXISTS " + tableName;
			db.execSQL(delete);
			//db.setTransactionSuccessful();
		}
		catch (Exception e) 
		{
			Log.e("ClienteGrupoProduto", "truncate table", e);  // log the error
		}
		finally
		{
			//db.endTransaction();
		}
		
	}
	
	@SuppressWarnings("unused")
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

	public static void insert(ClienteGrupoProdutoDTO objeto) {
		try {
			
			//db.beginTransaction();

			int id = objeto.getid();
			int id_empresa = objeto.getid_empresa();
			int id_filial = objeto.getid_filial();
			int id_cliente = objeto.getid_cliente();
			int id_categ1 = objeto.getid_categ1();
			int id_categ2 = objeto.getid_categ2();
			int id_categ3 = objeto.getid_categ3();
			int id_filial_faturamento = objeto.getid_filial_faturamento();
			int fl_trocafilial = objeto.getfl_trocafilial();
			int fl_ativo = objeto.getfl_ativo();
			int nr_diasentrega = objeto.getnr_diasentrega();

			String comandosql = "INSERT INTO  " + tableName + " "
					+ " (id, id_empresa, id_filial, id_cliente, id_categ1, id_categ2, id_categ3, id_filial_faturamento, fl_trocafilial, fl_ativo, nr_diasentrega) VALUES "
					+ " (?,?,?,?,?,?,?,?,?,?,?);";

			db.execSQL(comandosql, new Object[] { id, id_empresa, id_filial,
					id_cliente, id_categ1, id_categ2, id_categ3,
					id_filial_faturamento, fl_trocafilial, fl_ativo,
					nr_diasentrega });

			//db.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e("ClienteGrupoProduto", "insert", e); // log the error
		} finally {
			//db.endTransaction();
		}
	}

	public static void update(ClienteGrupoProdutoDTO objeto) {
		
		try {
			
			//TODO Fazer retornar inteiro
			
			//db.beginTransaction();
			
			int id = objeto.getid();
			int id_empresa = objeto.getid_empresa();
			int id_filial = objeto.getid_filial();
			int id_cliente = objeto.getid_cliente();
			int id_categ1 = objeto.getid_categ1();
			int id_categ2 = objeto.getid_categ2();
			int id_categ3 = objeto.getid_categ3();
			int id_filial_faturamento = objeto.getid_filial_faturamento();
			int fl_trocafilial = objeto.getfl_trocafilial();
			int fl_ativo = objeto.getfl_ativo();
			int nr_diasentrega = objeto.getnr_diasentrega();

			String comandosql = "UPDATE  " + tableName + " SET "
					+ " id_empresa=?, id_filial=?, id_cliente=?, id_categ1=?,"
					+ " id_categ2=?, id_categ3=?, id_filial_faturamento=?,"
					+ " fl_trocafilial=?, fl_ativo=?, nr_diasentrega=? "
					+ " WHERE id=" + id;

			db.execSQL(comandosql, new Object[] { id_empresa, id_filial,
					id_cliente, id_categ1, id_categ2, id_categ3,
					id_filial_faturamento, fl_trocafilial, fl_ativo,
					nr_diasentrega });

			//db.setTransactionSuccessful();
			
		} catch (Exception e) {
			Log.e("ClienteGrupoProduto", "update", e); // log the error
		} finally {
			//db.endTransaction();
		}

	}

	public static void delete(ClienteGrupoProdutoDTO objeto) {
		try {
			//db.beginTransaction();
			String delete = "DELETE FROM  " + tableName + " WHERE id='"
					+ objeto.getid() + "'";
			db.execSQL(delete);
			//db.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e("ClienteGrupoProduto", "delete", e); // log the error
		} finally {
			//db.endTransaction();
		}
	}

	public static ClienteGrupoProdutoDTO Get(int id) {

		Cursor cursor = null;

		try {

			cursor = db.rawQuery("SELECT * FROM  " + tableName + " Where id="
					+ id, null);

			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				return (cursorToObject(cursor));
			}

			return null;
		} catch (Exception e) {
			Log.e("ClienteGrupoProduto", "Get", e); // log the error
			return null;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}
	
	public static ClienteGrupoProdutoDTO Get(int id_cliente, int categ1, int categ3) {

		Cursor cursor = null;

		try {

			cursor = db.rawQuery("SELECT * FROM  " + tableName + " Where fl_ativo=1 and id_cliente=" + id_cliente 
									+ " and id_categ1=" + categ1 
									+ " and id_categ3=" + categ3, null);

			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				return (cursorToObject(cursor));
			}

			return null;
		} catch (Exception e) {
			Log.e("ClienteGrupoProduto", "Get", e); // log the error
			return null;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}
	
	public static ClienteGrupoProdutoDTO Get(int id_cliente, int categ1, int categ2, int categ3) {

		Cursor cursor = null;

		try {

			cursor = db.rawQuery("SELECT * FROM  " + tableName + " Where fl_ativo=1 and id_cliente=" + id_cliente 
									+ " and id_categ1=" + categ1 
									+ " and id_categ2=" + categ2 
									+ " and id_categ3=" + categ3, null);

			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				return (cursorToObject(cursor));
			}

			return null;
		} catch (Exception e) {
			Log.e("ClienteGrupoProduto", "Get", e); // log the error
			return null;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}
	
	public static List<ClienteGrupoProdutoDTO> fillAll() {
		Cursor cursor = null;
		try {
			List<ClienteGrupoProdutoDTO> all = new ArrayList<ClienteGrupoProdutoDTO>();

			cursor = db.rawQuery("SELECT * FROM  " + tableName + " ", null);

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				do {
					all.add(cursorToObject(cursor));
					cursor.moveToNext();

				} while (!cursor.isAfterLast());
			}

			return all;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	private static ClienteGrupoProdutoDTO cursorToObject(Cursor cursor) {

		ClienteGrupoProdutoDTO objeto = new ClienteGrupoProdutoDTO();

		objeto.setid(cursor.getInt(cursor.getColumnIndex("id")));
		objeto.setid_empresa(cursor.getInt(cursor.getColumnIndex("id_empresa")));
		objeto.setid_filial(cursor.getInt(cursor.getColumnIndex("id_filial")));
		objeto.setid_cliente(cursor.getInt(cursor.getColumnIndex("id_cliente")));
		objeto.setid_categ1(cursor.getInt(cursor.getColumnIndex("id_categ1")));
		objeto.setid_categ2(cursor.getInt(cursor.getColumnIndex("id_categ2")));
		objeto.setid_categ3(cursor.getInt(cursor.getColumnIndex("id_categ3")));
		objeto.setid_filial_faturamento(cursor.getInt(cursor.getColumnIndex("id_filial_faturamento")));
		objeto.setfl_trocafilial(cursor.getInt(cursor.getColumnIndex("fl_trocafilial")));
		objeto.setfl_ativo(cursor.getInt(cursor.getColumnIndex("fl_ativo")));
		objeto.setnr_diasentrega(cursor.getInt(cursor.getColumnIndex("nr_diasentrega")));

		return objeto;
	}

}
