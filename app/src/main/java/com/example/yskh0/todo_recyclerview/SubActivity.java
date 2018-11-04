package com.example.yskh0.todo_recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class SubActivity extends AppCompatActivity {
    private EditText editText;
    private RadioGroup radioGroup;
    private Button buttonRegister;
    private Todo todo;
    private int requestCode;
    private int index;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        editText = findViewById(R.id.etTodo);
        radioGroup = findViewById(R.id.rg);
        buttonRegister = findViewById(R.id.btRegister);
        Intent intent = this.getIntent();
        requestCode = intent.getIntExtra("requestCode", -1);
        index = intent.getIntExtra("index", -1);

        if (requestCode == MainActivity.UPDATE) {
            buttonRegister.setText("更新");
            todo = (Todo) intent.getSerializableExtra("todo");
            editText.setText(todo.getContents());
            switch (todo.getResId()) {
                case R.drawable.star_r:
                    radioGroup.check(R.id.rb1);
                    break;
                case R.drawable.star_g:
                    radioGroup.check(R.id.rb2);
                    break;
                case R.drawable.star_b:
                    radioGroup.check(R.id.rb3);
                    break;
            }
        }
    }

    public void btResult(View view) {
        Intent intent = new Intent();
        String t = editText.getText().toString();
        int resId = 0;
        int checkedId = radioGroup.getCheckedRadioButtonId();
        switch (checkedId) {
            case R.id.rb1:
                resId = R.drawable.star_r;
                break;
            case R.id.rb2:
                resId = R.drawable.star_g;
                break;
            case R.id.rb3:
                resId = R.drawable.star_b;
                break;
            default:
                resId = R.drawable.star_r;
                break;
        }
        switch (this.requestCode) {
            case MainActivity.ADD:
                todo = new Todo(resId, t);
                break;
            case MainActivity.UPDATE:
                todo.setContents(t);
                todo.setResId(resId);
                intent.putExtra("index", index);
                break;
        }

        intent.putExtra("todo", todo);
        this.setResult(RESULT_OK, intent);
        this.finish();

    }

}
