package weblayer.vendas.DAO;

import java.lang.Exception;
import java.util.List;
import java.util.ArrayList;

import weblayer.vendas.DTO.Categ1DTO;
import weblayer.vendas.DTO.Categ3DTO;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public final class Categ3DAO {

	private static SQLiteDatabase db;
	private static String tableName="Categ3";
	
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
					+ "id INTEGER PRIMARY KEY," + "id_empresa INTEGER, ds_nome VARCHAR(60) );";

			database.execSQL(tableSql);

			//database.setTransactionSuccessful();
		
		} 
		finally 
		{
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
	
	public static void insertorupdate(Categ3DTO objeto)
	{
			
		Categ3DTO param = Get(objeto.getid());
		
		if (param==null)
			insert(objeto);
		else
			update(objeto);
		
	}
	
	public static void insert(Categ3DTO objeto) {
		try {
			
			//db.beginTransaction();
			
			int id = objeto.getid();
			int id_empresa = objeto.getid_empresa();
			String ds_nome= objeto.getds_nome();

			String comandosql = "INSERT INTO  " + tableName + " "
					+ " (id, id_empresa, ds_nome) VALUES "
					+ " (?,?,?);";

			db.execSQL(comandosql, new Object[] { id, id_empresa, ds_nome});

			//db.setTransactionSuccessful();
			
		} catch (Exception e) {
			Log.e("Categ3", "insert", e); // log the error
		} finally {
			//db.endTransaction();
		}
	}

	public static void update(Categ3DTO objeto) {
		try {
			
			//db.beginTransaction();
			
			//TODO Fazer retornar inteiro
			
			int id = objeto.getid();
			int id_empresa = objeto.getid_empresa();
			String ds_nome = objeto.getds_nome();

			String comandosql = "UPDATE  " + tableName + " SET " + " ds_nome=? WHERE id=?";

			db.execSQL(comandosql, new Object[] { ds_nome, id});

			//db.setTransactionSuccessful();
			
		} catch (Exception e) {
			Log.e("Categ3", "update", e); // log the error
		} finally {
			//db.endTransaction();
		}

	}

	public static void delete(Categ3DTO objeto) {
		try {
			
			//db.beginTransaction();
			String delete = "DELETE FROM  " + tableName + " WHERE id='" + objeto.getid() + "'";
			db.execSQL(delete);
			//db.setTransactionSuccessful();
			
		} catch (Exception e) {
			Log.e("Categ3", "delete", e); // log the error
		} finally {
			//db.endTransaction();
		}
	}

	public static Categ3DTO Get(int id) {

		Cursor cursor = null;

		try {

			cursor = db.rawQuery("SELECT * FROM  " + tableName + " Where id=" + id, null);

			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				return (cursorToObject(cursor));
			}

			return null;
		} catch (Exception e) {
			Log.e("Categ3", "Get", e); // log the error
			return null;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}
	
		
	public static List<Categ3DTO> fillAll() {
		Cursor cursor = null;
		try {
			List<Categ3DTO> all = new ArrayList<Categ3DTO>();

			cursor = db.rawQuery("SELECT * FROM  " + tableName + " ", null);

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				do {
					all.add(cursorToObject(cursor));
					cursor.moveToNext();

				} while (!cursor.isAfterLast());
			}

			return all;
		} 
		catch (Exception e) {
			Log.e("Categ3", "Get", e); // log the error
			return null;
		}
		finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}
	
	public static List<Categ3DTO> fillCombo() {
		Cursor cursor = null;
		try {
			List<Categ3DTO> all = new ArrayList<Categ3DTO>();

			
			cursor = db.rawQuery("select 0 id,0 id_empresa,'SELECIONE..' ds_nome union select a.id, a.id_empresa, a.ds_nome from categ3 a inner Join Produto b on a.id = b.id_categ3 order by a.id", null);

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				do {
					all.add(cursorToObject(cursor));
					cursor.moveToNext();

				} while (!cursor.isAfterLast());
			}

			return all;
		} 
		catch (Exception e) {
			Log.e("Categ3", "Get", e); // log the error
			return null;
		}
		finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}
	
	private static Categ3DTO cursorToObject(Cursor cursor) {

		Categ3DTO objeto = new Categ3DTO();

		objeto.setid(cursor.getInt(cursor.getColumnIndex("id")));
		objeto.setid_empresa(cursor.getInt(cursor.getColumnIndex("id_empresa")));
		objeto.setds_nome(cursor.getString(cursor.getColumnIndex("ds_nome")));

		return objeto;
	}
}
