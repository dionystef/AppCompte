package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dionys on 29/10/15.
 */
public class Storage extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db"; // nom de la base //
    public static final String SMS_TABLE_NAME = "sms"; // nom de la table crée //
    public static final String SMS_COLUMN_ID = "id";    // nom de l'attribut //
    public static final String SMS_COLUMN_LABEL = "label"; //
    public static final String SMS_COLUMN_MONTANT = "montant"; //
    public static final String SMS_COLUMN_OPERATE = "operate"; //
    public static final String SMS_COLUMN_CONTENT = "isContent"; //

    // constructeur //
    public Storage(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table item " + "(id integer primary key, label text, montant int, operate boolean)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS item");
        onCreate(db);
    }

    /**
     * insert les données dans la base
     * @param label
     * @param montant
     * @param operate
     * @return true si réussi
     */
    public boolean insertItem  (String label, int montant, boolean operate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("label", label);
        contentValues.put("montant", montant);
        contentValues.put("operate", operate);

        db.insert("item", null, contentValues);
        return true;
    }

    /**
     *
     * @return tout les noms des sms recu
     */
    public ArrayList<Item> getAllItem()
    {
        ArrayList<Item> array_list = new ArrayList<Item>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from item", null );
        res.moveToFirst();
        while(res.isAfterLast() == false){
            String label = res.getString(res.getColumnIndex(SMS_COLUMN_LABEL));
            int montant = res.getInt(res.getColumnIndex(SMS_COLUMN_MONTANT));
            boolean operate = res.getInt(res.getColumnIndex(SMS_COLUMN_OPERATE)) > 0;
            Item item = new Content(label, montant, operate);
            array_list.add(item);
            res.moveToNext();
        }
        return array_list;
    }
}
