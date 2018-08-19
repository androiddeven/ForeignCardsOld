package inc.shungo.foreigncards;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitDb();

        //Parcelable card = getIntent().getParcelableExtra("new_card");
        TextView cardsCount = findViewById(R.id.cards_count);
        cardsCount.setText(Integer.toString(DatabaseProvider.CardsCount()));

        final EditText groupIdForAdding = findViewById(R.id.group_id_for_adding);
        groupIdForAdding.setText(Integer.toString(DatabaseProvider.GetSettings().GroupIdForAdding));

        groupIdForAdding.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s)
            {
                Settings e = DatabaseProvider.GetSettings();
                int group = s.toString().isEmpty() ? 0 : Integer.parseInt(s.toString());
                e.GroupIdForAdding = group;
                DatabaseProvider.UpdateSettings(e);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }
        });

        Button addButton = findViewById(R.id.button_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddActivity.class);
                i.putExtra("GroupIdForAdding", Integer.parseInt(groupIdForAdding.getText().toString()));
                startActivity(i);
            }
        });

        final EditText groupIdForView = findViewById(R.id.group_id_for_view);

        Button viewButton = findViewById(R.id.button_view);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ViewActivity.class);
                String group = groupIdForView.getText().toString().isEmpty() ? "0" : groupIdForView.getText().toString();

                i.putExtra("GroupIdForView", Integer.parseInt(group));
                startActivity(i);
            }
        });
    }

    private void InitDb()
    {
        DatabaseHelper mDBHelper = new DatabaseHelper(this);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            DatabaseProvider.Db = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
    }
}
