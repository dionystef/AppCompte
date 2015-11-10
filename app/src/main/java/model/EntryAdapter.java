package model;

import java.util.ArrayList;

import com.dcs.appcompte.MainActivity;
import com.dcs.appcompte.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EntryAdapter extends ArrayAdapter<Item> {

    private Context context;
    private ArrayList<Item> items;
    private LayoutInflater vi;
    private MainActivity mainActivity;

    public EntryAdapter(Context context, ArrayList<Item> items, MainActivity mainActivity) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
        this.mainActivity = mainActivity;
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        final Item i = items.get(position);
        if (i != null) {

            // création de "l'entete" //
            if (i.isHeader()) {
                SectionItem si = (SectionItem) i;
                v = vi.inflate(R.layout.list_item_section, null);

                v.setOnClickListener(null);
                v.setOnLongClickListener(null);
                v.setLongClickable(false);

                // titre //
                final TextView sectionView = (TextView) v.findViewById(R.id.list_item_section_text);
                sectionView.setText(si.getTitle());


                // création item du content //
            } else if (i.isContent()) {
                Content ei = (Content) i;
                v = vi.inflate(R.layout.list_item_entry, null);
                final TextView title = (TextView) v.findViewById(R.id.list_item_entry_title);
                final TextView summary = (TextView) v.findViewById(R.id.list_item_entry_summary);

                if (title != null)
                    title.setText(ei.label);
                summary.setText(String.valueOf(ei.montant));
            }/*
            else
			{
				// création item maison //
				v = vi.inflate(R.layout.list_item_contacts, null);
				final TextView name = (TextView)v.findViewById(R.id.list_item_name);
				final TextView contactNo = (TextView)v.findViewById(R.id.list_item_entry_contact);
				
				if (name != null) 
					name.setText(ei.title);
				if(contactNo != null)
					contactNo.setText(String.valueOf(ei.montant));
			}*/
        }
        return v;
    }

}
