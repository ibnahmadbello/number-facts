package com.regent.tech.numberisfun;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.regent.tech.numberisfun.Data.NumberContract;

public class NumberAdapter extends RecyclerView.Adapter<NumberAdapter.NumberAdapterViewHolder>{

    private Cursor mCursor;
    private Context mContext;

    public NumberAdapter(Context context, Cursor cursor){
        this.mContext = context;
        this.mCursor = cursor;
    }

    @NonNull
    @Override
    public NumberAdapter.NumberAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.number_list_item, parent, false);
        return new NumberAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NumberAdapter.NumberAdapterViewHolder holder, int position) {
        if (mCursor.moveToPosition(position))
            return;

        String fact = mCursor.getString(mCursor.getColumnIndex(NumberContract.NumberEntry.COLUMN_RESULT));
        long id = mCursor.getLong(mCursor.getColumnIndex(NumberContract.NumberEntry._ID));

        holder.factTextView.setText(fact);
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        if (mCursor != null)
            mCursor.close();
        if (newCursor != null)
            this.notifyDataSetChanged();
    }

    class NumberAdapterViewHolder extends RecyclerView.ViewHolder{

        TextView factTextView;

        public NumberAdapterViewHolder(View itemView){
            super(itemView);
            factTextView = itemView.findViewById(R.id.fact_text_view);
        }

    }
}
