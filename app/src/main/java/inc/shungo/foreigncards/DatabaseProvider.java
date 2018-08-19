package inc.shungo.foreigncards;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DatabaseProvider {
    public static SQLiteDatabase Db;

    public static void AddCard(Card c)
    {
        ContentValues v = new ContentValues();
        v.put("Word", c.Word);
        v.put("Transc", c.Transc);
        v.put("Transl", c.Transl);
        if (c.Group >= 0)
            v.put("GroupId", c.Group);

        Db.insert("Cards", null, v);
    }

    public static void RemoveCard(int id)
    {
        Db.execSQL("DELETE FROM Cards WHERE Id = ?", new Object[] { id });
    }

    public static void UpdateCard(Card c)
    {
        ContentValues v = new ContentValues();
        v.put("Word", c.Word);
        v.put("Transc", c.Transc);
        v.put("Transl", c.Transl);
        if (c.Group >= 0)
            v.put("GroupId", c.Group);

        Db.update("Cards", v, "Id = ?", new String[] { Integer.toString(c.Id) });
    }

    public static int CardsCount()
    {
        return (int) DatabaseUtils.queryNumEntries(Db, "Cards");
    }

    public static ArrayList<Card> GetCards(Integer groupId)
    {
        ArrayList<Card> result = new ArrayList<>();

        String q = "SELECT * FROM Cards";
        if (groupId != null)
            q = String.format("SELECT * FROM Cards WHERE GroupId = %d", groupId.intValue());

        Cursor c = Db.rawQuery(q, null);
        c.moveToFirst();
        while (!c.isAfterLast())
        {
            Card a = new Card();
            a.Id = c.getInt(c.getColumnIndex("Id"));
            a.Word = c.getString(c.getColumnIndex("Word"));
            a.Transl = c.getString(c.getColumnIndex("Transl"));
            a.Transc = c.getString(c.getColumnIndex("Transc"));

            int groupI = c.getColumnIndex("GroupId");
            a.Group = c.isNull(groupI) ? -1 : c.getInt(groupI);

            result.add(a);
            c.moveToNext();
        }
        c.close();

        return result;
    }

    public static Settings GetSettings()
    {
        Settings result = new Settings();

        Cursor c = Db.rawQuery("SELECT * FROM Settings", null);
        c.moveToFirst();

        result.GroupIdForAdding = c.getInt(c.getColumnIndex("GroupIdForAdding"));
        c.close();

        return result;
    }

    public static void UpdateSettings(Settings s)
    {
        ContentValues v = new ContentValues();
        v.put("GroupIdForAdding", s.GroupIdForAdding);

        Db.update("Settings", v, null, null);
    }
}
