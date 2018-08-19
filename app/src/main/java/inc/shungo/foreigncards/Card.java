package inc.shungo.foreigncards;

import android.os.Parcel;
import android.os.Parcelable;

public class Card implements Parcelable {
    public int Id;

    public String Word;
    public String Transl;
    public String Transc;

    public int Group;

    public Card() {
    }

    protected Card(Parcel in) {
        Word = in.readString();
        Transl = in.readString();
        Transc = in.readString();

        Group = in.readInt();
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel p, int i) {
        p.writeString(Word);
        p.writeString(Transl);
        p.writeString(Transc);
        p.writeInt(Group);
    }
}
