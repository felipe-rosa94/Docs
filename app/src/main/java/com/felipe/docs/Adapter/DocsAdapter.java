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

import com.felipe.docs.Fragment.FragDocs;
import com.felipe.docs.Model.Documentos;
import com.felipe.docs.R;

import java.util.ArrayList;

public class DocsAdapter extends RecyclerView.Adapter<DocsAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Documentos> documentos;

    public DocsAdapter(Context context, ArrayList<Documentos> documentos) {
        this.context = context;
        this.documentos = documentos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_docs, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Documentos d = documentos.get(position);
        holder.tvDescricao.setText(d.getDescricao());
        holder.tvNumeracao.setText(d.getNumero());
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragDocs.MsgConfirmar(position);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragDocs.MsgDeletar(position);
            }
        });
        holder.btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(documentos.get(position).getDescricao() +": "+documentos.get(position).getNumero());
                Toast.makeText(context, "Copiado para área de transferência!", Toast.LENGTH_LONG).show();
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                share.putExtra(Intent.EXTRA_SUBJECT,"Aqui vai sua mensagem");
                share.putExtra(Intent.EXTRA_TEXT, documentos.get(position).getDescricao() +"\n"+documentos.get(position).getNumero());
                context.startActivity(Intent.createChooser(share, "Compartilhar"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return documentos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvDescricao, tvNumeracao;
        public ImageView btnEdit, btnDelete, btnCopy;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvDescricao = itemView.findViewById(R.id.tv_docs_descricao);
            tvNumeracao = itemView.findViewById(R.id.tv_docs_numeracao);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnCopy = itemView.findViewById(R.id.btn_copy);
        }
    }
}
