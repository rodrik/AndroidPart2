package br.com.rodrigo.ijk;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SelfieAdapter extends BaseAdapter {

	private Activity activity;
	private List<Selfie> selfieList;
	
	public SelfieAdapter(Activity activity, List<Selfie> codeLearnChapterList) {
		super();
		this.activity = activity;
		this.selfieList = codeLearnChapterList;
	}

	@Override
	public int getCount() {
		return selfieList.size();
	}

	@Override
	public Selfie getItem(int arg0) {
		return selfieList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		if(arg1==null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            arg1 = inflater.inflate(R.layout.list_item, arg2, false);
        }

		TextView selfie = (TextView)arg1.findViewById(R.id.textView1);
		TextView chapterDesc = (TextView)arg1.findViewById(R.id.textView2);

		Selfie chapter = selfieList.get(arg0);

		selfie.setText(chapter.getTimestamp().toString());
		chapterDesc.setText(chapter.getFilename());

		return arg1;
	}

}