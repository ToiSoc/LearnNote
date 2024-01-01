package gpnu.zhoujie.learnnote.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import gpnu.zhoujie.learnnote.R;
import gpnu.zhoujie.learnnote.activity_fold.AddNoteActivity;
import gpnu.zhoujie.learnnote.entity.Note;

public class NoteAdapter extends ArrayAdapter<Note> {

    private List<Note> noteList;

    public NoteAdapter(Context context,List<Note> noteList)
    {
        super(context, R.layout.manage_list,noteList);
        this.noteList = noteList;
    }

    @Override
    public View getView(int position,  View convertView,ViewGroup parent) {

        if(convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.manage_list,parent,false);
        }

        TextView title = convertView.findViewById(R.id.manage_list_item_title);
        TextView note = convertView.findViewById(R.id.manage_list_item_msg);
        TextView time = convertView.findViewById(R.id.manage_list_item_time);


        Note res = noteList.get(position);
        title.setText(res.getTitle());
        note.setText(res.getNote());
        time.setText(res.getTime());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddNoteActivity.class);
                intent.putExtra("sta","1");
                intent.putExtra("account",res.getAccount());
                intent.putExtra("uuid",res.getUuid());
                intent.putExtra("title",res.getTitle());
                intent.putExtra("note",res.getNote());
                intent.putExtra("time",res.getTime());

                getContext().startActivity(intent);
            }
        });


        return convertView;
    }

    public NoteAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public NoteAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public NoteAdapter(@NonNull Context context, int resource, @NonNull Note[] objects) {
        super(context, resource, objects);
    }

    public NoteAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull Note[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public NoteAdapter(@NonNull Context context, int resource, @NonNull List<Note> objects) {
        super(context, resource, objects);
    }

    public NoteAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Note> objects) {
        super(context, resource, textViewResourceId, objects);
    }
}
