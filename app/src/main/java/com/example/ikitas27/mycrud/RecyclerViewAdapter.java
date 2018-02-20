package com.example.ikitas27.mycrud;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ikitas27 on 2/20/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
     Context context;
    private List<ResultItem>resultItems;

    public RecyclerViewAdapter(Context context,List<ResultItem>resultItems){
        this.context=context;
        this.resultItems=resultItems;

    }
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view,parent,false);
       ViewHolder holder= new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
            ResultItem result = resultItems.get(position);
            holder.NPM.setText(result.getNpm());
            holder.NAMA.setText(result.getNama());
            holder.KELAS.setText(result.getKelas());
            holder.JADWAL.setText(result.getJadwal());

            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context,UpdateActivity.class);
                    context.startActivity(i);

                }
            });

    }

    @Override
    public int getItemCount() {
        return resultItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

      public   TextView NPM;
      public TextView NAMA;
      public TextView KELAS;
       public TextView JADWAL;

        public ViewHolder(View itemView) {
            super(itemView);
            NPM=(TextView) itemView.findViewById(R.id.textNPM);
            NAMA=(TextView) itemView.findViewById(R.id.textNama);
            KELAS=(TextView) itemView.findViewById(R.id.textKelas);
            JADWAL=(TextView) itemView.findViewById(R.id.textJadwal);

        }

        @Override
        public void onClick(View v) {
            String npm = NPM.getText().toString();
            String nama = NAMA.getText().toString();
            String kelas = KELAS.getText().toString();
            String jadwal = JADWAL.getText().toString();

            Intent i = new Intent(context, UpdateActivity.class);
            i.putExtra("npm", npm);
            i.putExtra("nama", nama);
            i.putExtra("kelas", kelas);
            i.putExtra("jadwal", jadwal);
            context.startActivity(i);

        }
    }
}
