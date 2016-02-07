package weblayer.vendas.view;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpinAdapterFilial extends ArrayAdapter<weblayer.vendas.DTO.FilialDTO>{

    private Context context;
    private weblayer.vendas.DTO.FilialDTO[] values;

    public SpinAdapterFilial(Context context, int textViewResourceId, weblayer.vendas.DTO.FilialDTO[] values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    public int getCount(){
       return values.length;
    }

    public weblayer.vendas.DTO.FilialDTO getItem(int position){
       return values[position];
    }
    
    public long getItemId(int position){
       return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(values[position].getds_filial());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
            ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(values[position].getds_filial());
        return label;
    }
}