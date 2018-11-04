package com.example.yskh0.todo_recyclerview;

import android.app.DialogFragment;
import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD = 1, UPDATE = 2;
    private List<Todo> itemList = new ArrayList<>();
    private TodoAdapter todoAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.lv);
        Object ret = this.readFile();
        if (ret != null) {
            itemList = (List<Todo>) ret;
        }


        todoAdapter = new TodoAdapter(this, R.layout.list_item, itemList);
        listView.setAdapter(todoAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(MainActivity.this, SubActivity.class);
                intent.putExtra("requestCode", UPDATE);
                intent.putExtra("todo", itemList.get(position));
                intent.putExtra("index", position);
                startActivityForResult(intent,UPDATE);

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("todo", itemList.get(position));
                DialogFragment dialogFragment = new MyDialog();
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getFragmentManager(), "tag");
                return true;
            }
        });
    }

    public void btAdd(View v) {
        Intent i = new Intent(this, SubActivity.class);
        i.putExtra("requestCode", ADD);
        startActivityForResult(i, ADD);
    }

    public void removeItem(Todo todo) {
        itemList.remove(todo);
        todoAdapter.notifyDataSetChanged();
        Toast.makeText(this, todo.getContents() + "を削除しました。", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Todo t = (Todo) data.getSerializableExtra("todo");
            switch (requestCode) {
                case ADD:
                    itemList.add(t);
                    break;
                case UPDATE:
                    int index = data.getIntExtra("index", -1);
                    itemList.set(index, t);
                    break;
            }
            todoAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveFile();
    }

    private void saveFile() {
        ObjectOutputStream oos = null;
        try {
            FileOutputStream fos = openFileOutput("data.dat", MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(itemList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Object readFile() {
        Object ret = null;
        ObjectInputStream ois = null;
        try {
            FileInputStream fis = openFileInput("data.dat");
            ois = new ObjectInputStream(fis);
            ret = ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }
}
