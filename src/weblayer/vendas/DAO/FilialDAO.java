package weblayer.vendas.DAO;

import java.lang.Exception;
import java.util.List;
import java.util.ArrayList;

import weblayer.vendas.DTO.FilialDTO;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public final class FilialDAO {

	private static SQLiteDatabase db;
	private static String tableName="Filial";
	
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
					+ "id_empresa INTEGER, "
					+ "id_filial VARCHAR(10)," 
					+ "ds_filial VARCHAR(60), "
					+ "ds_cnpj VARCHAR(20)," 
					+ "ds_codmun VARCHAR(15) "
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

	public static void insert(FilialDTO objeto) {
		try {
			//db.beginTransaction();

			int id = objeto.getid();
			int id_empresa = objeto.getid_empresa();
			String id_filial = objeto.getid_filial();
			String ds_filial = objeto.getds_filial();
			String ds_cnpj = objeto.getds_cnpj();
			String ds_codmun = objeto.getds_codmun();

			String comandosql = "INSERT INTO  " + tableName + " "
					+ " (id, id_empresa, id_filial, ds_filial, ds_cnpj, ds_codmun) VALUES "
					+ " (?,?,?,?,?,?);";

			db.execSQL(comandosql, new Object[] { id, id_empresa, id_filial, ds_filial, ds_cnpj, ds_codmun});

			//db.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e("Filial", "insert", e); 
		} finally {
			//db.endTransaction();
		}
	}

	public static void update(FilialDTO objeto) {
		try {
			
			//TODO Fazer retornar inteiro
			
			//db.beginTransaction();

			int id = objeto.getid();
			int id_empresa = objeto.getid_empresa();
			String id_filial = objeto.getid_filial();
			String ds_filial = objeto.getds_filial();
			String ds_cnpj = objeto.getds_cnpj();
			String ds_codmun = objeto.getds_codmun();

			String comandosql = "UPDATE  " + tableName + " SET "
					+ " id_empresa=?, id_filial=?, ds_filial=?, ds_cnpj=?,"
					+ " ds_codmun=? " + " WHERE id=" + id;

			db.execSQL(comandosql, new Object[] { id_empresa, id_filial, ds_filial, ds_cnpj, ds_codmun });

			//db.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e("Filial", "update", e); 
		} finally {
			//db.endTransaction();
		}

	}

	public static void delete(FilialDTO objeto) {
		try {
			//db.beginTransaction();
			String delete = "DELETE FROM  " + tableName + " WHERE id='" + objeto.getid()
					+ "'";
			db.execSQL(delete);
			//db.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e("Filial", "delete", e); 
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
			Log.e("Filial", "delete", e); 
		} finally {
			//db.endTransaction();
		}
	}

	
	public static FilialDTO Get(int id) {

		Cursor cursor = null;

		try {

			cursor = db.rawQuery("SELECT * FROM  " + tableName + " where id=" + id, null);

			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				return (cursorToObject(cursor));
			}

			return null;
		} catch (Exception e) {
			Log.e("Filial", "Get", e);
			return null;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	public static List<FilialDTO> fillAll() {
		Cursor cursor = null;
		try {
			List<FilialDTO> all = new ArrayList<FilialDTO>();

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

	public static List<FilialDTO> fillCombo() {
		Cursor cursor = null;
		try {
			List<FilialDTO> all = new ArrayList<FilialDTO>();

			cursor = db.rawQuery("select 0 id, 0 id_empresa, 0 id_filial, 'SELECIONE..' ds_filial, '' ds_cnpj, '' ds_codmun union SELECT * FROM  " + tableName + " ", null);

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
	
	private static FilialDTO cursorToObject(Cursor cursor) {

		FilialDTO objeto = new FilialDTO();

		objeto.setid(cursor.getInt(cursor.getColumnIndex("id")));
		objeto.setid_empresa(cursor.getInt(cursor.getColumnIndex("id_empresa")));
		objeto.setid_filial(cursor.getString(cursor.getColumnIndex("id_filial")));
		objeto.setds_filial(cursor.getString(cursor.getColumnIndex("ds_filial")));
		objeto.setds_cnpj(cursor.getString(cursor.getColumnIndex("ds_cnpj")));
		objeto.setds_codmun(cursor.getString(cursor.getColumnIndex("ds_codmun")));

		return objeto;
	}

}
