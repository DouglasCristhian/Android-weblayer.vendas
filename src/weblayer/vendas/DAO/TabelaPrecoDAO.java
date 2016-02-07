package weblayer.vendas.DAO;

import java.util.ArrayList;
import java.util.List;

import weblayer.vendas.DTO.TabelaPrecoDTO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public final class TabelaPrecoDAO {

	private static SQLiteDatabase db;
	private static String tableName="TabelaPreco";

	public static void initialize(Context context)
	{
		//Abra a base de dados, cria se necessário.
		db = context.openOrCreateDatabase("weblayer.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
		
		//cria a tabela caso não exista.
		createTable(db);
	}
	 
	private static void createTable(SQLiteDatabase database)
	{
		try
		{
			
			//database.beginTransaction();
			
			String tableSql = "CREATE TABLE IF NOT EXISTS " + tableName 
					+ " ("
					+ "id INTEGER PRIMARY KEY," 
					+ "id_empresa INTEGER," 
					+ "id_filial INTEGER," 
					+ "id_produto INTEGER, "
					+ "nr_limite_formar_preco INTEGER, "
					+ "ds_opcoes_cond_pagto VARCHAR(30),"
					+ "id_tabela_preco VARCHAR(10),"
					+ "vl_preco1 DECIMAL(14,2)," 
					+ "vl_preco2 DECIMAL(14,2)"
					+ ");";
			
			database.execSQL(tableSql);

			//database.setTransactionSuccessful();
		} 
		finally
		{
			//database.endTransaction();
		}
	}
	
	@SuppressWarnings("unused")
	private static boolean isTableEmpty(String table)
	{
		Cursor cursor = null;
		try
		{
			cursor = db.rawQuery("SELECT count(*) FROM " + table, null);
			
			int countIndex = cursor.getColumnIndex("count(*)");
			cursor.moveToFirst();
			int rowCount = cursor.getInt(countIndex);
			if(rowCount > 0)
			{
				return false;
			}
			
			return true;
		}
		finally
		{
			if(cursor != null)
			{
				cursor.close();
			}
		}
	}
	
	public static void insert(TabelaPrecoDTO objeto)
	{
		try
		{
			//db.beginTransaction();
			
			int id = objeto.getid();
			int id_empresa = objeto.getid_empresa();
			int id_filial = objeto.getid_filial();
			int id_produto = objeto.getid_produto();
			int nr_limite_formar_preco = objeto.getnr_limite_formar_preco();
			
			String ds_opcoes_cond_pagto = objeto.getds_opcoes_cond_pagto();
			String id_tabela_preco = objeto.getid_tabela_preco();
			
			Double vl_preco1  = objeto.getvl_preco1();
			Double vl_preco2  = objeto.getvl_preco2();
			
			
			String insert = "INSERT INTO  " + tableName + " "
				+ " (id, id_empresa, id_filial, id_produto, nr_limite_formar_preco, ds_opcoes_cond_pagto, id_tabela_preco, vl_preco1, vl_preco2) VALUES " 
				+ " (?, ?, ?, ?, ?, ?, ?, ?, ?);";
			
			db.execSQL(insert,new Object[]{id, id_empresa, id_filial, id_produto, nr_limite_formar_preco, ds_opcoes_cond_pagto, id_tabela_preco, vl_preco1, vl_preco2});
			
			//db.setTransactionSuccessful();
		}
		catch (Exception e) 
		{
			Log.e("TabelaPreco", "insert", e);  // log the error
		}
		finally
		{
			//db.endTransaction();
		}
	}

	public static void update(TabelaPrecoDTO objeto)
	{
		try
		{
			//db.beginTransaction();
			
			//TODO Fazer retornar inteiro
			
			int id = objeto.getid();
			int id_empresa = objeto.getid_empresa();
			int id_filial = objeto.getid_filial();
			int id_produto = objeto.getid_produto();
			int nr_limite_formar_preco = objeto.getnr_limite_formar_preco();
			
			String ds_opcoes_cond_pagto = objeto.getds_opcoes_cond_pagto();
			String id_tabela_preco = objeto.getid_tabela_preco();
			
			Double vl_preco1  = objeto.getvl_preco1();
			Double vl_preco2  = objeto.getvl_preco2();
			
			
			String insert = "UPDATE  " + tableName + " SET "
							+ " id_empresa=?, id_filial=?, id_produto=?, nr_limite_formar_preco=?,"
							+ " ds_opcoes_cond_pagto=?, id_tabela_preco=?, vl_preco1=?, vl_preco2=?"
							+ " WHERE id=" + id;
			
			db.execSQL(insert,new Object[]{id_empresa, id_filial, id_produto, nr_limite_formar_preco, ds_opcoes_cond_pagto, id_tabela_preco, vl_preco1, vl_preco2});
			 
			//db.setTransactionSuccessful();
		}
		catch (Exception e) 
		{
			Log.e("TabelaPreco", "update", e);  // log the error
		}
		finally
		{
			//db.endTransaction();
		}
		
	}
	
	public static void delete(TabelaPrecoDTO objeto)
	{
		try
		{
			//db.beginTransaction();
			String delete = "DELETE FROM  " + tableName + " WHERE id='" + objeto.getid() + "'";
			db.execSQL(delete);
			//db.setTransactionSuccessful();
		}
		catch (Exception e) 
		{
			Log.e("TabelaPreco", "delete", e);  // log the error
		}
		finally
		{
			//db.endTransaction();
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
			Log.e("TabelaPreco", "delete", e);  // log the error
		}
		finally
		{
			//db.endTransaction();
		}
	}
	
	public static TabelaPrecoDTO Get(int id)
	{
			
		Cursor cursor = null;
			
		try
		{

			cursor = db.rawQuery("SELECT * FROM  " + tableName + " where id=" + id, null);
			
			if(cursor.getCount() > 0)
			{
				cursor.moveToFirst();
				return (cursorToObject(cursor));
			}
			
			return null;
		}
		catch (Exception e) 
		{
			Log.e("TabelaPreco", "Get", e);  // log the error
			return null;
		}
		finally
		{
			if(cursor != null)
			{
				cursor.close();
			}
		}
	}
	
	public static TabelaPrecoDTO Get(int id_empresa, String cd_tabelapreco, int id_produto)
	{
			
		Cursor cursor = null;
			
		try
		{

			cursor = db.rawQuery("SELECT * FROM  " + tableName + " where id_empresa=" + id_empresa 
															   + " and id_tabela_preco='" + cd_tabelapreco 
															   + "' and id_produto=" + id_produto, null);
			if(cursor.getCount() > 0)
			{
				cursor.moveToFirst();
				return (cursorToObject(cursor));
			}
			
			return null;
		}
		catch (Exception e) 
		{
			Log.e("TabelaPreco", "Get", e);  // log the error
			return null;
		}
		finally
		{
			if(cursor != null)
			{
				cursor.close();
			}
		}
	}
	
	
	public static List<TabelaPrecoDTO> fillAll()
	{
		Cursor cursor = null;
		try
		{
			List<TabelaPrecoDTO> all = new ArrayList<TabelaPrecoDTO>();
			
			cursor = db.rawQuery("SELECT * FROM  " + tableName + " ", null);
			
			if(cursor.getCount() > 0)
			{
				
				cursor.moveToFirst();
				do
				{
					all.add(cursorToObject(cursor));
					cursor.moveToNext();
					
				}while(!cursor.isAfterLast());
			}
			
			return all;
		}
		finally
		{
			if(cursor != null)
			{
				cursor.close();
			}
		}
	}
	
	private static TabelaPrecoDTO cursorToObject(Cursor cursor) 
	{
		    
		TabelaPrecoDTO objeto = new TabelaPrecoDTO();
		  
		objeto.setid(cursor.getInt(cursor.getColumnIndex("id")));
		objeto.setid_empresa(cursor.getInt(cursor.getColumnIndex("id_empresa")));
		objeto.setid_filial(cursor.getInt(cursor.getColumnIndex("id_filial")));
		objeto.setid_produto(cursor.getInt(cursor.getColumnIndex("id_produto")));
		objeto.setnr_limite_formar_preco(cursor.getInt(cursor.getColumnIndex("nr_limite_formar_preco")));
		
		objeto.setds_opcoes_cond_pagto(cursor.getString(cursor.getColumnIndex("ds_opcoes_cond_pagto")));
		objeto.setid_tabela_preco(cursor.getString(cursor.getColumnIndex("id_tabela_preco")));
		
		objeto.setvl_preco1(cursor.getDouble(cursor.getColumnIndex("vl_preco1")));
		objeto.setvl_preco2(cursor.getDouble(cursor.getColumnIndex("vl_preco2")));
		
		
		return objeto;
	}

}
