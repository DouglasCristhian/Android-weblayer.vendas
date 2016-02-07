package weblayer.vendas.DAO;

import java.lang.Exception;
import java.util.List;
import java.util.ArrayList;

import weblayer.vendas.DTO.ClienteDTO;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public final class ClienteDAO {

	private static SQLiteDatabase db;
	private static String tableName="Cliente";
	
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
			//begin the transaction
			//database.beginTransaction();
			
			// Create a table
			String tableSql = "CREATE TABLE IF NOT EXISTS " + tableName 
					+ " ("
					+ "id INTEGER PRIMARY KEY," 
					+ "id_empresa INTEGER," 
					+ "id_filial INTEGER," 
					+ "id_retaguarda VARCHAR(20),"
					+ "ds_razao VARCHAR(60),"
					+ "fl_ativo INTEGER, "
					+ "nr_diasentrega INTEGER," 
					+ "nr_diasprazopagamento INTEGER," 
					+ "id_filialfaturamento INTEGER, "
					+ "ds_endereco VARCHAR(260), "
					+ "ds_cidade VARCHAR(60), "
					+ "ds_cep VARCHAR(9), "
					+ "ds_bairro VARCHAR(60)," 
					+ "ds_cnpj VARCHAR(20), "
					+ "ds_ie VARCHAR(20), "
					+ "ds_obs VARCHAR(260)," 
					+ "id_tipo INTEGER, "
					+ "ds_contato VARCHAR(30)," 
					+ "ds_canal VARCHAR(40), "
					+ "ds_email VARCHAR(260), "
					+ "ds_sms VARCHAR(60), "
					+ "ds_uf VARCHAR(2), "
					+ "ds_codmunicipio VARCHAR(10)," 
					+ "ds_suframa VARCHAR(10), "
					+ "id_tabpreco VARCHAR(10), "
					+ "ds_prazo VARCHAR(30), "
					+ "ds_tel1 VARCHAR(20), "
					+ "ds_tel2 VARCHAR(20)"
					+ ");";
			
			database.execSQL(tableSql);
			
			//this makes sure transaction is committed
			//database.setTransactionSuccessful();
		} 
		finally
		{
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
			Log.e("Cliente", "truncate table", e);  // log the error
		}
		finally
		{
			//db.endTransaction();
		}
		
	}
	
	private static boolean isTableEmpty()
	{
		Cursor cursor = null;
		try
		{
			cursor = db.rawQuery("SELECT count(*) FROM " + tableName, null);
			
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
	
	public static void insert(ClienteDTO cliente)
	{
		try
		{
			//db.beginTransaction();
			
			int id = cliente.getid();
			int id_empresa = cliente.getid_empresa();
			int id_filial = cliente.getid_filial();
			String id_retaguarda = cliente.getid_retaguarda();
			String ds_razao = cliente.getds_razao();
			int fl_ativo = cliente.getfl_ativo();
			int nr_diasentrega= cliente.getnr_diasentrega();
			int nr_diasprazopagamento= cliente.getnr_diasprazopagamento();
			int id_filialfaturamento = cliente.getid_filial_faturamento();
			String ds_cnpj = cliente.getds_cnpj();
			
			String ds_uf = cliente.getds_uf();
			
			String ds_canal = cliente.getds_canal();
			
			String ds_endereco = cliente.getds_endereco();
			String ds_email = cliente.getds_email(); 
			String ds_cidade = cliente.getds_cidade();
			String ds_cep = cliente.getds_cep();
			String ds_bairro = cliente.getds_bairro();
			String ds_contato = cliente.getds_contato();
			String ds_tel1= cliente.getds_tel1();
			String id_tabpreco = cliente.getid_tabpreco();
			
			
			String insert = "INSERT INTO Cliente "
				+ " (id, id_empresa, id_filial, id_retaguarda, ds_razao, fl_ativo, "
				+ " nr_diasentrega, nr_diasprazopagamento, id_filialfaturamento, ds_cnpj, "
				+ " ds_endereco, ds_email, ds_cidade, ds_cep, ds_bairro, ds_contato, ds_tel1, ds_uf, id_tabpreco, ds_canal"
				+ ") VALUES " 
				+ " (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
			
			db.execSQL(insert,new Object[]{id, id_empresa, id_filial, id_retaguarda, ds_razao, 
											fl_ativo, nr_diasentrega, nr_diasprazopagamento,
											id_filialfaturamento, ds_cnpj, ds_endereco, 
											ds_email, ds_cidade, ds_cep, ds_bairro, ds_contato, ds_tel1,ds_uf, id_tabpreco, ds_canal});
			
			//db.setTransactionSuccessful();
		}
		catch (Exception e) 
		{
			Log.e("Cliente", "insert", e);  // log the error
		}
		finally
		{
			//db.endTransaction();
		}
	}

	public static int update(ClienteDTO cliente)
	{
		
		int rowsafected = 0;
		
		try
		{
			//db.beginTransaction();
			
			ContentValues values = new ContentValues();
	        values.put("id_empresa", cliente.getid_empresa());
	        values.put("id_filial", cliente.getid_filial());
	        values.put("id_retaguarda", cliente.getid_retaguarda());
	        values.put("ds_razao", cliente.getds_razao());
	        values.put("fl_ativo", cliente.getfl_ativo());
	        values.put("nr_diasentrega", cliente.getnr_diasentrega());
	        values.put("nr_diasprazopagamento", cliente.getnr_diasprazopagamento());
	        values.put("id_filialfaturamento", cliente.getid_filial_faturamento());
	        values.put("ds_cnpj", cliente.getds_cnpj());
	        values.put("ds_uf", cliente.getds_uf());
	        
	        values.put("ds_endereco", cliente.getds_endereco());
	        values.put("ds_email", cliente.getds_email());
	        values.put("ds_cidade", cliente.getds_cidade());
	        values.put("ds_cep", cliente.getds_cep());
	        values.put("ds_bairro", cliente.getds_bairro());
	        
	        values.put("ds_canal", cliente.getds_canal());
	        
	        values.put("ds_contato", cliente.getds_contato());
	        values.put("ds_tel1", cliente.getds_tel1());
	        values.put("id_tabpreco", cliente.getid_tabpreco());
	        
	        rowsafected = db.update(tableName, values, "id = ?", new String[] { String.valueOf(cliente.getid()) });
		        
	        //db.setTransactionSuccessful();
			
			return rowsafected ;
			
		}
		catch (Exception e) 
		{
			Log.e("Cliente", "update", e);  // log the error
		}
		finally
		{
			//db.endTransaction();
		}
		
		return rowsafected;
		
	}
	
	public static void delete(ClienteDTO objeto)
	{
		try
		{
			//db.beginTransaction();
			String delete = "DELETE FROM " + tableName + " WHERE id='" + objeto.getid() + "'";
			db.execSQL(delete);
			//db.setTransactionSuccessful();
		}
		catch (Exception e) 
		{
			Log.e("Cliente", "delete", e);  
		}
		finally
		{
			//db.endTransaction();
		}
	}

	public static void insertorupdate(ClienteDTO objeto)
	{
			
		if (update(objeto)==0)
			insert(objeto);
	
	}
	
	public static ClienteDTO Get(int id)
	{
			
		Cursor cursor = null;
			
		try
		{

			cursor = db.rawQuery("SELECT * FROM " + tableName + " where id=" + id, null);
			
			if(cursor.getCount() > 0)
			{
				cursor.moveToFirst();
				return (cursorToObject(cursor));
			}
			
			return null;
		}
		catch (Exception e) 
		{
			Log.e("Cliente", "delete", e);  // log the error
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
	 
	public static List<ClienteDTO> fillAll()
	{
		Cursor cursor = null;
		try
		{
			List<ClienteDTO> all = new ArrayList<ClienteDTO>();
			
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
		catch (Exception e) 
		{
			Log.e("Cliente", "fill", e);  // log the error
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
	
	public static List<ClienteDTO> fill(String Filtro)
	{
		Cursor cursor = null;
		try
		{
			List<ClienteDTO> all = new ArrayList<ClienteDTO>();
			
			if (android.text.TextUtils.isEmpty(Filtro))
				cursor = db.rawQuery("SELECT * FROM  " + tableName + " order by ds_razao", null);
			else
				cursor = db.rawQuery("SELECT * FROM  " + tableName + " where (id_retaguarda like ? or ds_razao like ? or ds_cnpj like ?) order by ds_razao", new String[]{ "%" + Filtro + "%", "%" + Filtro + "%", "%" + Filtro + "%"});
			
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
		catch (Exception e) 
		{
			Log.e("Cliente", "fill", e);  // log the error
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
	
	public static List<ClienteDTO> fillAtivo(String Filtro)
	{
		Cursor cursor = null;
		try
		{
			List<ClienteDTO> all = new ArrayList<ClienteDTO>();
			
			if (android.text.TextUtils.isEmpty(Filtro))
				cursor = db.rawQuery("SELECT * FROM  " + tableName + " where fl_ativo=1 order by ds_razao", null);
			else
				cursor = db.rawQuery("SELECT * FROM  " + tableName + " where fl_ativo=1  and (id_retaguarda like ? or ds_razao like ? or ds_cnpj like ?) order by ds_razao", new String[]{ "%" + Filtro + "%", "%" + Filtro + "%", "%" + Filtro + "%"});
			
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
		catch (Exception e) 
		{
			Log.e("Cliente", "fill", e);  // log the error
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
	
	private static ClienteDTO cursorToObject(Cursor cursor) 
	 {
		    
		 ClienteDTO cliente = new ClienteDTO();
		    
		 cliente.setid(cursor.getInt(cursor.getColumnIndex("id")));
		 cliente.setid_empresa(cursor.getInt(cursor.getColumnIndex("id_empresa")));
		 cliente.setid_filial(cursor.getInt(cursor.getColumnIndex("id_filial")));
		 cliente.setid_retaguarda(cursor.getString(cursor.getColumnIndex("id_retaguarda")));
		 
		 cliente.setds_razao(cursor.getString(cursor.getColumnIndex("ds_razao")));
		 cliente.setfl_ativo(cursor.getInt(cursor.getColumnIndex("fl_ativo")));
		 cliente.setnr_diasentrega(cursor.getInt(cursor.getColumnIndex("nr_diasentrega")));
		 cliente.setnr_diasprazopagamento(cursor.getInt(cursor.getColumnIndex("nr_diasprazopagamento")));
		 cliente.setid_filial_faturamento(cursor.getInt(cursor.getColumnIndex("id_filialfaturamento")));
		 cliente.setds_endereco(cursor.getString(cursor.getColumnIndex("ds_endereco")));
		 cliente.setds_cidade(cursor.getString(cursor.getColumnIndex("ds_cidade")));
		 cliente.setds_cep(cursor.getString(cursor.getColumnIndex("ds_cep")));
		 cliente.setds_bairro(cursor.getString(cursor.getColumnIndex("ds_bairro")));
		 cliente.setds_cnpj(cursor.getString(cursor.getColumnIndex("ds_cnpj")));
		 cliente.setds_ie(cursor.getString(cursor.getColumnIndex("ds_ie")));
		 cliente.setds_obs(cursor.getString(cursor.getColumnIndex("ds_obs")));	    
		 cliente.setid_tipo(cursor.getInt(cursor.getColumnIndex("id_tipo")));
		 cliente.setds_contato(cursor.getString(cursor.getColumnIndex("ds_contato")));
		 
		 cliente.setds_canal(cursor.getString(cursor.getColumnIndex("ds_canal")));
		 cliente.setds_email(cursor.getString(cursor.getColumnIndex("ds_email")));
		 cliente.setds_sms(cursor.getString(cursor.getColumnIndex("ds_sms")));
		 cliente.setds_uf(cursor.getString(cursor.getColumnIndex("ds_uf")));
		 cliente.setds_codmunicipio(cursor.getString(cursor.getColumnIndex("ds_codmunicipio")));
		 cliente.setds_suframa(cursor.getString(cursor.getColumnIndex("ds_suframa")));
		 cliente.setid_tabpreco(cursor.getString(cursor.getColumnIndex("id_tabpreco")));
		 cliente.setds_prazo(cursor.getString(cursor.getColumnIndex("ds_prazo")));
		 cliente.setds_tel1(cursor.getString(cursor.getColumnIndex("ds_tel1")));
		 cliente.setds_tel2(cursor.getString(cursor.getColumnIndex("ds_tel2")));
		 
		 return cliente;
	}
	
}
