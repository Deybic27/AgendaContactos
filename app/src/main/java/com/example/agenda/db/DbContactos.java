package com.example.agenda.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.agenda.entidades.Contactos;

import java.util.ArrayList;

public class DbContactos extends DbHelper{

    Context context;

    public DbContactos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarContacto(String nombre, String documento, String fecha_nacimiento, String telefono, String correo_electronico){

        long id = 0;
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("documento", documento);
            values.put("fecha_nacimiento", fecha_nacimiento);
            values.put("telefono", telefono);
            values.put("correo_electronico", correo_electronico);

            id = db.insert(TABLE_CONTACTOS, null, values);
        }catch (Exception ex){
            ex.toString();
        }
        return id;
    }

    public ArrayList<Contactos> mostrarContactos(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Contactos> listaContactos = new ArrayList<>();
        Contactos contacto = null;
        Cursor cursorContactos = null;

        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS, null);
        if (cursorContactos.moveToFirst()){
            do {
                contacto = new Contactos();
                contacto.setId(cursorContactos.getInt(0)); // id
                contacto.setNombre(cursorContactos.getString(1)); //nombre
                contacto.setDocumento(cursorContactos.getString(2)); //Numero de documento de identidad
                contacto.setFecha_nacimiento(cursorContactos.getString(3)); //Fecha de nacimiento
                contacto.setTelefono(cursorContactos.getString(4));// telefono
                contacto.setCorreo_electronico(cursorContactos.getString(5));// correo
                listaContactos.add(contacto);
            }while (cursorContactos.moveToNext());
        }
        cursorContactos.close();

        return listaContactos;
    }

    public Contactos verContacto(int id){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Contactos contacto = null;
        Cursor cursorContactos = null;

        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS + " WHERE id=" + id + " LIMIT 1", null);
        if (cursorContactos.moveToFirst()){
            contacto = new Contactos();
            contacto.setId(cursorContactos.getInt(0)); // id
            contacto.setNombre(cursorContactos.getString(1)); //nombre
            contacto.setDocumento(cursorContactos.getString(2)); //Numero de documento de identidad
            contacto.setFecha_nacimiento(cursorContactos.getString(3)); //Fecha de nacimiento
            contacto.setTelefono(cursorContactos.getString(4));// telefono
            contacto.setCorreo_electronico(cursorContactos.getString(5));// correo
        }
        cursorContactos.close();

        return contacto;
    }

    public boolean editarContacto(int id, String nombre, String documento, String fecha_nacimiento, String telefono, String correo_electronico){

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try{
            db.execSQL("UPDATE " + TABLE_CONTACTOS + " SET nombre = '" + nombre + "', documento = '" + documento + "', fecha_nacimiento = '" + fecha_nacimiento + "', telefono = '" + telefono +"', correo_electronico = '" + correo_electronico +"' WHERE id = '" + id + "'");
            correcto = true;
        }catch (Exception ex){
            ex.toString();
            System.out.println("___________________________*******************************************______________________________");
            System.out.println(ex.toString());
            System.out.println("___________________________*******************************************______________________________");
            correcto = false;
        }finally {
            db.close();
        }
        return correcto;
    }

    public boolean eliminarContacto(int id){

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try{
            db.execSQL("DELETE FROM " + TABLE_CONTACTOS + " WHERE id = '" + id + "'");
            correcto = true;
        }catch (Exception ex){
            ex.toString();
            correcto = false;
        }finally {
            db.close();
        }
        return correcto;
    }
}
