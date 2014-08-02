package com.simplerssreader;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.app.AlertDialog;

public class RssFragment extends Fragment implements OnItemClickListener {
    protected static final int REQUEST_CODE = 10;
	private ProgressBar progressBar;
	private ListView listView;
	private View view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_layout, container, false);
			progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
			listView = (ListView) view.findViewById(R.id.listView);
			listView.setOnItemClickListener(this);
			startService();
		} else {
			// If we are returning from a configuration change:
			// "view" is still attached to the previous view hierarchy
			// so we need to remove it and re-attach it to the current one
			ViewGroup parent = (ViewGroup) view.getParent();
			parent.removeView(view);
		}
		return view;
	}

	private void startService() {
		Intent intent = new Intent(getActivity(), RssService.class);
		intent.putExtra(RssService.RECEIVER, resultReceiver);
		getActivity().startService(intent);
	}

	/**
	 * Once the {@link RssService} finishes its task, the result is sent to this
	 * ResultReceiver.
	 */
	private final ResultReceiver resultReceiver = new ResultReceiver(new Handler()) {
		@SuppressWarnings("unchecked")
		@Override
		protected void onReceiveResult(int resultCode, Bundle resultData) {
			progressBar.setVisibility(View.GONE);
			List<RssItem> items = (List<RssItem>) resultData.getSerializable(RssService.ITEMS);
			if (items != null) {
				RssAdapter adapter = new RssAdapter(getActivity(), items);
				listView.setAdapter(adapter);
			} else {
				Toast.makeText(getActivity(), "An error occured while downloading the rss feed.",
						Toast.LENGTH_LONG).show();
			}
		};
	};

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		RssAdapter adapter = (RssAdapter) parent.getAdapter();
		RssItem item = (RssItem) adapter.getItem(position);
		Uri uri = Uri.parse(item.getLink());
		//Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		//startActivity(intent);

try {
    Intent intent = new Intent(parent.getContext(), ParametrosActivity.class);
    // damos valor al parámetro a pasar
    intent.putExtra("param1", item.getLink());
    intent.putExtra("videoTitle", item.getTitle());
            /*
             * Inicia una actividad que devolverá un resultado cuando
             * haya terminado. Cuando la actividad termina, se llama al método
             * onActivityResult() con el requestCode dado.
             * El uso de un requestCode negativo es lo mismo que llamar a
             * startActivity(intent) (la actividad no se iniciará como una
             * sub-actividad).
             */
    startActivityForResult(intent, REQUEST_CODE);
}
catch (Exception Ex)
{
    // 1. Instantiate an AlertDialog.Builder with its constructor
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

// 2. Chain together various setter methods to set the dialog characteristics
    builder.setMessage(Ex.getMessage())
            .setTitle(R.string.dialog_title);

// 3. Get the AlertDialog from create()
    AlertDialog dialog = builder.create();
}


	}
}
