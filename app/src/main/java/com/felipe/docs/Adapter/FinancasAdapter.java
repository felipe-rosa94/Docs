package com.felipe.docs.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.felipe.docs.Model.CriaContaFinaceira;
import com.felipe.docs.R;

import java.util.ArrayList;

public class FinancasAdapter extends RecyclerView.Adapter<FinancasAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<CriaContaFinaceira> contas;
    private double valorTotal;

    public FinancasAdapter(Context context, ArrayList<CriaContaFinaceira> contas, double valorTotal) {
        this.context = context;
        this.contas = contas;
        this.valorTotal = valorTotal;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_financas, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final CriaContaFinaceira c = contas.get(position);

        holder.tvDescricao.setText(c.getDescricao());

        if (c.getObservacao().equals("")) {
            holder.tvObs.setVisibility(View.GONE);
        } else {
            holder.tvObs.setVisibility(View.VISIBLE);
        }
        holder.tvObs.setText(c.getObservacao());

        if (c.getTipoPagamento().equals("Débito")) {
            holder.tvParcelas.setVisibility(View.GONE);
        } else {
            holder.tvParcelas.setVisibility(View.VISIBLE);
        }
        holder.tvCreDeb.setText(c.getTipoPagamento());

        holder.tvData.setText(c.getData());

        holder.tvParcelas.setText("Número de parcelas " + String.valueOf(c.getParcela()));

        try {
            switch (c.getTipo()) {
                case 0:
                    holder.tvTipo.setText("Lazer");
                    break;
                case 1:
                    holder.tvTipo.setText("Combustivél");
                    break;
                case 2:
                    holder.tvTipo.setText("Roupa");
                    break;
                case 3:
                    holder.tvTipo.setText("Alimentação");
                    break;
                case 4:
                    holder.tvTipo.setText("Transporte");
                    break;
                case 5:
                    holder.tvTipo.setText("Futilidade");
                    break;
                case 6:
                    holder.tvTipo.setText("Itens Casa");
                    break;
                case 7:
                    holder.tvTipo.setText("Comida");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.tvValor.setText(String.valueOf(c.getValorTotal()));

        //TrocarValor(c.getTipoPagamento(), c.getValorTotal());

    }



    @Override
    public int getItemCount() {
        return contas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvDescricao, tvObs, tvCreDeb, tvParcelas, tvTipo, tvValor, tvData;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvDescricao = itemView.findViewById(R.id.tv_financas_descricao);
            tvObs = itemView.findViewById(R.id.tv_financas_obs);
            tvCreDeb = itemView.findViewById(R.id.tv_financas_cre_deb);
            tvParcelas = itemView.findViewById(R.id.tv_financas_parcelas);
            tvTipo = itemView.findViewById(R.id.tv_financas_tipo);
            tvValor = itemView.findViewById(R.id.tv_financas_valor);
            tvData = itemView.findViewById(R.id.tv_financas_data);
        }
    }
}
