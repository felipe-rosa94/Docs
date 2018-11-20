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

import com.felipe.docs.Fragment.FragContas;
import com.felipe.docs.Model.Contas;
import com.felipe.docs.R;

import java.util.ArrayList;

public class ContasAdapter extends RecyclerView.Adapter<ContasAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Contas> contas;

    public ContasAdapter(Context context, ArrayList<Contas> contas) {
        this.context = context;
        this.contas = contas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_contas, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Contas d = contas.get(position);

        holder.tvNome.setText(d.getNome());
        holder.tvNumero.setText(d.getNumeroConta());
        holder.tvAgencia.setText(d.getAgencia());
        holder.tvTipo.setText(d.getTipo());
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragContas.MsgConfirmar(position);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragContas.MsgDeletar(position);
            }
        });
        holder.btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText("Nome banco: " + contas.get(position).getNome() + "\n"
                        + "Agencia: " + contas.get(position).getAgencia() + " Número da conta: " + contas.get(position).getNumeroConta()
                        + " Tipo: " + contas.get(position).getTipo());
                Toast.makeText(context, "Copiado para área de transferência!", Toast.LENGTH_LONG).show();
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                share.putExtra(Intent.EXTRA_SUBJECT, "Aqui vai sua mensagem");
                share.putExtra(Intent.EXTRA_TEXT, "Nome banco: " + contas.get(position).getNome() + "\n"
                        + "Agencia: " + contas.get(position).getAgencia() + " Número da conta: " + contas.get(position).getNumeroConta()
                        + " Tipo: " + contas.get(position).getTipo());
                context.startActivity(Intent.createChooser(share, "Compartilhar"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return contas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvNome, tvAgencia, tvTipo, tvNumero;
        public ImageView btnEdit, btnDelete, btnCopy;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(R.id.tv_contas_nome);
            tvAgencia = itemView.findViewById(R.id.tv_contas_agencia);
            tvNumero = itemView.findViewById(R.id.tv_contas_numero);
            tvTipo = itemView.findViewById(R.id.tv_contas_tipos);

            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnCopy = itemView.findViewById(R.id.btn_copy);
        }
    }
}
