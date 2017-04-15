package com.mobile.project;

        import android.content.Context;
        import android.net.Uri;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

        import java.util.List;


public class DocumentsAdapter extends BaseAdapter {

    List<MyDocument> list;

    public List<MyDocument> getList() {
        return list;
    }

    public void setList(List<MyDocument> list) {
        this.list = list;
    }

    Context context;
    private  static LayoutInflater inflater= null;

    public DocumentsAdapter( Context context,List<MyDocument> list) {
        this.list = list;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class Holder{
        TextView nom;
        TextView date;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Holder holder;
        if (convertView==null){
            convertView= inflater.inflate(R.layout.row_list,null);
            holder = new Holder();
            holder.nom = (TextView) convertView.findViewById(  R.id.row_name );
            holder.date = (TextView) convertView.findViewById(  R.id.row_date);
        }
        else holder = (Holder) convertView.getTag();

        if(holder!=null){
            holder.nom.setText( list.get( position ).getName() );
            holder.date.setText( list.get( position ).getDateCreation() );
        }

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ((HistoryActivity)context).longClick(position);
                return false;
            }
        });

        return convertView;
    }
}
