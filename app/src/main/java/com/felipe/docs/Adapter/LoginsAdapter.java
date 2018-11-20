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

import com.felipe.docs.Fragment.FragEmails;
import com.felipe.docs.Model.Logins;
import com.felipe.docs.R;

import java.util.ArrayList;

public class LoginsAdapter extends RecyclerView.Adapter<LoginsAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Logins> logins;

    public LoginsAdapter(Context context, ArrayList<Logins> logins) {
        this.context = context;
        this.logins = logins;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_logins, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Logins d = logins.get(position);
        holder.tvAplicacao.setText(d.getAplicacao());
        holder.tvLogins.setText(d.getLogins());
        holder.tvSenha.setText(d.getSenha());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragEmails.MsgConfirmar(position);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragEmails.MsgDeletar(position);
            }
        });

        holder.btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(logins.get(position).getAplicacao() + " ( " + logins.get(position).getLogins() + ", " + logins.get(position).getSenha() + " )");
                Toast.makeText(context, "Copiado para área de transferência!", Toast.LENGTH_LONG).show();
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                share.putExtra(Intent.EXTRA_SUBJECT, "Aqui vai sua mensagem");
                share.putExtra(Intent.EXTRA_TEXT, logins.get(position).getAplicacao() + "( " + logins.get(position).getLogins() + ", " + logins.get(position).getSenha() + " )");
                context.startActivity(Intent.createChooser(share, "Compartilhar"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return logins.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvAplicacao, tvLogins, tvSenha;
        public ImageView btnEdit, btnDelete, btnCopy;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvAplicacao = itemView.findViewById(R.id.tv_logins_aplicacao);
            tvLogins = itemView.findViewById(R.id.tv_logins_logins);
            tvSenha = itemView.findViewById(R.id.tv_logins_senha);

            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnCopy = itemView.findViewById(R.id.btn_copy);
        }
    }
}
