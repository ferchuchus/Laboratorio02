package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.Update;
import android.content.Context;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;

public class CategoriaRepository {
    private static CategoriaRepository _INSTANCIA_UNICA = null;
    private BaseDatos db;
    private CategoriaDao categoriaDao;

    private CategoriaRepository(Context ctx) {
        db = Room.databaseBuilder(ctx,
                BaseDatos.class, "DataBaseLab04")
                .fallbackToDestructiveMigration()
                .build();
        categoriaDao = db.categoriaDao();

    }

    public static CategoriaRepository getInstance(Context ctx) {
        if (_INSTANCIA_UNICA == null)
            _INSTANCIA_UNICA = new CategoriaRepository(ctx);
        return _INSTANCIA_UNICA;
    }

    public void borrarTodo() {
        this.db.clearAllTables();
    }

    public CategoriaDao getCategoriaDao() {
        return categoriaDao;
    }

    public void setCategoriaDao(CategoriaDao categoriaDao) {
        this.categoriaDao = categoriaDao;
    }

    public List<Categoria> ListaCategorias() {
        return categoriaDao.getAll();
    }

    public void crearCategoria(Categoria cat) {
        categoriaDao.insert(cat);
    }

    public void actualizarCategoria(Categoria cat) {
        categoriaDao.update(cat);
    }

    public void borrarCategoria(Categoria cat) {
        categoriaDao.delete(cat);
    }


}
