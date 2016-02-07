package weblayer.vendas.DAO;

import java.util.ArrayList;
import java.util.List;

import weblayer.vendas.DTO.ProdutoDTO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public final class ProdutoDAO {

	private static SQLiteDatabase db;
	private static String tableName = "Produto";

	public static void initialize(Context context) {
		// Abra a base de dados, cria se necessário.
		db = context.openOrCreateDatabase("weblayer.db",
				SQLiteDatabase.CREATE_IF_NECESSARY, null);

		// cria a tabela caso não exista.
		createTable(db);
	}

	private static void createTable(SQLiteDatabase database) {
		try {

			//database.beginTransaction();

			String tableSql = "CREATE TABLE IF NOT EXISTS " + tableName + " ("
					+ "id INTEGER PRIMARY KEY," + "id_empresa INTEGER,"
					+ "id_filial INTEGER," + "id_retaguarda VARCHAR(20),"
					+ "ds_nome_completo VARCHAR(255),"
					+ "ds_nome_curto VARCHAR(30), "
					+ "nr_peso_bruto DECIMAL(10,2),"
					+ "nr_peso_liquido DECIMAL(10,2),"
					+ "ds_ean13 VARCHAR(13)," + "ds_dun14 VARCHAR(14), "
					+ "ds_unidademedida VARCHAR(10),"
					+ "fl_cx_pallet INTEGER, " + "fl_ativo INTEGER, "
					+ "id_categ1 INTEGER, " + "id_categ2 INTEGER,"
					+ "id_categ3 INTEGER" + ");";

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

	public static void insert(ProdutoDTO objeto) {
		try {
			// db.beginTransaction();

			int id = objeto.getid();
			int id_empresa = objeto.getid_empresa();
			int id_filial = objeto.getid_filial();

			String id_retaguarda = objeto.getid_retaguarda();
			String ds_nome_completo = objeto.getds_nome_completo();
			String ds_nome_curto = objeto.getds_nome_curto();
			String ds_ean13 = objeto.getds_ean13();
			String ds_dun14 = objeto.getds_dun14();
			String ds_unidademedida = objeto.getds_unidademedida();

			Double nr_peso_bruto = objeto.getnr_peso_bruto();
			Double nr_peso_liquido = objeto.getnr_peso_liquido();

			int fl_cx_pallet = objeto.getfl_cx_pallet();
			int fl_ativo = objeto.getfl_ativo();
			int id_categ1 = objeto.getid_categ1();
			int id_categ2 = objeto.getid_categ2();
			int id_categ3 = objeto.getid_categ3();

			String insert = "INSERT INTO  "
					+ tableName
					+ " "
					+ " (id, id_empresa, id_filial, id_retaguarda, ds_nome_completo, ds_nome_curto, "
					+ " nr_peso_bruto, nr_peso_liquido, ds_ean13, ds_dun14, ds_unidademedida, fl_cx_pallet, fl_ativo, id_categ1, id_categ2, id_categ3) VALUES "
					+ " (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

			db.execSQL(insert, new Object[] { id, id_empresa, id_filial,
					id_retaguarda, ds_nome_completo, ds_nome_curto,
					nr_peso_bruto, nr_peso_liquido, ds_ean13, ds_dun14,
					ds_unidademedida, fl_cx_pallet, fl_ativo, id_categ1,
					id_categ2, id_categ3 });

			// db.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e("Produto", "insert", e); // log the error
		} finally {
			// db.endTransaction();
		}
	}

	public static void update(ProdutoDTO objeto) {
		try {
			// db.beginTransaction();

			// TODO Fazer retornar inteiro

			int id = objeto.getid();
			int id_empresa = objeto.getid_empresa();
			int id_filial = objeto.getid_filial();

			String id_retaguarda = objeto.getid_retaguarda();
			String ds_nome_completo = objeto.getds_nome_completo();
			String ds_nome_curto = objeto.getds_nome_curto();
			String ds_ean13 = objeto.getds_ean13();
			String ds_dun14 = objeto.getds_dun14();
			String ds_unidademedida = objeto.getds_unidademedida();

			Double nr_peso_bruto = objeto.getnr_peso_bruto();
			Double nr_peso_liquido = objeto.getnr_peso_liquido();

			int fl_cx_pallet = objeto.getfl_cx_pallet();
			int fl_ativo = objeto.getfl_ativo();
			int id_categ1 = objeto.getid_categ1();
			int id_categ2 = objeto.getid_categ2();
			int id_categ3 = objeto.getid_categ3();

			String insert = "UPDATE  "
					+ tableName
					+ " SET "
					+ " id_empresa=?, id_filial=?, id_retaguarda=?, ds_nome_completo=?,"
					+ " ds_nome_curto=?, ds_ean13=?, ds_dun14=?, ds_unidademedida=?, "
					+ " nr_peso_bruto=?, nr_peso_liquido=?, fl_cx_pallet=?, fl_ativo=?, id_categ1=?, id_categ2=?, id_categ3=?"
					+ " WHERE id=" + id;

			db.execSQL(insert, new Object[] { id_empresa, id_filial,
					id_retaguarda, ds_nome_completo, ds_nome_curto, ds_ean13,
					ds_dun14, ds_unidademedida, nr_peso_bruto, nr_peso_liquido,
					fl_cx_pallet, fl_ativo, id_categ1, id_categ2, id_categ3 });

			// db.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e("Produto", "update", e); // log the error
		} finally {
			// db.endTransaction();
		}

	}

	public static void delete(ProdutoDTO objeto) {
		try {
			// db.beginTransaction();
			String delete = "DELETE FROM  " + tableName + " WHERE id='"
					+ objeto.getid() + "'";
			db.execSQL(delete);
			// db.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e("Produto", "delete", e); // log the error
		} finally {
			// db.endTransaction();
		}
	}

	public static void TruncateTable() {
		try {
			db.beginTransaction();
			String delete = " DROP TABLE IF EXISTS " + tableName;
			db.execSQL(delete);
			db.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e("Produto", "delete", e); // log the error
		} finally {
			db.endTransaction();
		}
	}

	public static ProdutoDTO Get(int id) {

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
			Log.e("Produto", "Get", e); // log the error
			return null;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	public static List<ProdutoDTO> fillAll() {
		Cursor cursor = null;
		try {
			List<ProdutoDTO> all = new ArrayList<ProdutoDTO>();

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
			Log.e("Produto", "Get", e); // log the error
			return null;
		}
		finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	public static List<ProdutoDTO> fill(String Filtro, int categ1, int categ2,
			int categ3) {
		Cursor cursor = null;
		try {
			List<ProdutoDTO> all = new ArrayList<ProdutoDTO>();

			String where = "";
			if (categ1 > 0)
				where = where + " and id_categ1=" + String.valueOf(categ1);

			if (categ2 > 0)
				where = where + " and id_categ2=" + String.valueOf(categ2);

			if (categ3 > 0)
				where = where + " and id_categ3=" + String.valueOf(categ3);

			if (android.text.TextUtils.isEmpty(Filtro))
				cursor = db.rawQuery("SELECT * FROM  " + tableName
						+ " where 1=1 " + where + " order by ds_nome_completo",
						null);
			else
				cursor = db
						.rawQuery(
								"SELECT * FROM  "
										+ tableName
										+ " where (id_retaguarda like ? or ds_nome_completo like ?) "
										+ where + " order by ds_nome_completo",
								new String[] { "%" + Filtro + "%",
										"%" + Filtro + "%" });

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
			Log.e("Produto", "Get", e); // log the error
			return null;
		}
		finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}
	
	
	public static List<ProdutoDTO> fillAtivos(String Filtro, int categ1, int categ2,
			int categ3) {
		Cursor cursor = null;
		try {
			List<ProdutoDTO> all = new ArrayList<ProdutoDTO>();

			String where = "";
			if (categ1 > 0)
				where = where + " and id_categ1=" + String.valueOf(categ1);

			if (categ2 > 0)
				where = where + " and id_categ2=" + String.valueOf(categ2);

			if (categ3 > 0)
				where = where + " and id_categ3=" + String.valueOf(categ3);

			if (android.text.TextUtils.isEmpty(Filtro))
				cursor = db.rawQuery("SELECT * FROM  " + tableName
						+ " where fl_ativo=1 " + where + " order by ds_nome_completo",
						null);
			else
				cursor = db
						.rawQuery(
								"SELECT * FROM  "
										+ tableName
										+ " where (id_retaguarda like ? or ds_nome_completo like ?) "
										+ where + " order by ds_nome_completo",
								new String[] { "%" + Filtro + "%",
										"%" + Filtro + "%" });

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
			Log.e("Produto", "Get", e); // log the error
			return null;
		}
		finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	
	private static ProdutoDTO cursorToObject(Cursor cursor) {

		ProdutoDTO objeto = new ProdutoDTO();

		objeto.setid(cursor.getInt(cursor.getColumnIndex("id")));
		objeto.setid_empresa(cursor.getInt(cursor.getColumnIndex("id_empresa")));
		objeto.setid_filial(cursor.getInt(cursor.getColumnIndex("id_filial")));
		objeto.setid_retaguarda(cursor.getString(cursor
				.getColumnIndex("id_retaguarda")));

		objeto.setds_nome_completo(cursor.getString(cursor
				.getColumnIndex("ds_nome_completo")));
		objeto.setds_nome_curto(cursor.getString(cursor
				.getColumnIndex("ds_nome_curto")));

		objeto.setnr_peso_bruto(cursor.getDouble(cursor
				.getColumnIndex("nr_peso_bruto")));
		objeto.setnr_peso_liquido(cursor.getDouble(cursor
				.getColumnIndex("nr_peso_liquido")));

		objeto.setds_ean13(cursor.getString(cursor.getColumnIndex("ds_ean13")));
		objeto.setds_dun14(cursor.getString(cursor.getColumnIndex("ds_dun14")));

		objeto.setds_unidademedida(cursor.getString(cursor
				.getColumnIndex("ds_unidademedida")));

		objeto.setfl_ativo(cursor.getInt(cursor.getColumnIndex("fl_ativo")));
		objeto.setfl_cx_pallet(cursor.getInt(cursor
				.getColumnIndex("fl_cx_pallet")));
		objeto.setfl_ativo(cursor.getInt(cursor.getColumnIndex("fl_ativo")));

		objeto.setid_categ1(cursor.getInt(cursor.getColumnIndex("id_categ1")));
		objeto.setid_categ2(cursor.getInt(cursor.getColumnIndex("id_categ2")));
		objeto.setid_categ3(cursor.getInt(cursor.getColumnIndex("id_categ3")));

		return objeto;
	}

	public static double GetPrecoLista(Context context, int id_cliente,
			int id_produto) {

		double juros = 0;
		double precoimposto = 0;
		double xjuros = 0;

		// buscar o cliente
		weblayer.vendas.DAO.ClienteDAO.initialize(context);
		weblayer.vendas.DTO.ClienteDTO cliente = weblayer.vendas.DAO.ClienteDAO
				.Get(id_cliente);
		if (cliente == null) {
			// cliente não encontrado.
			return 0;
		}

		// buscar o produto
		weblayer.vendas.DAO.ProdutoDAO.initialize(context);
		weblayer.vendas.DTO.ProdutoDTO produto = weblayer.vendas.DAO.ProdutoDAO
				.Get(id_produto);
		if (produto == null) {
			// produto não encontrado.
			return 0;
		}
		
		weblayer.vendas.DAO.TabelaPrecoDAO.initialize(context);
		weblayer.vendas.DTO.TabelaPrecoDTO tabelapreco = weblayer.vendas.DAO.TabelaPrecoDAO
				.Get(cliente.getid_empresa(), cliente.getid_tabpreco(),
						produto.getid());
		if (tabelapreco == null) {
			// tabela de preco não encontrado.
			return 0;
		}

		if (tabelapreco.getvl_preco1() == tabelapreco.getvl_preco2())
			return tabelapreco.getvl_preco1();

		// weblayer.vendas.DAO.ParametroDAO.initialize();

		// Juros
		if (!cliente.getds_cidade().equalsIgnoreCase("EX")) {
			
			weblayer.vendas.DAO.ParametroDAO.initialize(context);
			weblayer.vendas.DTO.ParametroDTO paramjuros = weblayer.vendas.DAO.ParametroDAO
					.GetByKey("V_JUROS");
			if (paramjuros == null) {
				return 0;
			}

			juros = Double.parseDouble(paramjuros.getds_valor());

		}

		// Definição dos dias de prazo
		int nr_diasprazo = 0;
		if (tabelapreco.getnr_limite_formar_preco() > 0)
			nr_diasprazo = tabelapreco.getnr_limite_formar_preco();

		double x = 1 + juros;
		double x1 = ((double) 1 / (double) 30);
		double x2 = (double) nr_diasprazo;

		xjuros = Math.pow(Math.pow(x, x1), x2);

		return (tabelapreco.getvl_preco1() * xjuros);

	}
}
