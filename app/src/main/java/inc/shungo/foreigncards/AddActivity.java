package inc.shungo.foreigncards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddActivity extends AppCompatActivity {
    private Button _addButton;
    private Button _closeButton;

    private EditText _word;
    private EditText _translation;
    private EditText _transcription;

    private EditText _group;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        final int groupIdForAdding = getIntent().getIntExtra("GroupIdForAdding", 0);

        _word = findViewById(R.id.word);
        _translation = findViewById(R.id.translation);
        _transcription = findViewById(R.id.transcription);
        _group = findViewById(R.id.group);

        _closeButton = findViewById(R.id.button_close);
        _closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        _addButton = findViewById(R.id.button_add);
        _addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Card c = new Card();
                c.Word = _word.getText().toString();
                c.Transl = _translation.getText().toString();
                c.Transc = _transcription.getText().toString();
                c.Group = groupIdForAdding;

                DatabaseProvider.AddCard(c);

                Intent i = new Intent(AddActivity.this, MainActivity.class);
                //i.putExtra("new_card", c);
                startActivity(i);
            }
        });
    }
}
