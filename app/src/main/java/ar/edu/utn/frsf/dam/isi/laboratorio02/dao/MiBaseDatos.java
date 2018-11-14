package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Room;
import android.content.Context;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;

public class MiBaseDatos {
    private static MiBaseDatos _INSTANCIA_UNICA = null;
    private BaseDatos db;
    private CategoriaDao categoriaDao;

    private MiBaseDatos(Context ctx) {
        db = Room.databaseBuilder(ctx,
                BaseDatos.class, "MyDataBase")
                .fallbackToDestructiveMigration()
                .build();
        categoriaDao = db.categoriaDao();

    }

    public static MiBaseDatos getInstance(Context ctx) {
        if (_INSTANCIA_UNICA == null) _INSTANCIA_UNICA = new MiBaseDatos(ctx);
        return _INSTANCIA_UNICA;
    }

  /*  public void borrarTodo() {
        this.db.clearAllTables();
    }

    public CategoriaDao getCategoriaDao() {
        return categoriaDao;
    }

    public void setCategoriaDao(CategoriaDao categoriaDao) {
        this.categoriaDao = categoriaDao;
    }
    */
}
