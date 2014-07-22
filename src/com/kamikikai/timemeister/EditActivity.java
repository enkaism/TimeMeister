package com.kamikikai.timemeister;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class EditActivity extends FragmentActivity implements OnClickListener {

	private static final String TAG = EditActivity.class.getSimpleName();
	private Alarm editor;
	private String editorNum;
	private ImageView icon;
	private EditText timeH, timeM, timeS,name;
	private Button bt;
	private int iconId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_edit);

		Intent intent = getIntent();
		if (intent != null) {
			editor = (Alarm) intent.getSerializableExtra("editor");
			editorNum = intent.getStringExtra("editorNum");
		}
		icon = (ImageView) findViewById(R.id.imageIcon);
		iconId = editor.getIconId(editor.getIcon());
		name = (EditText) findViewById(R.id.editName);
		timeH = (EditText) findViewById(R.id.editTimeH);
		timeM = (EditText) findViewById(R.id.editTimeM);
		timeS = (EditText) findViewById(R.id.editTimeS);

		bt = (Button) findViewById(R.id.btEdit);

        name.setText(editor.getName());
        timeH.setText(""+editor.getHour(editor.getTime()));
        timeM.setText(""+editor.getMinute(editor.getTime()));
        timeS.setText(""+editor.getSecond(editor.getTime()));
		bt.setOnClickListener(this);
		Spinner spinner = (Spinner) findViewById(R.id.spinnerIcon);
		spinner.setSelection(iconId);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				icon.setImageResource(Res.drawable.get((int)id));
				iconId = (int) id;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				Toast.makeText(getApplicationContext(), "アイコンが選択されていません",
						Toast.LENGTH_SHORT).show();
			}

		});

	}

	public void onClick(View v) {
		try {
			editor.setName(name.getText().toString());
			editor.setTime(Integer.parseInt(timeH.getText().toString()) * 3600
                    + Integer.parseInt(timeM.getText().toString()) * 60
                    + Integer.parseInt(timeS.getText().toString()));
			editor.setIcon(Res.drawableButton.get(iconId));
			editAlarm();
            Toast.makeText(getApplication(),"タイマーを変更しました。",Toast.LENGTH_SHORT).show();
            finish();
		} catch (NumberFormatException e) {
			Toast.makeText(getApplication(), "入力エラー:時間", Toast.LENGTH_SHORT)
					.show();
		}
		
	}

	protected void editAlarm() {
		Map<String, Alarm> data = readAlarm();
		data.put(editorNum,editor);
		Log.d(TAG, data.get(editorNum).getName());
		try {
			FileOutputStream fos = openFileOutput("Alarm.dat", MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(data);
			oos.close();
		} catch (Exception e) {
			Log.d(TAG, "Error");
		}
	}

	@SuppressWarnings("unchecked")
	public Map<String, Alarm> readAlarm() {
		Map<String, Alarm> data = new HashMap<String, Alarm>();
		try {
			FileInputStream fis = openFileInput("Alarm.dat");
			ObjectInputStream ois = new ObjectInputStream(fis);
			data = (Map<String, Alarm>) ois.readObject();
		} catch (Exception e) {
			Log.d(TAG, "Error");
		}

		return data;
	}
}
