package edu.missouri.phonebook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter {

    private List<Contact> contactList;

    private OnItemClickListener onItemClickListener = null;

    public RecyclerViewAdapter(List<Contact> contactList) {
        this.contactList = contactList;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.contact, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        Contact contact = contactList.get(position);
        viewHolder.contactTextView.setText(contact.getName());
        viewHolder.phoneTextView.setText(String.valueOf(contact.getPhoneNumber()).replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1)-$2-$3"));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(viewHolder.itemView, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public void addContact(Contact contact) {
        contactList.add(contact);
        notifyItemInserted(contactList.size()-1);
        //notifyDataSetChanged();
    }


    public void deleteContact(int position) {
        if (position < contactList.size() && position >=0 ) {
            contactList.remove(position);
            //notifyItemChanged(position);
            notifyItemRemoved(position);
        }
    }

     public void updateContact(int position, Contact newContact){
        if (position < contactList.size() && position >=0 ) {
            contactList.set(position, newContact);
            //notifyDataSetChanged();
            notifyItemChanged(position);
        }
     }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView contactTextView;
        public TextView phoneTextView;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            contactTextView = itemView.findViewById(R.id.contactTextView);
            phoneTextView = itemView.findViewById(R.id.phoneTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(itemView, getAdapterPosition());
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

}
