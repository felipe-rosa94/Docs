package com.felipe.docs.Adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.felipe.docs.Fragment.FragCartoes;
import com.felipe.docs.Model.Cartoes;
import com.felipe.docs.R;

import java.util.ArrayList;

public class CartoesAdapter extends RecyclerView.Adapter<CartoesAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Cartoes> cartoes;

    public CartoesAdapter(Context context, ArrayList<Cartoes> cartoes) {
        this.context = context;
        this.cartoes = cartoes;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cartoes, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Cartoes d = cartoes.get(position);
        holder.tvDescricao.setText(d.getDescricao());
        holder.tvNumeracao.setText(d.getNumero());
        holder.tvNome.setText(d.getNome());
        holder.tvData.setText(d.getData());
        holder.tvCvc.setText(d.getCvc());
        holder.tvSenha.setText(d.getSenha());
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragCartoes.MsgConfirmar(position);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragCartoes.MsgDeletar(position);
            }
        });
        holder.btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(cartoes.get(position).getDescricao() + ": " + cartoes.get(position).getSenha());
                Toast.makeText(context, "Copiado para área de transferência!", Toast.LENGTH_LONG).show();
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                share.putExtra(Intent.EXTRA_SUBJECT, "Aqui vai sua mensagem");
                share.putExtra(Intent.EXTRA_TEXT, cartoes.get(position).getDescricao() + ": " + cartoes.get(position).getSenha());
                context.startActivity(Intent.createChooser(share, "Compartilhar"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartoes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvDescricao, tvNome, tvNumeracao, tvData, tvCvc, tvSenha;
        public ImageView btnEdit, btnDelete, btnCopy;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvDescricao = itemView.findViewById(R.id.tv_carts_descricao);
            tvNome = itemView.findViewById(R.id.tv_carts_nome);
            tvNumeracao = itemView.findViewById(R.id.tv_carts_numeracao);
            tvData = itemView.findViewById(R.id.tv_carts_data);
            tvCvc = itemView.findViewById(R.id.tv_carts_cvc);
            tvSenha = itemView.findViewById(R.id.tv_carts_senha);

            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnCopy = itemView.findViewById(R.id.btn_copy);
        }
    }
}
