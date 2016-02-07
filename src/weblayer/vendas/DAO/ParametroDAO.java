package weblayer.vendas.DAO;

import java.lang.Exception;
import java.util.List;
import java.util.ArrayList;

import weblayer.vendas.DTO.ParametroDTO;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public final class ParametroDAO {

	private static SQLiteDatabase db;
	private static String tableName="Parametro";
	
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
					+ "id INTEGER PRIMARY KEY," + "id_empresa INTEGER,"
					+ "ds_chave VARCHAR(60)," + "ds_valor VARCHAR(100)" + ");";

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
	
	public static void insertorupdate(ParametroDTO objeto)
	{
			
		ParametroDTO param = GetByKey(objeto.getds_chave());
		
		if (param==null)
			insert(objeto);
		else
			update(objeto);
		
	}
	
	public static void insert(ParametroDTO objeto) {
		try {
			//db.beginTransaction();

			
			int id_empresa = objeto.getid_empresa();
			String ds_chave = objeto.getds_chave();
			String ds_valor = objeto.getds_valor();

			String comandosql = "INSERT INTO  " + tableName + " "
					+ " (id_empresa, ds_chave, ds_valor) VALUES "
					+ " (?,?,?);";

			db.execSQL(comandosql, new Object[] { id_empresa, ds_chave, ds_valor });

			//db.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e("Parametro", "insert", e); // log the error
		} finally {
			//db.endTransaction();
		}
	}

	public static void update(ParametroDTO objeto) {
		try {
			
			//db.beginTransaction();
			
			//TODO Fazer retornar inteiro
			
			int id = objeto.getid();
			int id_empresa = objeto.getid_empresa();
			String ds_chave = objeto.getds_chave();
			String ds_valor = objeto.getds_valor();

			String comandosql = "UPDATE  " + tableName + " SET " + " ds_valor=?"
					+ " WHERE id_empresa=? and ds_chave=?";

			db.execSQL(comandosql, new Object[] { ds_valor, id_empresa, ds_chave });

			//db.setTransactionSuccessful();
			
		} catch (Exception e) {
			Log.e("Parametro", "update", e); // log the error
		} finally {
			//db.endTransaction();
		}

	}

	public static void delete(ParametroDTO objeto) {
		try {
			//db.beginTransaction();
			String delete = "DELETE FROM  " + tableName + " WHERE id='" + objeto.getid()
					+ "'";
			db.execSQL(delete);
			//db.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e("Parametro", "delete", e); // log the error
		} finally {
			//db.endTransaction();
		}
	}

	public static ParametroDTO Get(int id) {

		Cursor cursor = null;

		try {

			cursor = db.rawQuery("SELECT * FROM  " + tableName + " Where id=" + id, null);

			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				return (cursorToObject(cursor));
			}

			return null;
		} catch (Exception e) {
			Log.e("Parametro", "Get", e); // log the error
			return null;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}
	
	public static ParametroDTO GetByKey(String ds_chave) {

		Cursor cursor = null;

		try {

			cursor = db.rawQuery("SELECT * FROM  " + tableName + " Where ds_chave='" + ds_chave + "'", null);

			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				return (cursorToObject(cursor));
			}

			return null;
		} catch (Exception e) {
			Log.e("Parametro", "Get", e); // log the error
			return null;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}
	
	public static String GetByKey(String ds_chave, String defaultvalue) {

		Cursor cursor = null;

		try {

			cursor = db.rawQuery("SELECT * FROM  " + tableName + " Where ds_chave='" + ds_chave + "'", null);

			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				return (cursorToObject(cursor).getds_valor());
			}

			return defaultvalue;
		} catch (Exception e) {
			Log.e("Parametro", "Get", e); // log the error
			return defaultvalue;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}
	
	public static List<ParametroDTO> fillAll() {
		Cursor cursor = null;
		try {
			List<ParametroDTO> all = new ArrayList<ParametroDTO>();

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

	private static ParametroDTO cursorToObject(Cursor cursor) {

		ParametroDTO objeto = new ParametroDTO();

		objeto.setid(cursor.getInt(cursor.getColumnIndex("id")));
		objeto.setid_empresa(cursor.getInt(cursor.getColumnIndex("id_empresa")));
		objeto.setds_chave(cursor.getString(cursor.getColumnIndex("ds_chave")));
		objeto.setds_valor(cursor.getString(cursor.getColumnIndex("ds_valor")));

		return objeto;
	}
}
