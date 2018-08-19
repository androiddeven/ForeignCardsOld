package inc.shungo.foreigncards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {
    private ArrayList<Card> _cards;
    private int _currentIndex;
    private boolean _wasShowed;

    private EditText _word;
    private EditText _transl;
    private EditText _transc;
    private EditText _group;

    private TextView _count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Button closeButton = findViewById(R.id.button_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ViewActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        int groupIdForView = getIntent().getIntExtra("GroupIdForView", 0);

        _cards = DatabaseProvider.GetCards(groupIdForView);
        _currentIndex = 0;

        _count = findViewById(R.id.text_count);
        _count.setText(Integer.toString(_cards.size()));

        if (_cards.size() == 0)
            return;

        _word = findViewById(R.id.word);
        _transl = findViewById(R.id.transl);
        _transc = findViewById(R.id.transc);
        _group = findViewById(R.id.group);

        UpdateCurrCard();

        final ViewActivity t = this;

        Button show = findViewById(R.id.button_show);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t.ShowCard();
            }
        });

        Button prev = findViewById(R.id.button_prev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t._currentIndex--;
                if (t._currentIndex < 0)
                    t._currentIndex = 0;

                UpdateCurrCard();
            }
        });

        Button next = findViewById(R.id.button_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t._currentIndex++;
                if (t._currentIndex >= t._cards.size())
                    t._currentIndex = t._cards.size() - 1;

                UpdateCurrCard();
            }
        });

        Button remove = findViewById(R.id.button_remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t.RemoveCard();
            }
        });

        Button update = findViewById(R.id.button_update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t.UpdateCard();
            }
        });
    }

    private void UpdateCurrCard()
    {
        _word.setText(_cards.get(_currentIndex).Word);
        _group.setText(Integer.toString(_cards.get(_currentIndex).Group));

        _transl.setText("");
        _transc.setText("");

        _wasShowed = false;
        _count.setText(Integer.toString(_currentIndex + 1) + " (" + Integer.toString(_cards.size()) + ")");
    }

    private void ShowCard()
    {
        _transl.setText(_cards.get(_currentIndex).Transl);
        _transc.setText(_cards.get(_currentIndex).Transc);

        _wasShowed = true;
    }

    private void RemoveCard()
    {
        DatabaseProvider.RemoveCard(_cards.get(_currentIndex).Id);
    }

    private void UpdateCard()
    {
        Card c = _cards.get(_currentIndex);
        c.Group = Integer.parseInt(_group.getText().toString());
        c.Word = _word.getText().toString();
        c.Transl = _wasShowed ? _transl.getText().toString() : c.Transl;
        c.Transc = _wasShowed ? _transc.getText().toString() : c.Transc;

        DatabaseProvider.UpdateCard(c);
    }
}
