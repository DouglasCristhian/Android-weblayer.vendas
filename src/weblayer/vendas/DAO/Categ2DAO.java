package weblayer.vendas.DAO;

import java.lang.Exception;
import java.util.List;
import java.util.ArrayList;

import weblayer.vendas.DTO.Categ1DTO;
import weblayer.vendas.DTO.Categ2DTO;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public final class Categ2DAO {

	private static SQLiteDatabase db;
	private static String tableName="Categ2";
	
	public static void initialize(Context context) {
		// Abra a base de dados, cria se necess�rio.
		db = context.openOrCreateDatabase("weblayer.db",
				SQLiteDatabase.CREATE_IF_NECESSARY, null);

		// cria a tabela caso n�o exista.
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
	
	public static void insertorupdate(Categ2DTO objeto)
	{
			
		Categ2DTO param = Get(objeto.getid());
		
		if (param==null)
			insert(objeto);
		else
			update(objeto);
		
	}
	
	public static void insert(Categ2DTO objeto) {
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
			Log.e("Categ2", "insert", e); // log the error
		} finally {
			//db.endTransaction();
		}
	}

	public static void update(Categ2DTO objeto) {
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
			Log.e("Categ2", "update", e); // log the error
		} finally {
			//db.endTransaction();
		}

	}

	public static void delete(Categ2DTO objeto) {
		try {
			
			//db.beginTransaction();
			String delete = "DELETE FROM  " + tableName + " WHERE id='" + objeto.getid() + "'";
			db.execSQL(delete);
			//db.setTransactionSuccessful();
			
		} catch (Exception e) {
			Log.e("Categ2", "delete", e); // log the error
		} finally {
			//db.endTransaction();
		}
	}

	public static Categ2DTO Get(int id) {

		Cursor cursor = null;

		try {

			cursor = db.rawQuery("SELECT * FROM  " + tableName + " Where id=" + id, null);

			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				return (cursorToObject(cursor));
			}

			return null;
		} 
		catch (Exception e) {
			Log.e("Categ2", "Get", e); // log the error
			return null;
		} 
		finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}
	
		
	public static List<Categ2DTO> fillAll() {
		Cursor cursor = null;
		try {
			List<Categ2DTO> all = new ArrayList<Categ2DTO>();

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
		catch (Exception e) 
		{
			Log.e("Categ2", "fill", e);  // log the error
			return null;
		}
		finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	public static List<Categ2DTO> fillCombo(int id_categ1) {
		Cursor cursor = null;
		try {
			
			List<Categ2DTO> all = new ArrayList<Categ2DTO>();
			
			cursor = db.rawQuery("select 0 id,0 id_empresa,'SELECIONE..' ds_nome union select a.id, a.id_empresa, a.ds_nome from categ2 a inner Join Produto b on a.id = b.id_categ2 where b.id_categ1=" + id_categ1 + " order by a.id", null);

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
			Log.e("Categ2", "fill", e);  // log the error
			return null;
		}
		finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}
	
	
	private static Categ2DTO cursorToObject(Cursor cursor) {

		Categ2DTO objeto = new Categ2DTO();

		objeto.setid(cursor.getInt(cursor.getColumnIndex("id")));
		objeto.setid_empresa(cursor.getInt(cursor.getColumnIndex("id_empresa")));
		objeto.setds_nome(cursor.getString(cursor.getColumnIndex("ds_nome")));

		return objeto;
	}
}
