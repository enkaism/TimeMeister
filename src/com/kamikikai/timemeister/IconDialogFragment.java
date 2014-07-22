package com.kamikikai.timemeister;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class IconDialogFragment extends DialogFragment implements OnClickListener{
	private ListView listView;
	private ArrayAdapter<String> adapter;
	private String[] icons;
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_icon, null);
		icons = getResources().getStringArray(R.array.icons_array);
		listView = (ListView) v.findViewById(R.id.listView1);
		adapter = new ArrayAdapter<String>(getActivity(),R.layout.listicon,icons);
		listView.setAdapter(adapter);
		builder.setMessage("アイコンを設定してください").setItems(icons, this);
		// Create the AlertDialog object and return it
		return builder.create();
	}
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		
	}
}
