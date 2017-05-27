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

        import model.Folder;


public class FoldersAdapter extends BaseAdapter {

    List<Folder> list;

    public List<Folder> getList() {
        return list;
    }

    public void setList(List<Folder> list) {
        this.list = list;
    }

    Context context;
    private  static LayoutInflater inflater= null;

    public FoldersAdapter(Context context, List<Folder> list) {
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
            holder.nom.setText( list.get( position ).getId() );
            holder.date.setText( list.get( position ).getState() );
        }

        return convertView;
    }
}
