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

import com.felipe.docs.Fragment.FragOutros;
import com.felipe.docs.Model.Outros;
import com.felipe.docs.R;

import java.util.ArrayList;

public class OutrosAdapter extends RecyclerView.Adapter<OutrosAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Outros> outros;

    public OutrosAdapter(Context context, ArrayList<Outros> outros) {
        this.context = context;
        this.outros = outros;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_outros, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Outros d = outros.get(position);

        holder.tvDescricao.setText(d.getDescricao());
        holder.tvSenha.setText(d.getSenha());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragOutros.MsgConfirmar(position);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragOutros.MsgDeletar(position);
            }
        });
        holder.btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(outros.get(position).getDescricao() + ": " + outros.get(position).getSenha());
                Toast.makeText(context, "Copiado para área de transferência!", Toast.LENGTH_LONG).show();
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                share.putExtra(Intent.EXTRA_SUBJECT, "Aqui vai sua mensagem");
                share.putExtra(Intent.EXTRA_TEXT, outros.get(position).getDescricao() + ": " + outros.get(position).getSenha());
                context.startActivity(Intent.createChooser(share, "Compartilhar"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return outros.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvDescricao, tvSenha;
        public ImageView btnEdit, btnDelete, btnCopy;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvDescricao = itemView.findViewById(R.id.tv_outros_descricao);
            tvSenha = itemView.findViewById(R.id.tv_outros_senha);

            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnCopy = itemView.findViewById(R.id.btn_copy);
        }
    }
}
