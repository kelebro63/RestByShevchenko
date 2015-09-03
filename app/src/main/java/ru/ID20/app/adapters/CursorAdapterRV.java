package ru.ID20.app.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;

import ru.ID20.app.tools.Logger;

/**
 * Created by Dzmitry Lukashanets on 04.12.2014.
 * This adapter use only with loader manager
 */

public abstract class CursorAdapterRV<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected final Logger LOG = Logger.getLogger(getTagFg(), isLogged());

    protected Cursor mCursor;
    protected Context mContext;

    public abstract String getTagFg();

    protected abstract boolean isLogged();

    public abstract boolean onBindViewHolder(VH holder, Cursor cursor);

    public CursorAdapterRV(Context context, Cursor cursor) {
        this.mCursor = cursor;
        this.mContext = context;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        if (mCursor == null || mCursor.isClosed()) {
            return;
        }
        if (mCursor.moveToPosition(position)) {
            onBindViewHolder(holder, mCursor);
        }
    }

    protected Context getContext() {
        return mContext;
    }

    protected Cursor getCursor() {
        return this.mCursor;
    }

    public void swapCursor(Cursor cursor) {
        this.mCursor = cursor;
        notifyDataSetChanged();
    }

    public Cursor getItemCursor(int position) {
        if (mCursor != null && !mCursor.isClosed() && mCursor.moveToPosition(position)) {
            return mCursor;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mCursor != null ? mCursor.getCount() : 0;
    }

}